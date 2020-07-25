package com.examen.shiller.rest;

import com.examen.shiller.httpRequest.AddPersonRequest;
import com.examen.shiller.httpResponse.MessageResponse;
import com.examen.shiller.model.Person;
import com.examen.shiller.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PersonaAPI {

    private final PersonServices personServices;

    @Autowired
    public PersonaAPI(PersonServices personServices){
        this.personServices=personServices;
    }

    @GetMapping("/person")
    public ResponseEntity<?> getAllPerson(){
        try{
            return ResponseEntity.ok(personServices.findAll());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(),false));
        }
    }

    @GetMapping("/person/{person_id}")
    public ResponseEntity<?> getPersona(@PathVariable Long person_id){
        try{
            Optional<Person> personOptional=personServices.getPersona(person_id);
            if(personOptional.isPresent()) return ResponseEntity.ok(personOptional.get());
            else return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(),false));
        }
    }

    @DeleteMapping("/person/{person_id}")
    public ResponseEntity<?> deletePersona(@PathVariable Long person_id){
        try{
            Optional<Person> personOptional=personServices.getPersona(person_id);
            if(personOptional.isPresent()) {
                personServices.deletePerson(personOptional.get());
                return ResponseEntity.ok().body(new MessageResponse("Persona deleted",true));
            }
            else return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(),false));
        }
    }

    @PostMapping("/person")
    public ResponseEntity<?> addPersona(@RequestBody AddPersonRequest addPersonRequest){
        try{
            Person person =personServices.addPerson(addPersonRequest);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/person/{id}")
                    .buildAndExpand(person.getPersonId()).toUri();
            return ResponseEntity.created(location).build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(),false));
        }
    }

    @PutMapping("/person")
    public ResponseEntity<?> editPersona(@RequestBody AddPersonRequest addPersonRequest){
        try{
            Optional<Person> personOptional=personServices.getPersona(addPersonRequest.getPerson_id());
            if(personOptional.isPresent()) {
                Person person = personServices.editPerson(personOptional.get(),addPersonRequest);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest().path("/person/{id}")
                        .buildAndExpand(person.getPersonId()).toUri();
                return ResponseEntity.created(location).build();
            }else{
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(),false));
        }
    }

}
