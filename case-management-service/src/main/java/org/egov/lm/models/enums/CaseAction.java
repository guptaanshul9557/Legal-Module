package org.egov.lm.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CaseAction {

	    INITIATE("INITIATE"),
	    REGISTER("REGISTER"),
	    SCHEDULE_CASE("SCHEDULE_CASE"),
	    ADD_INTERIM_ORDER("ADD_INTERIM_ORDER"),
	    SETTLE("SETTLE"),
	    CLOSE("CLOSE"),
	    REJECT("REJECT");

	    private final String value;

	    CaseAction(String value) {
	        this.value = value;
	    }

	    @JsonValue
	    public String getValue() {
	        return value;
	    }

	    @Override
	    public String toString() {
	        return value;
	    }

	    @JsonCreator
	    public static CaseAction fromValue(String text) {
	        for (CaseAction action : CaseAction.values()) {
	            if (action.value.equalsIgnoreCase(text)) {
	                return action;
	            }
	        }
	        throw new IllegalArgumentException("Invalid CaseAction: " + text);
	    }
}
