package org.egov.lm.models;

import java.util.Set;

import org.egov.lm.models.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaseCriteria {

    /* -------------------- Mandatory Scope -------------------- */

    private String tenantId;

    /* -------------------- Identifiers -------------------- */

    private Set<String> caseIds;

    private Set<String> acknowledgementIds;

    private Set<String> advocateIds;

    private Set<String> petitionerIds;

    private Set<String> respondentIds;

    /* -------------------- Case Classification -------------------- */

    private Set<String> caseTypes;

    private Set<String> caseCategories;

    private String department;

    private String status;
    /* -------------------- Court & Hearing -------------------- */

    private String courtType;

    private String courtName;

    private Long fromHearingDate;

    private Long toHearingDate;
    
    private Boolean hasJudgement;


    /* -------------------- Pagination & Audit -------------------- */

    private boolean audit;

    private Long offset;

    private Long limit;
}

