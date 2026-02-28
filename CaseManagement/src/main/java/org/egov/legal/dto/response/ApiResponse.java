package org.egov.legal.dto.response;

import java.time.OffsetDateTime;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data,
        OffsetDateTime timestamp
) {

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(
                true,
                message,
                data,
                OffsetDateTime.now()
        );
    }

    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(
                false,
                message,
                null,
                OffsetDateTime.now()
        );
    }
}

