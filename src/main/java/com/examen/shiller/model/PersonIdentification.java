package com.examen.shiller.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "person_identification", schema = "person_schema")
public class PersonIdentification {

    public PersonIdentification(){}

    public PersonIdentification(String identificationNumber) {
        this.identificationNumber = identificationNumber;
        this.active=true;
        this.createdAt=new Timestamp(new Date().getTime());
    }

    @Column(name="person_identification_id", nullable=false, unique=true)
    @Id
    @GeneratedValue(generator="PERSON_IDENTIFICATION_ID_GENERATOR")
    @org.hibernate.annotations.GenericGenerator(name="PERSON_IDENTIFICATION_ID_GENERATOR", strategy="native")
    private Long personIdentificationId;

    @ManyToOne(targetEntity=Person.class, fetch= FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumns(value={ @JoinColumn(name="person_id", referencedColumnName="person_id", nullable=false) })
    private Person person;

    @ManyToOne(targetEntity=Identification.class, fetch= FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumns(value={ @JoinColumn(name="identification_id", referencedColumnName="identification_id", nullable=false) })
    private Identification identification;

    private String identificationNumber;
    private Boolean active;
    private Timestamp createdAt;


}
