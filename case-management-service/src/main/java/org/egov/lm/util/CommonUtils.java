package org.egov.lm.util;

import org.egov.lm.config.CaseConfiguration;
import org.egov.lm.models.AuditDetails;
import org.egov.lm.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

@Getter
public class CommonUtils {

	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private CaseConfiguration configs;
	
    @Autowired
    private ServiceRequestRepository restRepo;

  
    /**
     * Method to return auditDetails for create/update flows
     *
     * @param by
     * @param isCreate
     * @return AuditDetails
     */
    public AuditDetails getAuditDetails(String by, Boolean isCreate) {
    	
        Long time = System.currentTimeMillis();
        
        if(isCreate)
            return AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time).build();
        else
            return AuditDetails.builder().lastModifiedBy(by).lastModifiedTime(time).build();
    }
    
}
