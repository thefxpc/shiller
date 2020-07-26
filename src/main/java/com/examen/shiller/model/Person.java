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
@Table(name = "person", schema = "person_schema")
public class Person {

    public Person(){}

    public Person(@NonNull String name,@NonNull Integer age, String gender) {
        this.name = name;
        this.age = age;

        if(gender==null||gender.equals("")) this.gender="";
        else this.gender = gender;
        this.code=this.getAlphaNumericString(10);

        this.active=true;
        this.createdAt=new Timestamp(new Date().getTime());

    }

    public Person(String name, Integer age, String gender, String code, Timestamp createdAt, Boolean active) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.code = code;
        this.createdAt = createdAt;
        this.active = active;
    }

    @Column(name="person_id", nullable=false, unique=true)
    @Id
    @GeneratedValue(generator="PERSON_ID_GENERATOR")
    @org.hibernate.annotations.GenericGenerator(name="PERSON_ID_GENERATOR", strategy="native")
    private Long personId;
    private String name;
    private Integer age;
    @Column(length = 1)
    private String gender;
    private String code;
    private Timestamp createdAt;
    private Boolean active;

    @JsonIgnore
    @OneToMany(mappedBy="person",
            cascade = CascadeType.ALL,
            targetEntity=PersonIdentification.class,fetch = FetchType.LAZY)
    private Set<PersonIdentification> personIdentifications;


    String getAlphaNumericString(int n)
    {
        // chose a Character random from this String
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            // add Character one by one in end of sb
            sb.append(alphaNumericString
                    .charAt((int) (alphaNumericString.length() * Math.random())));
        }
        return sb.toString();
    }
}
