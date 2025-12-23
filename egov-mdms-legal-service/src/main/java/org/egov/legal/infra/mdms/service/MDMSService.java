package org.egov.legal.infra.mdms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egov.legal.infra.mdms.exceptions.FileReadingException;
import org.egov.legal.infra.mdms.exceptions.InvalidFilterExpressionException;
import org.egov.legal.infra.mdms.exceptions.InvalidTenantIdException;
import org.egov.legal.infra.mdms.exceptions.MasterDataNotFoundException;
import org.egov.legal.infra.mdms.exceptions.ModuleNotFoundException;
import org.egov.legal.infra.mdms.service.impl.MDMSServiceImpl;
import org.egov.legal.infra.mdms.utils.ErrorStructure;
import org.egov.legal.infra.mdms.utils.MDMSConstants;
import org.egov.legal.infra.mdms.utils.ResponseStructure;
import org.egov.mdms.model.MasterDetail;
import org.egov.mdms.model.MdmsCriteriaReq;
import org.egov.mdms.model.ModuleDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;


@Service
@Slf4j
public class MDMSService {

    @Autowired
    private ResponseStructure<Map<String, Map<String, JSONArray>>> responseStructure;

    @Autowired
    private ErrorStructure errorStructure;

    private static final Logger log = LoggerFactory.getLogger(MDMSService.class);

    public Map<String, Map<String, JSONArray>> searchMaster(MdmsCriteriaReq mdmsCriteriaReq) {
        Map<String, Map<String, Map<String, JSONArray>>> tenantIdMap = MDMSServiceImpl.getTenantMap();
        String tenantId = mdmsCriteriaReq.getMdmsCriteria().getTenantId();
        Map<String, Map<String, JSONArray>> stateLevel = null;
        Map<String, Map<String, JSONArray>> ulbLevel = null;

        try {
            // Validate tenantId and fetch tenant data
            if (tenantId.contains(".")) {
                String[] array = tenantId.split("\\.");
                stateLevel = tenantIdMap.get(array[0]);
                ulbLevel = tenantIdMap.get(tenantId);
                if (ulbLevel == null) {
                    log.error("Invalid tenant ID: {}", tenantId);
                    throw new InvalidTenantIdException("Invalid tenantId provided");
                }
            } else {
                stateLevel = tenantIdMap.get(tenantId);
                if (stateLevel == null) {
                    log.error("Invalid tenant ID: {}", tenantId);
                    throw new InvalidTenantIdException("Invalid tenantId provided");
                }
            }

            // Processing modules and masters
            List<ModuleDetail> moduleDetails = mdmsCriteriaReq.getMdmsCriteria().getModuleDetails();
            Map<String, Map<String, JSONArray>> responseMap = new HashMap<>();

            for (ModuleDetail moduleDetail : moduleDetails) {
                if (stateLevel.get(moduleDetail.getModuleName()) == null) {
                    log.error("Module not found: {}", moduleDetail.getModuleName());
                    throw new ModuleNotFoundException("Module not found in MDMS");
                }

                Map<String, JSONArray> finalMasterMap = new HashMap<>();
                for (MasterDetail masterDetail : moduleDetail.getMasterDetails()) {
                    JSONArray masterData;

                    try {
                        masterData = getMasterData(stateLevel, ulbLevel, moduleDetail.getModuleName(), masterDetail.getName(), tenantId);
                    } catch (FileReadingException e) {
                        errorStructure.setStatuscode(500)
                                .setErrorMessage("Error occurred while reading the master file")
                                .setRootCause(e.getMessage());
                        throw e;
                    } catch (InvalidFilterExpressionException e) {
                        errorStructure.setStatuscode(400)
                                .setErrorMessage("Invalid filter expression")
                                .setRootCause(e.getMessage());
                        throw e;
                    }

                    if (masterData == null) continue;

                    if (masterDetail.getFilter() != null) {
                        masterData = filterMaster(masterData, masterDetail.getFilter());
                    }

                    finalMasterMap.put(masterDetail.getName(), masterData);
                }
                responseMap.put(moduleDetail.getModuleName(), finalMasterMap);
            }

            // Prepare successful response
            responseStructure.setStatuscode(200)
                    .setMessage("Master data fetched successfully")
                    .setData(responseMap);

            return responseMap;

        } catch (InvalidTenantIdException e) {
            log.error("Invalid tenant ID: {}", tenantId, e);
            errorStructure.setStatuscode(400)
                    .setErrorMessage("Invalid tenantId provided")
                    .setRootCause(e.getMessage());
            throw new InvalidTenantIdException(tenantId);

        } catch (ModuleNotFoundException e) {
            log.error("Module not found: {}", e.getMessage());
            errorStructure.setStatuscode(404)
                    .setErrorMessage("Module not found")
                    .setRootCause(e.getMessage());
            throw new ModuleNotFoundException("Module Not Found!");

        } catch (Exception e) {
            log.error("Error occurred while processing the request", e);
            errorStructure.setStatuscode(500)
                    .setErrorMessage("An unexpected error occurred")
                    .setRootCause(e.getMessage());
            throw new MasterDataNotFoundException("Master data fetch failed", e);  // To be handled globally
        }
    }

    private JSONArray getMasterData(Map<String, Map<String, JSONArray>> stateLevel, Map<String, Map<String, JSONArray>> ulbLevel, String moduleName, String masterName, String tenantId) throws Exception {
        Map<String, Map<String, Object>> masterConfigMap = MDMSServiceImpl.getMasterConfigMap();

        boolean isStateLevel = true;

        Object masterData = masterConfigMap.get(moduleName).get(masterName);

        try {
            isStateLevel = JsonPath.read(
                    new ObjectMapper().writeValueAsString(masterData),
                    MDMSConstants.STATE_LEVEL_JSONPATH
            );
        } catch (Exception ignored) {}

        if (ulbLevel == null || isStateLevel) {
            return stateLevel.get(moduleName).get(masterName);
        } else {
            return ulbLevel.get(moduleName).get(masterName);
        }
    }

    public JSONArray filterMaster(JSONArray masters, String filterExp) {
        return JsonPath.read(masters, filterExp);
    }
}



//
//@Service
//@Slf4j
//public class MDMSService {
//
//	public Map<String, Map<String, JSONArray>> searchMaster(MdmsCriteriaReq mdmsCriteriaReq) {
//		Map<String, Map<String, Map<String, JSONArray>>> tenantIdMap = MDMSServiceImpl.getTenantMap();
//
//		String tenantId = mdmsCriteriaReq.getMdmsCriteria().getTenantId();
//
//		Map<String, Map<String, JSONArray>> stateLevel = null;
//		Map<String, Map<String, JSONArray>> ulbLevel = null;
//
//
//		if (tenantId.contains(".")) {
//			String array[] = tenantId.split("\\.");
//			stateLevel = tenantIdMap.get(array[0]);
//			ulbLevel = tenantIdMap.get(tenantId);
//			if (ulbLevel == null)
//				throw new CustomException("Invalid_tenantId.MdmsCriteria.tenantId", "Invalid Tenant Id");
//		} else {
//			stateLevel = tenantIdMap.get(tenantId);
//			if (stateLevel == null)
//				throw new CustomException("Invalid_tenantId.MdmsCriteria.tenantId", "Invalid Tenant Id");
//		}
//
//		
//		List<ModuleDetail> moduleDetails = mdmsCriteriaReq.getMdmsCriteria().getModuleDetails();
//
//		Map<String, Map<String, JSONArray>> responseMap = new HashMap<>();
//
//		for(ModuleDetail moduleDetail : moduleDetails) {
//
//			if(stateLevel.get(moduleDetail.getModuleName()) == null)
//				continue;
//			
//			Map<String, JSONArray> finalMasterMap = new HashMap<>();
//
//			for(MasterDetail masterDetail : moduleDetail.getMasterDetails()) {
//				JSONArray masterData;
//
//				try {
//					masterData = getMasterData(
//							stateLevel,
//							ulbLevel,
//							moduleDetail.getModuleName(),
//							masterDetail.getName(),
//							tenantId);
//				} catch (Exception e) {
//					// TODO: handle exception
//					System.out.println("Exception occured while reading the msater data");
//					continue;
//				}
//
//				if(masterData == null)
//					continue;
//
//
//				if(masterDetail.getFilter() != null)
//					masterData = filterMaster(masterData, masterDetail.getFilter());
//
//				finalMasterMap.put(masterDetail.getName(), masterData);
//			}
//			responseMap.put(moduleDetail.getModuleName(), finalMasterMap);
//		}
//		return responseMap;
//
//
//	}
//
//	//	Determines STATE vs ULB using legal-config.json
//
//	private JSONArray getMasterData(
//			Map<String, Map<String, JSONArray>> stateLevel,
//			Map<String, Map<String, JSONArray>> ulbLevel, String moduleName, String masterName, String tenantId) throws Exception {
//
//
//		Map<String, Map<String, Object>> masterConfigMap = MDMSServiceImpl.getMasterConfigMap();
//
//		boolean isStateLevel = true; //court master default
//
//		Object masterData = masterConfigMap.get(moduleName).get(masterName);
//
//		try {
//			isStateLevel = JsonPath.read(
//					new ObjectMapper().writeValueAsString(masterData),
//					MDMSConstants.STATE_LEVEL_JSONPATH
//					);
//		} catch (Exception ignored) {}
//
//		if (ulbLevel == null || isStateLevel) {
//			return stateLevel.get(moduleName).get(masterName);
//		} else {
//			return ulbLevel.get(moduleName).get(masterName);
//		}
//	}
//
//
//
//	public JSONArray filterMaster(JSONArray masters, String filterExp) {
//		JSONArray filteredMasters = JsonPath.read(masters, filterExp);
//		return filteredMasters;
//	}
//
//}
