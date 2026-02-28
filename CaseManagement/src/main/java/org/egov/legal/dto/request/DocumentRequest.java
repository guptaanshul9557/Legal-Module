package org.egov.legal.dto.request;


public record DocumentRequest(
        String fileName,
        String documentUid,
        String fileStoreId,
        String documentType
) {}
