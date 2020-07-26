package com.examen.shiller.serviceimpl;

import com.examen.shiller.httpRequest.AddPersonRequest;
import com.examen.shiller.httpRequest.ModifyPersonRequest;
import com.examen.shiller.model.Person;
import com.examen.shiller.repository.PersonRepository;
import com.examen.shiller.service.PersonServices;
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
    public Optional<Person> getPersona(Long personId) {
        return personRepository.findById(personId);
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
    public Person editPerson(Person person, ModifyPersonRequest modifyPersonRequest) {
        person.setName(modifyPersonRequest.getName());
        person.setAge(modifyPersonRequest.getAge());
        person.setGender(modifyPersonRequest.getGender());
        return  personRepository.save(person);
    }
}
