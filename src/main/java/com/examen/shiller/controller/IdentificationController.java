package com.examen.shiller.controller;

import com.examen.shiller.exception.PersonIdentificationNotFoundException;
import com.examen.shiller.exception.PersonNotFoundException;
import com.examen.shiller.httpRequest.AddIdentificationRequest;
import com.examen.shiller.httpRequest.ModifyIdentificationRequest;
import com.examen.shiller.model.Identification;
import com.examen.shiller.model.Person;
import com.examen.shiller.model.PersonIdentification;
import com.examen.shiller.service.IdentificationServices;
import com.examen.shiller.service.PersonIdentificationServices;
import com.examen.shiller.service.PersonServices;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class IdentificationController {

    private final PersonIdentificationServices personIdentificationServices;
    private final PersonServices personServices;
    private final IdentificationServices identificationServices;


    public IdentificationController(PersonIdentificationServices personIdentificationServices,
                                    PersonServices personServices,
                                    IdentificationServices identificationServices){
        this.personIdentificationServices=personIdentificationServices;
        this.personServices=personServices;
        this.identificationServices=identificationServices;
    }

    @ApiOperation(value = "Retrieve all the existing person identification relations")
    @GetMapping("/identification")
    public ResponseEntity<?> getAllIdentifications(){
        return ResponseEntity.ok(personIdentificationServices.getIdentifications());
    }

    @ApiOperation(value = "Retrieve an specific person identification relation")
    @GetMapping("/identification/{personIdentificationId}")
    public ResponseEntity<?> getIdentification(@PathVariable Long personIdentificationId){
        Optional<PersonIdentification> personIdentificationOptional;
        personIdentificationOptional = personIdentificationServices.getPersonaIdentification(personIdentificationId);
        if(personIdentificationOptional.isPresent()) return ResponseEntity.ok(personIdentificationOptional.get());
        else throw new PersonIdentificationNotFoundException("IdentificationId "+personIdentificationId+" not found");
    }

    @ApiOperation(value = "Delete an existing person identification relation")
    @DeleteMapping("/identification/{personIdentificationId}")
    public void deleteIdentification(@PathVariable Long personIdentificationId){
        Optional<PersonIdentification> personIdentificationOptional;
        personIdentificationOptional = personIdentificationServices.getPersonaIdentification(personIdentificationId);
        if(personIdentificationOptional.isPresent()) {
            personIdentificationServices.deletePersonaIdentification(personIdentificationOptional.get());
        }
        else throw new PersonIdentificationNotFoundException("IdentificationId:"+personIdentificationId+"not found");
    }

    @ApiOperation(value = "Add a new person identification relation")
    @PostMapping("/identification")
    public ResponseEntity<?> addIdentification(@Valid @RequestBody AddIdentificationRequest addIdentificationRequest){
            Optional<Person> personOptional=personServices.getPersona(addIdentificationRequest.getPerson_id());
            if(personOptional.isEmpty()) throw new PersonNotFoundException("PersonId:"+addIdentificationRequest.getPerson_id()+"not found");

            Optional<Identification> identificationOptional=identificationServices.getIdentification(addIdentificationRequest.getIdentification_id());
            if(identificationOptional.isEmpty()) throw new PersonIdentificationNotFoundException(this.notFoundMessage(addIdentificationRequest.getIdentification_id()));

            PersonIdentification personIdentification=personIdentificationServices.addPersonIdentification(personOptional.get(),identificationOptional.get(),addIdentificationRequest.getIdentificationNumber());

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(personIdentification.getPersonIdentificationId()).toUri();
            return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Modify an existing person identification relation")
    @PutMapping("/identification")
    public ResponseEntity<?> updateIdentification(@Valid @RequestBody ModifyIdentificationRequest modifyIdentificationRequest){

            Optional<PersonIdentification> personIdentificationOptional=personIdentificationServices.getPersonaIdentification(modifyIdentificationRequest.getPersonIdentificationId());
            if(personIdentificationOptional.isEmpty()) throw new PersonIdentificationNotFoundException(this.notFoundMessage(modifyIdentificationRequest.getPersonIdentificationId()));
            else{
                PersonIdentification personIdentification=personIdentificationServices.updatePersonIdentification(personIdentificationOptional.get(),modifyIdentificationRequest.getIdentificationNumber());
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest().path("/{id}")
                        .buildAndExpand(personIdentification.getPersonIdentificationId()).toUri();
                return ResponseEntity.created(location).build();
            }
    }

    public String notFoundMessage(Long personIdentificationId){
        return "IdentificationId:"+personIdentificationId+" not found";
    }

}
