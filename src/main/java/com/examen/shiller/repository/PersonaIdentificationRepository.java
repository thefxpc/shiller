package com.examen.shiller.repository;

import com.examen.shiller.model.PersonIdentification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonaIdentificationRepository extends JpaRepository<PersonIdentification,Long> {

    @Query("SELECT P FROM PersonIdentification P WHERE P.active=true")
    List<PersonIdentification> findAll();

    @Query("SELECT P FROM PersonIdentification P WHERE P.active=true AND P.personIdentificationId=:personIdentificationId")
    Optional<PersonIdentification> findById(Long personIdentificationId);
}
