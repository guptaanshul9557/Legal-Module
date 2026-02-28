package org.egov.lm.models;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Respondent {

    @JsonProperty("respondentId")
    private String respondentId;

    @JsonProperty("name")
    @NotBlank(message = "respondent name is mandatory")
    private String name;

    @JsonProperty("type")
    private String type; // INDIVIDUAL / DEPARTMENT / ORGANIZATION

    @JsonProperty("department")
    private String department;

    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @JsonProperty("email")
    private String email;

    @JsonProperty("address")
    private Address address;
}
