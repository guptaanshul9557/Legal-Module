package org.egov.legal.service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    void saveDocuments(MultipartFile file, String referenceId);
}

