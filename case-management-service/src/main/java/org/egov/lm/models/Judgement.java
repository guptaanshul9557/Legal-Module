package org.egov.lm.models;

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
public class Judgement {

	    @JsonProperty("judgementId")
	    private String judgementId;

	    @JsonProperty("remark")
	    private String remark;

	    @JsonProperty("orderDetail")
	    private String orderDetail;
}
