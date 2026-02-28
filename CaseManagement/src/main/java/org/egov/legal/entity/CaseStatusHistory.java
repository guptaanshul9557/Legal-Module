package org.egov.legal.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.egov.legal.enums.CaseStatus;
import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.*;

//@Entity
//@Table(name = "case_status_history")
public class CaseStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "case_id")
    private Case courtCase;

    @Enumerated(EnumType.STRING)
    private CaseStatus oldStatus;

    @Enumerated(EnumType.STRING)
    private CaseStatus newStatus;

    private String remarks;

    private LocalDateTime changedAt;

    private String changedBy;
}

