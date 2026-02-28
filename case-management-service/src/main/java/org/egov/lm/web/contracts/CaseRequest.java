package org.egov.lm.web.contracts;

import javax.validation.Valid;

import org.egov.common.contract.request.RequestInfo;
import org.egov.lm.models.Case;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaseRequest {
	
	 @JsonProperty("RequestInfo")
	  private RequestInfo requestInfo;
	 
	 @JsonProperty("case")
	  @Valid
	  private Case cases;

}
