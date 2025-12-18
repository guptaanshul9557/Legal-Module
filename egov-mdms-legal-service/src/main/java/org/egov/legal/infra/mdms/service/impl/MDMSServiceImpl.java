package org.egov.legal.infra.mdms.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.annotation.PostConstruct;

import org.apache.commons.io.*;
import org.apache.commons.io.FilenameUtils;
import org.egov.legal.infra.mdms.utils.MDMSConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;

@Component
@Slf4j
public class MDMSServiceImpl {
	
	private static final Logger log =
	        LoggerFactory.getLogger(MDMSServiceImpl.class);


	@Autowired
	public ResourceLoader resourceLoader;

	@Value("${egov.mdms.conf.path}")
	public String mdmsFileDirectory;

	@Value("${masters.config.url}")
	public String masterConfigUrl;

	@Value("${egov.mdms.stopOnAnyConfigError:true}")
	public boolean stopOnAnyConfigError;

	private static Map<String, Map<String, Map<String, JSONArray>>> tenantMap = new HashMap<>();

	private static Map<String, Map<String, Object>> masterConfigMap = new HashMap<>();

	public static Map<String, Map<String, Map<String, JSONArray>>> getTenantMap() {
		return tenantMap;
	}
	public static Map<String, Map<String, Object>> getMasterConfigMap() {
		return masterConfigMap;
	}
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	
	@PostConstruct
	public void run() {
//		 importing try and catch 
		try {
//			System.out.println("Reading Files from: " + mdmsFileDirectory);
			LinkedList<String> errorFilesList = new LinkedList<>();
			
//			legal-config 
			readMdmsConfigFiles(masterConfigUrl);
			
//			load all MDMS master files 
			readFiles(mdmsFileDirectory, errorFilesList);
			log.info("MDMS Loaded tenants = {}", tenantMap.keySet());
			
			System.out.println("List OF files which has Error while parsing " +errorFilesList);
			
//			stopping application if any config fails
			if(!errorFilesList.isEmpty() && stopOnAnyConfigError) {
				System.out.println("Stopping as all files couldn't be loaded!");
				System.exit(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Getting, Exceptions while loading yaml files!");
			e.printStackTrace();
		}
	}
	
	public void readFiles(String baseFolderPath, LinkedList<String> errorList) {
		File folder = new File(baseFolderPath);
		
		File[]  listOfFiles = folder.listFiles();
		
		if(listOfFiles != null) {
			for(File file : listOfFiles) {
				if(file.isFile()) {
					String fileExtension = FilenameUtils.getExtension(file.getAbsolutePath().toLowerCase());
					if(fileExtension.equalsIgnoreCase("json")
							|| fileExtension.equalsIgnoreCase("yaml") || fileExtension.equalsIgnoreCase("yml")) {
						System.out.println("Reading Files: "+ file.getAbsolutePath());
						
						try {
							Map<String, Object> jsonMap = objectMapper.readValue(file, new TypeReference<Map<String, Object>>() {
							});
//							to read the json files 
							prepareTenantMap(jsonMap);
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println("Error Occured while reading the file"); //change to log
							e.printStackTrace();
							 errorList.add(file.getAbsolutePath());
						}
					
					}
				}
				else if(file.isDirectory()) {
					readFiles(file.getAbsolutePath(), errorList);
				}
			}
		}
	}

//	Builds tenant -> module -> master -> JSONArray map
	public void prepareTenantMap(Map<String, Object> map) {
		
		String tenantId = (String) map.get("tenantId");
		String moduleName = (String) map.get("moduleName");
		
		Set<String> masterKeys = map.keySet();
		
//		Here, remove non-master keys
		
		List<String> ignoreKey = new ArrayList<>(Arrays.asList("tenantId" , "moduleName"));
		masterKeys.removeAll(ignoreKey);
		
		Map<String, JSONArray> masterDataMap = new HashMap<>();
		
		Iterator<String> masterKeyIterator = masterKeys.iterator();
		
		while(masterKeyIterator.hasNext()) {
			String masterName = masterKeyIterator.next();
			JSONArray masterDataJsonArray = null;
			
			try {
				masterDataJsonArray = JsonPath.read(objectMapper.writeValueAsString(map.get(masterName)), "$");
			} catch (JsonProcessingException e) {
				// TODO: handle exception
				System.out.println("Error while parsing file!");
				e.printStackTrace();
			}
			
			if(!tenantMap.containsKey(tenantId)) {
				Map<String, Map<String, JSONArray>> moduleMap = new HashMap<>();
				moduleMap.put(moduleName, masterDataMap);
				tenantMap.put(tenantId, moduleMap);
			} else {
				Map<String, Map<String, JSONArray>> tenantModule = tenantMap.get(tenantId);
				
				if(!tenantModule.containsKey(moduleName)) {
					tenantModule.put(moduleName, masterDataMap);
				} else {
					Map<String, JSONArray> moduleMaster = tenantModule.get(moduleName);

//					Needed only when same master exists in multiple files, not for basic court-masters.
					
					boolean isMergeAllowed =
                            isMergeAllowedForMaster(moduleName, masterName);

					 if (!moduleMaster.containsKey(masterName)) {
	                        masterDataMap.put(masterName, masterDataJsonArray);
	                        moduleMaster.putAll(masterDataMap);
	                    } else if (isMergeAllowed) {
	                        moduleMaster.get(masterName).merge(masterDataJsonArray);
	                    }
				}
				tenantMap.put(tenantId, tenantModule);
			}
			masterDataMap.put(masterName, masterDataJsonArray);
		}
		
	}

	
//	 It's loads legal-master-config.json
	public void readMdmsConfigFiles(String masterConfigUrl) throws Exception {
        Resource resource = resourceLoader.getResource(masterConfigUrl);
        InputStream inputStream = null;

        try {
            inputStream = resource.getInputStream();
            masterConfigMap =
                    objectMapper.readValue(inputStream,
                            new TypeReference<Map<String, Map<String, Object>>>() {});
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
	
	public boolean isMergeAllowedForMaster(String moduleName, String masterName) {
        boolean isMergeAllowed = false;

        if (masterConfigMap.containsKey(moduleName)
                && masterConfigMap.get(moduleName).containsKey(masterName)) {

            Object masterData =
                    masterConfigMap.get(moduleName).get(masterName);

            try {
                isMergeAllowed =
                        JsonPath.read(objectMapper.writeValueAsString(masterData),
                                MDMSConstants.MERGE_FILES);
            } catch (Exception ignored) {}
        }
        return isMergeAllowed;
    }


}
