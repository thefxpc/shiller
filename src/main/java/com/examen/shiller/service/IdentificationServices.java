package com.examen.shiller.service;

import com.examen.shiller.model.Identification;

import java.util.Optional;

public interface IdentificationServices {
    Optional<Identification> getIdentification(Long identificationId);
}
