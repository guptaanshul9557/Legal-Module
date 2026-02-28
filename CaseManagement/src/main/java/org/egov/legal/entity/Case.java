package org.egov.legal.entity;

import java.time.LocalDate;

import org.egov.legal.enums.CaseStatus;
import org.egov.legal.enums.CaseType;
import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.*;

//@Entity
//@Table(name = "cases")
public class Case extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long caseId;

    @Column(name = "diary_number", unique = true, nullable = false)
    private String diaryNumber;

    @Column(name = "case_number", unique = true)
    private String caseNumber;

    @Column(name = "case_year")
    private Integer caseYear;

    @Column(name = "filing_date", nullable = false)
    private LocalDate filingDate;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "case_type", nullable = false)
    private CaseType caseType;

    @Enumerated(EnumType.STRING)
    @Column(name = "case_status", nullable = false)
    private CaseStatus currentStatus;

    @Column(name = "court_id", nullable = false)
    private Long courtId;

    @ManyToAny
    @JoinColumn(name = "parent_case_id")
    private Case parentCase;

    @Column(name = "is_active")
    private Boolean active = true;

    @Column(name = "priority_level")
    private String priorityLevel;

    @Column(name = "is_sensitive")
    private Boolean sensitive = false;

    @Column(name = "is_sealed")
    private Boolean sealed = false;
}
