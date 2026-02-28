package org.egov.lm.repository.aggregate;

import java.util.*;

import org.egov.lm.models.*;

public class CaseAggregate {

    private final Case base;

    private final Map<String, Advocate> advocates = new LinkedHashMap<>();
    private final Map<String, Petitioner> petitioners = new LinkedHashMap<>();
    private final Map<String, Respondent> respondents = new LinkedHashMap<>();
    private final Map<String, Document> documents = new LinkedHashMap<>();

    public CaseAggregate(Case base) {
        this.base = base;
    }

    /* -------------------- Adders (O(1)) -------------------- */

    public void addAdvocate(Advocate advocate) {
        if (advocate == null || advocate.getAdvocateId() == null) return;
        advocates.putIfAbsent(advocate.getAdvocateId(), advocate);
    }

    public void addPetitioner(Petitioner petitioner) {
        if (petitioner == null || petitioner.getPetitionerId() == null) return;
        petitioners.putIfAbsent(petitioner.getPetitionerId(), petitioner);
    }

    public void addRespondent(Respondent respondent) {
        if (respondent == null || respondent.getRespondentId() == null) return;
        respondents.putIfAbsent(respondent.getRespondentId(), respondent);
    }

    public void addDocument(Document document) {
        if (document == null || document.getId() == null) return;
        documents.putIfAbsent(document.getId(), document);
    }

    /* -------------------- Finalization -------------------- */

    public Case toCase() {
        base.setAdvocates(new ArrayList<>(advocates.values()));
        base.setPetitioners(new ArrayList<>(petitioners.values()));
        base.setRespondents(new ArrayList<>(respondents.values()));
        base.setDocuments(new ArrayList<>(documents.values()));
        return base;
    }
}
