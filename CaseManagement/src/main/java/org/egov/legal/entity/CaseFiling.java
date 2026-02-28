package org.egov.legal.entity;

import java.time.LocalDate;

import org.egov.legal.enums.FilingMode;
import org.egov.legal.enums.ScrutinyStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

//@Entity
//@Table(name = "case_filing")
public class CaseFiling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long filingId;

    @OneToOne
    private Case courtCase;

    @Enumerated(EnumType.STRING)
    private FilingMode filingMode;

    @Enumerated(EnumType.STRING)
    private ScrutinyStatus scrutinyStatus;

    private Integer defectCount;

    private String defectDetails;

    private LocalDate defectNotifiedDate;

    private LocalDate defectClearedDate;

    private Boolean refiled = false;

    private Integer refileCount;
}

