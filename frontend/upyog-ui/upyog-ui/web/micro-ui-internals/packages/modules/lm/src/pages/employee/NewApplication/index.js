import {  Loader } from "@upyog/digit-ui-react-components";
import React, { useState } from "react";
import { useTranslation } from "react-i18next";
import FormComposer from "../../../../../../react-components/src/hoc/FormComposer"
import { useHistory } from "react-router-dom";
import { caseRegistrationConfig as newConfig } from "../../../config/caseRegistrationConfig";

const NewCaseRegistration = () => {
  const { t } = useTranslation();
  const history = useHistory();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const [canSubmit, setCanSubmit] = useState(false);

  const onFormValueChange = (setValue, formData, formState) => {
    setCanSubmit(!Object.keys(formState.errors).length);
  };

  const onSubmit = (data) => {
    console.log("dataaa",data)
    const payload = {
      tenantId,
      ...data,
      status: "FILED",
      channel: "EMPLOYEE"
    };
    console.log("payloadd",payload)
    history.push("/upyog-ui/employee/legal/response", {
      Case: payload
    });
  };

  return (
    <FormComposer
      heading={t("LEGAL_NEW_CASE_REGISTRATION")}
      label={t("COMMON_SUBMIT")}
      config={newConfig.map((config) => {   
        return {
          ...config,
          body: config.body.filter((a) => !a.hideInEmployee),
        };
      })}
      onSubmit={onSubmit}
      onFormValueChange={onFormValueChange}
      isDisabled={!canSubmit}
    />
  );
};

export default NewCaseRegistration;
