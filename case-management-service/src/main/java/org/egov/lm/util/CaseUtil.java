package org.egov.lm.util;

import java.util.Arrays;

import org.egov.lm.config.CaseConfiguration;
import org.egov.lm.models.Case;
import org.egov.lm.models.enums.CaseAction;
import org.egov.lm.models.workflow.ProcessInstance;
import org.egov.lm.models.workflow.ProcessInstanceRequest;
import org.egov.lm.web.contracts.CaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CaseUtil extends CommonUtils {
	
	@Autowired
	private CaseConfiguration configs;
	
	
	
public ProcessInstanceRequest initiateCaseWorkFlow(CaseRequest request) {
		
	   Case legalCase = request.getCases();

	    ProcessInstance wf = legalCase.getWorkflow() != null
	            ? legalCase.getWorkflow()
	            : new ProcessInstance();;
	
	            wf.setBusinessId(legalCase.getCaseId()); 
	            wf.setTenantId(legalCase.getTenantId());
	            wf.setBusinessService(configs.getCreateLMWfName());
	            wf.setModuleName(configs.getLegalModuleName());
	            wf.setAction(CaseAction.INITIATE.getValue());

	            legalCase.setWorkflow(wf);

	            return ProcessInstanceRequest.builder()
	                    .processInstances(Arrays.asList(wf))
	                    .requestInfo(request.getRequestInfo())
	                    .build();
	        }

}
