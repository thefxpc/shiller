package com.examen.shiller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "identification", schema = "identification_schema")
public class Identification {

    @Column(name="identification_id", nullable=false, unique=true)
    @Id
    @GeneratedValue(generator="IDENTIFICATION_ID_GENERATOR")
    @org.hibernate.annotations.GenericGenerator(name="IDENTIFICATION_ID_GENERATOR", strategy="native")
    private Long identificationId;
    private String identificationName;
    private String description;
    private Timestamp createdAt;
    private Boolean active;

    public Identification(){}

    public Identification(@NonNull String identificationName, @NonNull String description) {
        this.identificationName = identificationName;
        this.description = description;
        this.active=true;
        this.createdAt=new Timestamp(new Date().getTime());
    }

    public Identification(String identificationName, String description, Timestamp createdAt, Boolean active) {
        this.identificationName = identificationName;
        this.description = description;
        this.createdAt = createdAt;
        this.active = active;
    }

    @JsonIgnore
    @OneToMany(mappedBy="identification",
            cascade = CascadeType.ALL,
            targetEntity=PersonIdentification.class,fetch = FetchType.LAZY)
    private Set<PersonIdentification> personIdentifications;

}
