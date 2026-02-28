package org.egov.legal.controller;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.egov.legal.dto.request.CaseFilingRequest;
import org.egov.legal.dto.response.ApiResponse;
import org.egov.legal.service.CaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/cases")
public class CaseController {
	
	private static final Logger log = LoggerFactory.getLogger(CaseController.class);

	private final CaseService caseService;

	public CaseController(CaseService caseService) {
		this.caseService = caseService;
	}

	/**
	 * STEP 1: Case Filing (Diary creation)
	 */
	@PostMapping(value="/fileCase",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<Map<String, String>>> fileCase( @RequestPart("caseData") CaseFilingRequest request,
	        @RequestPart(value = "files", required = false) List<MultipartFile> files) {
	    Function<CaseFilingRequest, ResponseEntity<ApiResponse<Map<String, String>>>> fileCaseFunction = req -> {
	        String diaryNumber = null;
	        try {
	            diaryNumber = caseService.fileCase(req,files);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        
	        return ResponseEntity.accepted()
	            .body(ApiResponse.success("Case filed successfully", 
	                Map.of("diaryNumber", diaryNumber != null ? diaryNumber : "")));
	    };
	    
	    return fileCaseFunction.apply(request);
	}


}
