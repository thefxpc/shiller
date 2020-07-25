package com.examen.shiller.services;

import com.examen.shiller.model.Identification;

import java.util.Optional;

public interface IdentificationServices {
    Optional<Identification> getIdentification(Long IdentificationId);
}
