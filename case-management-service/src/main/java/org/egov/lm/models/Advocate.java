package org.egov.lm.models;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.actuate.audit.AuditEventRepository;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Advocate {

    @JsonProperty("advocateId")
    private String advocateId;

    @JsonProperty("name")
    @NotBlank(message = "advocate name is mandatory")
    private String name;

    @JsonProperty("barRegistrationNumber")
    private String barRegistrationNumber;

    @JsonProperty("mobileNumber")
    private String mobileNumber;
    
    @JsonProperty("activeCaseCount")
    private int activeCaseCount;
    
    @JsonProperty("role")
    private String role;

    @JsonProperty("email")
    private String email;

    @JsonProperty("isGovernmentAdvocate")
    private Boolean isGovernmentAdvocate;
    
    @JsonProperty("auditDetails")
	private AuditDetails auditDetails;
}
