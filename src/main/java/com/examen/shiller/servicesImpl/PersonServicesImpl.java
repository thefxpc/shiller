package com.examen.shiller.servicesImpl;

import com.examen.shiller.httpRequest.AddPersonRequest;
import com.examen.shiller.model.Person;
import com.examen.shiller.repository.PersonRepository;
import com.examen.shiller.services.PersonServices;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServicesImpl implements PersonServices {

    private final PersonRepository personRepository;

    public PersonServicesImpl(PersonRepository personRepository){
        this.personRepository=personRepository;
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> getPersona(Long person_id) {
        return personRepository.findById(person_id);
    }

    @Override
    public void deletePerson(Person person) {
        person.setActive(false);
        personRepository.save(person);
    }

    @Override
    public Person addPerson(AddPersonRequest addPersonRequest) {
        Person person=new Person(addPersonRequest.getName(),addPersonRequest.getAge(),addPersonRequest.getGender());
        return  personRepository.save(person);
    }

    @Override
    public Person editPerson(Person person,AddPersonRequest addPersonRequest) {
        person.setName(addPersonRequest.getName());
        person.setAge(addPersonRequest.getAge());
        person.setGender(addPersonRequest.getGender());
        return  personRepository.save(person);
    }
}
