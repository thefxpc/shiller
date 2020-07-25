package com.examen.shiller.services;

import com.examen.shiller.model.Identification;
import com.examen.shiller.model.Person;
import com.examen.shiller.model.PersonIdentification;

import java.util.List;
import java.util.Optional;

public interface PersonIdentificationServices {
    List<PersonIdentification>  getIdentifications();
    Optional<PersonIdentification> getPersonaIdentification(Long personIdentificationId);
    void deletePersonaIdentification(PersonIdentification personIdentification);
    PersonIdentification addPersonIdentification(Person person, Identification identification, String identificationNumber);
    PersonIdentification updatePersonIdentification(PersonIdentification personIdentification, String identificationNumber);
}
