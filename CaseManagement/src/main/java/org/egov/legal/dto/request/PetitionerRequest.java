package org.egov.legal.dto.request;

public record PetitionerRequest(
        String petitionerType,   // INDIVIDUAL / ORGANIZATION / GOVERNMENT
        String name,
        String address,
        String contactNumber,
        String email
) {} 
