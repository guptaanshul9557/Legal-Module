package org.egov.legal.infra.mdms.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.validation.Valid;

import org.egov.common.contract.request.RequestInfo;
import org.egov.legal.infra.mdms.service.MDMSService;
import org.egov.mdms.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;

@RestController
@Slf4j
@RequestMapping(value = "/v1")
public class MDMSLegalController {
	
	@Autowired
	private MDMSService mdmsService;
	
    @PostMapping("_search")
    @ResponseBody
	private ResponseEntity<?> search(@RequestBody @Valid MdmsCriteriaReq mdmsCriteriaReq) {
//		fetch master data based on tenant, module and master details
		Map<String, Map<String, JSONArray>> response = mdmsService.searchMaster(mdmsCriteriaReq);
		
//		Wrapping it into standard MDMS response object
		MdmsResponse mdmsResponse = new MdmsResponse();
		mdmsResponse.setMdmsRes(response);
		
		return new ResponseEntity<>(mdmsResponse, HttpStatus.OK);
	}
	
	 @PostMapping("_get")
	    @ResponseBody
	    private ResponseEntity<?> search(
	            @RequestParam("moduleName") String module,        
	            @RequestParam("masterName") String master,       
	            @RequestParam(value = "filter", required = false) String filter, 
	            @RequestParam("tenantId") String tenantId,        
	            @RequestBody RequestInfo requestInfo) {

	        // Log request for debugging
	        System.out.println("MDMSController mdmsCriteriaReq [" + module + ", " + master + ", " + filter + "]");

	        // Build MDMS request programmatically
	        MdmsCriteriaReq mdmsCriteriaReq = new MdmsCriteriaReq();
	        mdmsCriteriaReq.setRequestInfo(requestInfo);

	        // Set tenant information
	        MdmsCriteria criteria = new MdmsCriteria();
	        criteria.setTenantId(tenantId);

	        // Define module details
	        ModuleDetail detail = new ModuleDetail();
	        detail.setModuleName(module);

	        // Define master details (single master)
	        MasterDetail masterDetail = new MasterDetail();
	        masterDetail.setName(master);
	        masterDetail.setFilter(filter);

	        // Attach master to module
	        ArrayList<MasterDetail> masterList = new ArrayList<>();
	        masterList.add(masterDetail);
	        detail.setMasterDetails(masterList);

	        // Attach module to criteria
	        ArrayList<ModuleDetail> moduleList = new ArrayList<>();
	        moduleList.add(detail);
	        criteria.setModuleDetails(moduleList);

	        // Attach criteria to request
	        mdmsCriteriaReq.setMdmsCriteria(criteria);

	        // Fetch master data using common service
	        Map<String, Map<String, JSONArray>> response =
	                mdmsService.searchMaster(mdmsCriteriaReq);

	        // Prepare standard response
	        MdmsResponse mdmsResponse = new MdmsResponse();
	        mdmsResponse.setMdmsRes(response);

	        return new ResponseEntity<>(mdmsResponse, HttpStatus.OK);
	    }
}
