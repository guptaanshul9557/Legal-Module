package org.egov.legal.service.storage.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.egov.legal.service.CaseService;
import org.egov.legal.service.storage.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Profile("dev")
public class LocalFileStorageService implements FileStorageService {

	private static final Logger log = LoggerFactory.getLogger(LocalFileStorageService.class);

	@Value("${file.storage.local.base-path}")
	private String BASE_DIR;

	@Override
	public void saveDocuments(MultipartFile file, String referenceId) {
		log.info("Inside LocalFileStorageService --> saveDocuments() :: Diary no. --> {}", referenceId);
		try {
			Path dir = Paths.get(BASE_DIR, referenceId);
			Files.createDirectories(dir);

			Path filePath = dir.resolve(System.currentTimeMillis() + "_" + file.getOriginalFilename());

			Files.write(filePath, file.getBytes());
			log.info(
					"Exiting LocalFileStorageService --> saveDocuments() :: Document saved successfully ::  Diary no. --> {}",
					referenceId);
		} catch (IOException e) {
			log.error("Error while saving document for Diary no. --> {} with error --> {}", referenceId,
					e.getMessage());
			e.printStackTrace();
		}
	}
}
