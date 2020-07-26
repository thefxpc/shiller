package com.examen.shiller.rest;

import com.examen.shiller.exception.PersonNotFoundException;
import com.examen.shiller.httpRequest.AddPersonRequest;
import com.examen.shiller.httpRequest.ModifyPersonRequest;
import com.examen.shiller.model.Person;
import com.examen.shiller.services.PersonServices;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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

    @ApiOperation(value = "Retrieve all the existing persons")
    @GetMapping("/person")
    public ResponseEntity<?> getAllPerson(){
        return ResponseEntity.ok(personServices.findAll());
    }

    @ApiOperation(value = "Retrieve an existing person")
    @GetMapping("/person/{person_id}")
    public ResponseEntity<?> getPersona(@PathVariable Long person_id){
        Optional<Person> personOptional=personServices.getPersona(person_id);
        if(personOptional.isPresent()) return ResponseEntity.ok(personOptional.get());
        else throw new PersonNotFoundException("PersonId "+person_id+" not found");
    }

    @ApiOperation(value = "Deletes an existing person")
    @DeleteMapping("/person/{person_id}")
    public void deletePersona(@PathVariable Long person_id){
        Optional<Person> personOptional=personServices.getPersona(person_id);
        if(personOptional.isPresent()) {
            personServices.deletePerson(personOptional.get());
        }
        else throw new PersonNotFoundException("PersonId "+person_id+" not found");
    }

    @ApiOperation(value = "Creates a new person")
    @PostMapping("/person")
    public ResponseEntity<?> addPersona(@Valid @RequestBody AddPersonRequest addPersonRequest){
        Person person =personServices.addPerson(addPersonRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(person.getPersonId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Modify an existing person")
    @PutMapping("/person")
    public ResponseEntity<?> editPersona(@Valid @RequestBody ModifyPersonRequest modifyPersonRequest){
        Optional<Person> personOptional=personServices.getPersona(modifyPersonRequest.getPerson_id());
        if(personOptional.isPresent()) {
            Person person = personServices.editPerson(personOptional.get(),modifyPersonRequest);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(person.getPersonId()).toUri();
            return ResponseEntity.created(location).build();
        }else{
            throw new PersonNotFoundException("PersonId:"+modifyPersonRequest.getPerson_id()+" not found");
        }
    }

}
