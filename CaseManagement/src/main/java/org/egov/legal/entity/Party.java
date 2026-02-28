package org.egov.legal.entity;

import org.egov.legal.enums.PartyType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//@Entity
//@Table(name = "parties")
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partyId;

    @Enumerated(EnumType.STRING)
    private PartyType partyType;

    private String name;

    private String fatherOrSpouseName;

    private Integer age;

    private String gender;

    private String address;

    private String state;

    private String mobile;

    private String email;

    private Boolean deceased = false;
}
