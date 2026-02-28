package org.egov.legal.event;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.egov.legal.enums.ScrutinyStatus;

public record CaseFiledEvent(
        UUID eventId,

        String tenantId,
        String diaryNumber,

        String caseType,
        String caseCategory,

        String courtType,
        String courtCode,

        String title,
        String description,
        String department,

        // 🔴 PETITIONER DETAILS
        String petitionerType,
        String petitionerName,
        String petitionerAddress,
        String petitionerContact,
        String petitionerEmail,

        List<String> advocateIds,
        String ulbOfficerId,

        List<DocumentPayload> documents,

        String scrutinyStatus,
        boolean registered,

        OffsetDateTime occurredAt,
        int version
) {}
