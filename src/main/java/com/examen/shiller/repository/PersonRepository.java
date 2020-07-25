package com.examen.shiller.repository;

import com.examen.shiller.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person,Long> {
    @Query("SELECT P FROM Person P WHERE P.active=true")
    List<Person> findAll();

    @Query("SELECT P FROM Person P WHERE P.active=true AND P.personId=?1")
    Optional<Person> findById(Long person_id);
}
