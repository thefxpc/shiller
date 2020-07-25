package com.examen.shiller.httpRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddIdentificationRequest {
    private  Long personIdentificationId;
    private  Long person_id;
    private  Long identification_id;
    private String identificationNumber;
}
