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

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Petitioner {

    @JsonProperty("petitionerId")
    private String petitionerId;

    @JsonProperty("name")
    @NotBlank(message = "petitioner name is mandatory")
    private String name;

    @JsonProperty("type")
    private String type; // INDIVIDUAL / ORGANIZATION / DEPARTMENT

    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @JsonProperty("email")
    private String email;
}
