package org.egov.lm.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egov.lm.models.Advocate;
import org.egov.lm.models.Document;
import org.egov.lm.models.Petitioner;
import org.egov.lm.models.Respondent;
import org.egov.lm.web.contracts.CaseRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.egov.tracer.model.CustomException;

public class CaseValidator {
	
		public final String emailReg = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

	   public void validateCreateRequest(CaseRequest request) {

	        Map<String, String> errorMap = new HashMap<>();

	        // Validate mandatory fields for creation
	        if (StringUtils.isEmpty(request.getCases().getTenantId()))
	            errorMap.put("TENANT_ID_MANDATORY", "Tenant ID is mandatory for case creation");

	        // Validate case specific fields
	        validateCaseFields(request, errorMap);

	        // Validate parties (advocates, petitioners, respondents)
	        validateParties(request, errorMap);

	        // Validate documents
	        validateDocuments(request, errorMap);

	        // If there are any validation errors, throw exception
	        if (!errorMap.isEmpty()) {
	            throw new CustomException(errorMap);
	        }
	    }

	    /**
	     * Validates the case specific fields such as caseType, caseCategory, title, etc.
	     * @param request Case object to validate
	     * @param errorMap A map of errors to be thrown in case of validation failures
	     */
	    private void validateCaseFields(CaseRequest request, Map<String, String> errorMap) {

	        if (StringUtils.isEmpty(request.getCases().getCaseType()))
	            errorMap.put("CASE_TYPE_MANDATORY", "Case Type is mandatory for case creation");

	        if (StringUtils.isEmpty(request.getCases().getCaseCategory()))
	            errorMap.put("CASE_CATEGORY_MANDATORY", "Case Category is mandatory for case creation");

	        if (StringUtils.isEmpty(request.getCases().getTitle()))
	            errorMap.put("TITLE_MANDATORY", "Title is mandatory for case creation");

	        if (StringUtils.isEmpty(request.getCases().getDepartment()))
	            errorMap.put("DEPARTMENT_MANDATORY", "Department is mandatory for case creation");
	    }

	    /**
	     * Validates the parties associated with the case (advocates, petitioners, and respondents).
	     * @param request Case object containing party information
	     * @param errorMap A map of errors to be thrown in case of validation failures
	     */
	    private void validateParties(CaseRequest request, Map<String, String> errorMap) {

	        List<Advocate> advocates = request.getCases().getAdvocates();
	        List<Petitioner> petitioners = request.getCases().getPetitioners();
	        List<Respondent> respondents = request.getCases().getRespondents();

	        if (CollectionUtils.isEmpty(advocates)) {
	            errorMap.put("ADVOCATE_MANDATORY", "At least one advocate must be provided for case creation");
	        } else {
	            for (Advocate advocate : advocates) {
	                // Validate advocate fields
	                validateAdvocate(advocate, errorMap);
	            }
	        }

	        if (CollectionUtils.isEmpty(petitioners)) {
	            errorMap.put("PETITIONER_MANDATORY", "At least one petitioner must be provided for case creation");
	        } else {
	            for (Petitioner petitioner : petitioners) {
	                // Validate petitioner fields
	                validatePetitioner(petitioner, errorMap);
	            }
	        }

	        if (CollectionUtils.isEmpty(respondents)) {
	            errorMap.put("RESPONDENT_MANDATORY", "At least one respondent must be provided for case creation");
	        } else {
	            for (Respondent respondent : respondents) {
	                // Validate respondent fields
	                validateRespondent(respondent, errorMap);
	            }
	        }
	    }
	    
	    private void validateAdvocate(Advocate advocate, Map<String, String> errorMap) {
	    	// Validate advocateId: Check if it's not blank (NotBlank annotation already checks this)
	        if (StringUtils.isEmpty(advocate.getAdvocateId())) {
	            errorMap.put("ADVOCATE_ID_MANDATORY", "Advocate ID is mandatory");
	        }

	        // Validate name: Check if it's not blank
	        if (StringUtils.isEmpty(advocate.getName())) {
	            errorMap.put("ADVOCATE_NAME_MANDATORY", "Advocate name is mandatory");
	        }

	        // Validate mobile number: Ensure it matches a valid phone number format
	        if (StringUtils.isEmpty(advocate.getMobileNumber()) || !advocate.getMobileNumber().matches("\\d{10}")) {
	            errorMap.put("ADVOCATE_MOBILE_INVALID", "Advocate mobile number must be a valid 10-digit number");
	        }

	        // Validate email: Check if it's a valid email format
	        if (StringUtils.hasText(advocate.getEmail()) && !advocate.getEmail().matches(emailReg)) {
	            errorMap.put("ADVOCATE_EMAIL_INVALID", "Advocate email must be a valid email format");
	        }

	        // Validate bar registration number: If it's provided, it shouldn't be empty
	        if (StringUtils.hasText(advocate.getBarRegistrationNumber()) && advocate.getBarRegistrationNumber().trim().isEmpty()) {
	            errorMap.put("ADVOCATE_BAR_REGISTRATION_INVALID", "Advocate Bar Registration Number, if provided, cannot be empty");
	        }

	        // Optional: Validate the government advocate flag if it's required
	        if (advocate.getIsGovernmentAdvocate() == null) {
	            errorMap.put("ADVOCATE_IS_GOVERNMENT_FLAG_MANDATORY", "Is Government Advocate flag is mandatory");
	        }
	    }

	    private void validatePetitioner(Petitioner petitioner, Map<String, String> errorMap) {
	    	// Validate petitionerId: Check if it's not blank
	        if (StringUtils.isEmpty(petitioner.getPetitionerId())) {
	            errorMap.put("PETITIONER_ID_MANDATORY", "Petitioner ID is mandatory");
	        }

	        // Validate name: Check if it's not blank
	        if (StringUtils.isEmpty(petitioner.getName())) {
	            errorMap.put("PETITIONER_NAME_MANDATORY", "Petitioner name is mandatory");
	        }

	        // Validate type: Ensure it is either INDIVIDUAL, ORGANIZATION, or DEPARTMENT
//	        if (StringUtils.isEmpty(petitioner.getType())) {
//	            errorMap.put("PETITIONER_TYPE_MANDATORY", "Petitioner type is mandatory");
//	        } else if (!petitioner.getType().equalsIgnoreCase("INDIVIDUAL") && 
//	                   !petitioner.getType().equalsIgnoreCase("ORGANIZATION") && 
//	                   !petitioner.getType().equalsIgnoreCase("DEPARTMENT")) {
//	            errorMap.put("PETITIONER_TYPE_INVALID", "Petitioner type must be either INDIVIDUAL, ORGANIZATION, or DEPARTMENT");
//	        }

	        // Validate mobile number: Ensure it matches a valid phone number format 
	        if (StringUtils.isEmpty(petitioner.getMobileNumber()) || !petitioner.getMobileNumber().matches("\\d{10}")) {
	            errorMap.put("PETITIONER_MOBILE_INVALID", "Petitioner mobile number must be a valid 10-digit number");
	        }

	        // Validate email: Check if it's a valid email format 
	        if (StringUtils.hasText(petitioner.getEmail()) && !petitioner.getEmail().matches(emailReg)) {
	            errorMap.put("PETITIONER_EMAIL_INVALID", "Petitioner email must be a valid email format");
	        }
	    }

	    private void validateRespondent(Respondent respondent, Map<String, String> errorMap) {
	    	 // Validate respondentId: Check if it's not blank
	        if (StringUtils.isEmpty(respondent.getRespondentId())) {
	            errorMap.put("RESPONDENT_ID_MANDATORY", "Respondent ID is mandatory");
	        }

	        // Validate name: Check if it's not blank 
	        if (StringUtils.isEmpty(respondent.getName())) {
	            errorMap.put("RESPONDENT_NAME_MANDATORY", "Respondent name is mandatory");
	        }

	        // Validate type: Ensure it is either INDIVIDUAL, DEPARTMENT, or ORGANIZATION
//	        if (StringUtils.isEmpty(respondent.getType())) {
//	            errorMap.put("RESPONDENT_TYPE_MANDATORY", "Respondent type is mandatory");
//	        } else if (!respondent.getType().equalsIgnoreCase("INDIVIDUAL") && 
//	                   !respondent.getType().equalsIgnoreCase("DEPARTMENT") && 
//	                   !respondent.getType().equalsIgnoreCase("ORGANIZATION")) {
//	            errorMap.put("RESPONDENT_TYPE_INVALID", "Respondent type must be either INDIVIDUAL, DEPARTMENT, or ORGANIZATION");
//	        }

	        // Validate department: Ensure it's provided if the respondent type is "DEPARTMENT"
	        if ("DEPARTMENT".equalsIgnoreCase(respondent.getType()) && StringUtils.isEmpty(respondent.getDepartment())) {
	            errorMap.put("RESPONDENT_DEPARTMENT_MANDATORY", "Department must be provided for Respondent type 'DEPARTMENT'");
	        }

	        // Validate mobile number: Ensure it matches a valid phone number format
	        if (StringUtils.isEmpty(respondent.getMobileNumber()) || !respondent.getMobileNumber().matches("\\d{10}")) {
	            errorMap.put("RESPONDENT_MOBILE_INVALID", "Respondent mobile number must be a valid 10-digit number");
	        }

	        // Validate email: Check if it's a valid email format 
	        if (StringUtils.hasText(respondent.getEmail()) && !respondent.getEmail().matches(emailReg)) {
	            errorMap.put("RESPONDENT_EMAIL_INVALID", "Respondent email must be a valid email format");
	        }

	        // Validate address: Ensure it's not null and contains valid data 
	        if (respondent.getAddress() == null) {
	            errorMap.put("RESPONDENT_ADDRESS_MANDATORY", "Respondent address is mandatory");
	        }
	    }

	    /**
	     * Validates the documents related to the case.
	     * @param request Case object containing document information
	     * @param errorMap A map of errors to be thrown in case of validation failures
	     */
	    private void validateDocuments(CaseRequest request, Map<String, String> errorMap) {

	        List<Document> documents = request.getCases().getDocuments();

	        if (CollectionUtils.isEmpty(documents))
	            errorMap.put("DOCUMENTS_MANDATORY", "At least one document must be provided for case creation");
	    }
}
