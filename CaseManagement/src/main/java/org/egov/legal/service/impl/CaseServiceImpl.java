package org.egov.legal.service.impl;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.egov.legal.dto.request.CaseFilingRequest;
import org.egov.legal.enums.ScrutinyStatus;
import org.egov.legal.event.CaseFiledEvent;
import org.egov.legal.event.DocumentPayload;
import org.egov.legal.kafka.CaseEventProducer;
import org.egov.legal.service.CaseService;
import org.egov.legal.service.storage.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CaseServiceImpl implements CaseService {

	private static final Logger log = LoggerFactory.getLogger(CaseService.class);
	private static final String SCRUTINY_STATUS = "Pending";
	
	private final CaseEventProducer producer;
	private final FileStorageService fileStorageService;
	

	public CaseServiceImpl(CaseEventProducer producer,FileStorageService fileStorageService) {
		this.producer = producer;
		this.fileStorageService=fileStorageService;
	}

	@Override
	public String fileCase(CaseFilingRequest req,List<MultipartFile> files) {
		log.info("Inside CaseServiceImpl --> fileCase() :: JSON : {}", req.toString());
		String diaryNumber = null;
		try {
		 // 1. Save case + document metadata
	     diaryNumber = saveCaseMetadata(req);
	    
	 // 2. Save files locally
	    if (files != null && !files.isEmpty()) {
	        for (MultipartFile file : files) {
	            fileStorageService.saveDocuments(file, diaryNumber);
	        }
	    }
		}catch(Exception ex) {
	    	ex.printStackTrace();
	    	log.error("Error in CaseServiceImpl --> fileCase()  :: {}",ex.getMessage());
	    }
		
	    log.info("Exiting CaseServiceImpl --> fileCase()");
		return diaryNumber;

	}

	
	private String saveCaseMetadata(CaseFilingRequest req) {
		log.info("Inside CaseServiceImpl --> saveCaseMetadata()");
		String diaryNumber = null;
		try {
			/*
			 * Add NUll check for Ids
			 *
			 *
			 *
			 *
			 */
			List<String> advocateIds = req.advocate().stream().map(a -> a.advocateId()).toList();
			
			diaryNumber = UUID.randomUUID().toString(); // should have another service
			
			List<DocumentPayload> documentPayloads = req.documents().stream()
					.map(d -> new DocumentPayload(d.fileName(), d.documentUid(), d.fileStoreId(), d.documentType()))
					.toList();
			CaseFiledEvent event = new CaseFiledEvent(UUID.randomUUID(), req.tenantId(), diaryNumber, req.caseType(),
					req.caseCategory(), req.courtType(), req.courtCode(), req.caseDetails().title(),
					req.caseDetails().description(), req.caseDetails().department(),

					// petitioner
					req.petitioner().petitionerType(), req.petitioner().name(), req.petitioner().address(),
					req.petitioner().contactNumber(), req.petitioner().email(),

					advocateIds, req.ulbOfficer().officerId(), documentPayloads, SCRUTINY_STATUS, false, OffsetDateTime.now(),
					1);

				
            //sending to kafka
			producer.publishCaseFiled(event);

			log.info("Exiting CaseServiceImpl --> saveCaseMetadata() :: Case filed successfully. Tenant={}, Diary={}", req.tenantId(), event.diaryNumber());
//			 return diaryNumber;
		} catch (Exception ex) {
			log.error("System error while filing case --> {}", ex);
			ex.printStackTrace();
		}
		return diaryNumber;
	}
}
