package org.egov.legal.event;

public record DocumentPayload(
        String fileName,
        String documentUid,
        String fileStoreId,
        String documentType
) {}
