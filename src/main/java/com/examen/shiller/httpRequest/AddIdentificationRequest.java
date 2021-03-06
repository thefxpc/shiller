package com.examen.shiller.httpRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ApiModel(description = "Data structure to add a Identification and person relation")
public class AddIdentificationRequest {

    private static final String PERSON_ID_MESSAGE="The id of an existing person is mandatory";
    private static final String IDENTIFICATION_ID_MESSAGE="The id of an existing identification type is mandatory";
    private static final String IDENTIFICATION_NUMBER_MESSAGE="Identification number is mandatory and should be between 2 and 50 characters long";

    @NotNull(message =PERSON_ID_MESSAGE )
    @ApiModelProperty(notes = PERSON_ID_MESSAGE)
    private  Long person_id;

    @NotNull(message =IDENTIFICATION_ID_MESSAGE )
    @ApiModelProperty(notes = IDENTIFICATION_ID_MESSAGE)
    private  Long identification_id;

    @NotNull(message =IDENTIFICATION_NUMBER_MESSAGE )
    @Size(min = 2,max =50 ,message = IDENTIFICATION_NUMBER_MESSAGE)
    @ApiModelProperty(notes = IDENTIFICATION_NUMBER_MESSAGE)
    private String identificationNumber;
}
