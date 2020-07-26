package com.examen.shiller.services;

import com.examen.shiller.httpRequest.AddPersonRequest;
import com.examen.shiller.httpRequest.ModifyPersonRequest;
import com.examen.shiller.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonServices {

    List<Person> findAll();
    Optional<Person> getPersona(Long person_id);
    void deletePerson(Person person);
    Person addPerson(AddPersonRequest addPersonRequest);
    Person editPerson(Person person, ModifyPersonRequest modifyPersonRequest);

}
