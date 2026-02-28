package org.egov.lm.web.controllers;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.egov.common.contract.response.ResponseInfo;
import org.egov.lm.models.Case;
import org.egov.lm.models.CaseCriteria;
import org.egov.lm.service.CaseService;
import org.egov.lm.util.ResponseInfoFactory;
import org.egov.lm.web.contracts.CaseRequest;
import org.egov.lm.web.contracts.CaseResponse;
import org.egov.lm.web.contracts.RequestInfoWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/case")
public class CaseController {

	@Autowired
	private CaseService caseService;

	@Autowired
	private ResponseInfoFactory responseInfoFactory;


	@PostMapping("/_create")
	public ResponseEntity<CaseResponse> create(@Valid @RequestBody CaseRequest caseRequest) {

		Case cases = caseService.fileCase(caseRequest);
		ResponseInfo resInfo = responseInfoFactory.createResponseInfoFromRequestInfo(caseRequest.getRequestInfo(), true);
		CaseResponse response = CaseResponse.builder()
				.cases(Arrays.asList(cases))
				.responseInfo(resInfo)
				.build();
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	
	@PostMapping("/_update")
	public ResponseEntity<CaseResponse> update(@Valid @RequestBody CaseRequest caseRequest) {
		
		Case cases = caseService.updateCase(caseRequest);
		ResponseInfo resInfo = responseInfoFactory.createResponseInfoFromRequestInfo(caseRequest.getRequestInfo(), true);
		CaseResponse response = CaseResponse.builder()
				.cases(Arrays.asList(cases))
				.responseInfo(resInfo)
				.build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/_search")
	public ResponseEntity<CaseResponse> search(@Valid @RequestBody RequestInfoWrapper requestInfoWrapper,
			@Valid @ModelAttribute CaseCriteria caseCriteria) {
		
//		propertyValidator.validatePropertyCriteria(caseCriteria, requestInfoWrapper.getRequestInfo());
		List<Case> cases = caseService.searchCases(caseCriteria,requestInfoWrapper.getRequestInfo());
		CaseResponse response = CaseResponse.builder().cases(cases).responseInfo(
				responseInfoFactory.createResponseInfoFromRequestInfo(requestInfoWrapper.getRequestInfo(), true))
				.build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}


}