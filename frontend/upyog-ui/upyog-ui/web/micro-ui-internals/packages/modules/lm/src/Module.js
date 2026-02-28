import React from "react";
import { useTranslation } from "react-i18next";
import { useRouteMatch } from "react-router-dom";
import { Loader,  OBPSIcon, CitizenInfoLabel, CitizenHomeCard } from "@upyog/digit-ui-react-components";
import EmployeeApp from "./pages/employee";
import NewCaseRegistration from "./pages/employee/NewApplication";
import LMEmployeeCard from "./pages/employee/EmployeeCard";
import SelectCaseType from "./pageComponents/SelectCaseType";
import SelectAdvocate from "./pageComponents/SelectAdvocate";
import SelectCaseCategory from "./pageComponents/SelectCaseCategory";
import SelectCourtDetails from "./pageComponents/SelectCourtDetails";
import SelectULBOfficer from "./pageComponents/SelectULBOfficer";
import CaseDetails from "./pageComponents/caseDetails";
import FilingDate from "./pageComponents/FilingDate";
import SelectDocuments from "./pageComponents/SelectDocuments";
import PetitionerDetails from "./pageComponents/PetitionerDetails";
const LMModule = ({ stateCode, userType, tenants }) => {
  const moduleCode = "LM";
  const { path, url } = useRouteMatch();
  const language = Digit.StoreData.getCurrentLanguage();
  const { isLoading, data: store } = Digit.Services.useStore({ stateCode, moduleCode, language });

  Digit.SessionStorage.set("LM_TENANTS", tenants);

  if (isLoading) {
    return <Loader />;
  }

  

  return <EmployeeApp path={path} stateCode={stateCode} />
}

const LMLinks = ({ matchPath, userType }) => {
  const { t } = useTranslation();

  const links = [
    
    {
      link: `${matchPath}/building-permit`,
      i18nKey: t("BPA_CITIZEN_HOME_STAKEHOLDER_LOGIN_LABEL"),
    },
    {
      link: `${matchPath}/home`,
      i18nKey: t("BPA_CITIZEN_HOME_ARCHITECT_LOGIN_LABEL"),
    }
  ];

  return (
    <CitizenHomeCard header={t("ACTION_TEST_BUILDING_PLAN_APPROVAL")} links={links} Icon={() => <OBPSIcon />}
      Info={() => <CitizenInfoLabel style={{margin: "0px", padding: "10px"}} info={t("CS_FILE_APPLICATION_INFO_LABEL")} text={t(`BPA_CITIZEN_HOME_STAKEHOLDER_INCLUDES_INFO_LABEL`)} />} isInfo={true}
    />
  );
} 

const componentsToRegister = {
  LMModule,
  LMLinks,
  LMCard:LMEmployeeCard,
  NewCaseRegistration,
  SelectCaseType,
  SelectAdvocate,
  SelectCaseCategory,
  SelectCourtDetails,
  SelectULBOfficer,
  CaseDetails,
  FilingDate,
  SelectDocuments,
  PetitionerDetails
}

export const initLMComponents = () => {
  Object.entries(componentsToRegister).forEach(([key, value]) => {
    Digit.ComponentRegistryService.setComponent(key, value);
  });
};