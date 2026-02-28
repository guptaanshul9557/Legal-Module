package org.egov.legal.dto.request;

import java.util.List;

public record CaseFilingRequest(String tenantId,

		// case classification
		String caseType, // CIVIL / CRIMINAL / WRIT
		String caseCategory, // LAND / SERVICE / TAX

		// COURT SELECTION (MANDATORY)
		String courtType, // DISTRICT / HIGH_COURT / TRIBUNAL
		String courtCode, // optional at filing (e.g. DIST-01)

		CaseDetails caseDetails,

		List<AdvocateRef> advocate,
		UlbOfficerRef ulbOfficer,
		PetitionerRequest petitioner,
		List<DocumentRequest> documents) {
}
