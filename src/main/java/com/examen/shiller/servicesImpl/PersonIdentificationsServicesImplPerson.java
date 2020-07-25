package com.examen.shiller.servicesImpl;

import com.examen.shiller.model.Identification;
import com.examen.shiller.model.Person;
import com.examen.shiller.model.PersonIdentification;
import com.examen.shiller.repository.PersonaIdentificationRepository;
import com.examen.shiller.services.PersonIdentificationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class PersonIdentificationsServicesImplPerson implements PersonIdentificationServices {

    private final PersonaIdentificationRepository personaIdentificationRepository;

    @Autowired
    public PersonIdentificationsServicesImplPerson(PersonaIdentificationRepository personaIdentificationRepository){
        this.personaIdentificationRepository=personaIdentificationRepository;
    }

    @Override
    public List<PersonIdentification> getIdentifications() {
        return personaIdentificationRepository.findAll();
    }

    @Override
    public Optional<PersonIdentification> getPersonaIdentification(Long personIdentificationId) {
        return personaIdentificationRepository.findById(personIdentificationId);
    }

    @Override
    public void deletePersonaIdentification(PersonIdentification personIdentification) {
        personIdentification.setActive(false);
        personaIdentificationRepository.save(personIdentification);
    }

    @Override
    public PersonIdentification addPersonIdentification(Person person, Identification identification, String identificationNumber) {
        PersonIdentification personIdentification=new PersonIdentification(identificationNumber);

        if(person.getPersonIdentifications()==null) person.setPersonIdentifications(new HashSet<>());
        person.getPersonIdentifications().add(personIdentification);
        personIdentification.setPerson(person);

        if(identification.getPersonIdentifications()==null) person.setPersonIdentifications(new HashSet<>());
        identification.getPersonIdentifications().add(personIdentification);
        personIdentification.setIdentification(identification);

        return personaIdentificationRepository.save(personIdentification);
    }

    @Override
    public PersonIdentification updatePersonIdentification(PersonIdentification personIdentification, String identificationNumber) {
        personIdentification.setIdentificationNumber(identificationNumber);
        return personaIdentificationRepository.save(personIdentification);
    }
}
