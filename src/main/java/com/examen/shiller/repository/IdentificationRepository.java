package com.examen.shiller.repository;

import com.examen.shiller.model.Identification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IdentificationRepository extends JpaRepository<Identification,Long> {
    @Query("SELECT I FROM Identification I WHERE I.active=true AND I.identificationId=:identificationId")
    Optional<Identification> findById(Long identificationId);
}
