package org.egov.legal.entity;

import java.time.LocalDate;

import org.egov.legal.enums.AdvocateRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

//@Entity
//@Table(name = "case_counsel")
public class CaseCounsel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Case courtCase;

    @ManyToOne
    private Party party;

    @ManyToOne
    private Advocate advocate;

    @Enumerated(EnumType.STRING)
    private AdvocateRole advocateRole;

    private LocalDate appearanceDate;
}

