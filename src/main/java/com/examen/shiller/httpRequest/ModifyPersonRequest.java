package com.examen.shiller.httpRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@ApiModel(description = "Data structure to modify an existing person")
public class ModifyPersonRequest {
    private static final String PERSON_ID_MESSAGE="The id of the existing person is mandatory";
    private static final String NAME_MESSAGE="Name should have at least 2 characters";
    private static final String AGE_MESSAGE="Age should not be less than 18 and greater than 150";
    private static final String GENDER_MESSAGE="Available gender values are M,H or \" \"";

    @ApiModelProperty(notes = PERSON_ID_MESSAGE)
    private Long person_id;

    @Size(min = 2,max = 250,message = NAME_MESSAGE)
    @ApiModelProperty(notes = NAME_MESSAGE)
    private String name;

    @Min(value = 18, message = AGE_MESSAGE)
    @Max(value = 150, message = AGE_MESSAGE)
    @ApiModelProperty(notes = AGE_MESSAGE)
    private Integer age;

    @Size(min = 1,max = 1,message = GENDER_MESSAGE)
    @ApiModelProperty(notes = GENDER_MESSAGE)
    private String gender;


    @AssertTrue(message = GENDER_MESSAGE)
    private boolean isOk() {
        return this.gender.equals("M")|| this.gender.equals("H") || this.gender.equals(" ");
    }
}
