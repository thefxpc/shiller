package com.examen.shiller.servicesImpl;

import com.examen.shiller.model.Identification;
import com.examen.shiller.repository.IdentificationRepository;
import com.examen.shiller.services.IdentificationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdentificationServicesImpl implements IdentificationServices {

    private final IdentificationRepository identificationRepository;

    @Autowired
    public IdentificationServicesImpl(IdentificationRepository identificationRepository){
        this.identificationRepository=identificationRepository;
    }

    @Override
    public Optional<Identification> getIdentification(Long identificationId) {
        return identificationRepository.findById(identificationId);
    }
}
