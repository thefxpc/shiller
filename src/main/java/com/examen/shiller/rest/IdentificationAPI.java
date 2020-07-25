package com.examen.shiller.rest;

import com.examen.shiller.httpRequest.AddIdentificationRequest;
import com.examen.shiller.httpResponse.MessageResponse;
import com.examen.shiller.model.Identification;
import com.examen.shiller.model.Person;
import com.examen.shiller.model.PersonIdentification;
import com.examen.shiller.services.IdentificationServices;
import com.examen.shiller.services.PersonIdentificationServices;
import com.examen.shiller.services.PersonServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class IdentificationAPI {

    private final PersonIdentificationServices personIdentificationServices;
    private final PersonServices personServices;
    private final IdentificationServices identificationServices;


    public IdentificationAPI(PersonIdentificationServices personIdentificationServices,
                             PersonServices personServices,
                             IdentificationServices identificationServices){
        this.personIdentificationServices=personIdentificationServices;
        this.personServices=personServices;
        this.identificationServices=identificationServices;
    }

    @GetMapping("/identification")
    public ResponseEntity<?> getAllIdentifications(){
        try{
            return ResponseEntity.ok(personIdentificationServices.getIdentifications());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(),false));
        }
    }

    @GetMapping("/identification/{personIdentificationId}")
    public ResponseEntity<?> getIdentification(@PathVariable Long personIdentificationId){
        try{
            Optional<PersonIdentification> personIdentificationOptional=personIdentificationServices.getPersonaIdentification(personIdentificationId);
            if(personIdentificationOptional.isPresent()) return ResponseEntity.ok(personIdentificationOptional.get());
            else return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(),false));
        }
    }

    @DeleteMapping("/identification/{personIdentificationId}")
    public ResponseEntity<?> deleteIdentification(@PathVariable Long personIdentificationId){
        try{
            Optional<PersonIdentification> personIdentificationOptional=personIdentificationServices.getPersonaIdentification(personIdentificationId);
            if(personIdentificationOptional.isPresent()) {
                personIdentificationServices.deletePersonaIdentification(personIdentificationOptional.get());
                return ResponseEntity.ok().body(new MessageResponse("Identification deleted",true));
            }
            else return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(),false));
        }
    }

    @PostMapping("/identification")
    public ResponseEntity<?> addIdentification(@RequestBody AddIdentificationRequest addIdentificationRequest){
        try{
            Optional<Person> personOptional=personServices.getPersona(addIdentificationRequest.getPerson_id());
            if(personOptional.isEmpty()) return ResponseEntity.badRequest().body(new MessageResponse("Person not found",false));
            Optional<Identification> identificationOptional=identificationServices.getIdentification(addIdentificationRequest.getIdentification_id());
            if(identificationOptional.isEmpty()) return ResponseEntity.badRequest().body(new MessageResponse("Identification type not found",false));

            PersonIdentification personIdentification=personIdentificationServices.addPersonIdentification(personOptional.get(),identificationOptional.get(),addIdentificationRequest.getIdentificationNumber());

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/identification/{id}")
                    .buildAndExpand(personIdentification.getIdentification()).toUri();
            return ResponseEntity.created(location).build();

        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(),false));
        }
    }

    @PutMapping("/identification")
    public ResponseEntity<?> updateIdentification(@RequestBody AddIdentificationRequest addIdentificationRequest){
        try{
            Optional<PersonIdentification> personIdentificationOptional=personIdentificationServices.getPersonaIdentification(addIdentificationRequest.getPersonIdentificationId());
            if(personIdentificationOptional.isEmpty()) return ResponseEntity.badRequest().body(new MessageResponse("Identification not found",false));
            else{
                PersonIdentification personIdentification=personIdentificationServices.updatePersonIdentification(personIdentificationOptional.get(),addIdentificationRequest.getIdentificationNumber());
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest().path("/identification/{id}")
                        .buildAndExpand(personIdentification.getIdentification()).toUri();
                return ResponseEntity.created(location).build();
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(),false));
        }
    }

}
