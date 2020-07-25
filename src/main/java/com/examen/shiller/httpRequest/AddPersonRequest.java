package com.examen.shiller.httpRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddPersonRequest {

    private Long person_id;
    private String name;
    private Integer age;
    private String gender;

}
