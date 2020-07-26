package com.examen.shiller.httpRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ApiModel(description = "Data structure to modify an existing Identification and person relation")
public class ModifyIdentificationRequest {
    private static final String PERSON_IDENTIFICATION_ID_MESSAGE="In case you want to modify an existing identification this field is mandatory";
    private static final String IDENTIFICATION_NUMBER_MESSAGE="Identification number is mandatory and should be between 2 and 50 characters long";

    @ApiModelProperty(notes = PERSON_IDENTIFICATION_ID_MESSAGE)
    @NotNull(message=PERSON_IDENTIFICATION_ID_MESSAGE)
    private  Long personIdentificationId;

    @NotNull(message =IDENTIFICATION_NUMBER_MESSAGE )
    @Size(min = 2,max =50 ,message = IDENTIFICATION_NUMBER_MESSAGE)
    @ApiModelProperty(notes = IDENTIFICATION_NUMBER_MESSAGE)
    private String identificationNumber;
}
