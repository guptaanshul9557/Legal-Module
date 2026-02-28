package org.egov.legal.entity;

import org.egov.legal.enums.PartyRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

//@Entity
//@Table(name = "case_parties")
public class CaseParty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Case courtCase;

    @ManyToOne(optional = false)
    private Party party;

    @Enumerated(EnumType.STRING)
    private PartyRole role;

    private Integer sequenceNo;

    private Boolean mainParty = false;
}
