package org.egov.legal.service;

import java.util.List;

import org.egov.legal.dto.request.CaseFilingRequest;
import org.springframework.web.multipart.MultipartFile;

public interface CaseService {

	String fileCase(CaseFilingRequest req, List<MultipartFile> files);

}

