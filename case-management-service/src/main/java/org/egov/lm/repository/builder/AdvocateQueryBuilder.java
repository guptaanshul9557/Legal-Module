package org.egov.lm.repository.builder;

import java.util.*;

import org.egov.lm.models.Advocate;
import org.egov.lm.web.contracts.CaseRequest;
import org.springframework.stereotype.Component;

@Component
public class AdvocateQueryBuilder {
	
	private static final String CASE_ADVOCATE_SELECT =
	        "SELECT  advocateid, role,activecasecount " +
	        "createdby, createdtime, lastmodifiedby, lastmodifiedtime " +
	        "FROM eg_lm_case_advocate where ";

	
	public String fetchCaseAdvocates(CaseRequest caseRequest, List<Object> params) {

	    StringBuilder query = new StringBuilder();
	    query.append(CASE_ADVOCATE_SELECT);
	    
	    if (caseRequest.getCases().getCaseId() != null) {
	        query.append(" caseid = ?");
	        params.add(caseRequest.getCases().getCaseId());
	    }

	    query.append(" ORDER BY lastmodifiedtime DESC");

	    return query.toString(); 
	}

}
