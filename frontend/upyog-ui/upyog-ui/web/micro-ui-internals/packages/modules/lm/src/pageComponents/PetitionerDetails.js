import {
    CardLabel,
    CardLabelError,
    Dropdown,
    LabelFieldPair,
    TextInput
  } from "@upyog/digit-ui-react-components";
  import React, { useEffect, useState } from "react";
  
  const PetitionerDetails = ({
    t,
    config,
    onSelect,
    userType,
    formData,
    setError,
    clearErrors,
    formState,
    onBlur
  }) => {
    const [petitioner, setPetitioner] = useState(
      formData?.[config.key] || {}
    );
  
    const petitionerTypeMenu = [
      { code: "INDIVIDUAL", i18nKey: "LEGAL_PETITIONER_INDIVIDUAL" },
      { code: "DEPARTMENT", i18nKey: "LEGAL_PETITIONER_DEPARTMENT" },
      { code: "INSTITUTION", i18nKey: "LEGAL_PETITIONER_INSTITUTION" }
    ];
  
    const update = (key, value) => {
      setPetitioner({ ...petitioner, [key]: value });
    };
  
    useEffect(() => {
      if (userType === "employee") {
        if (!petitioner?.type || !petitioner?.name) {
          setError(config.key, {
            type: "required",
            message: t("CORE_COMMON_REQUIRED_ERRMSG")
          });
        } else {
          clearErrors(config.key);
          onSelect(config.key, petitioner);
        }
      }
    }, [petitioner]);
  
    if (userType !== "employee") return null;
  
    return (
      <React.Fragment>
        <LabelFieldPair>
          <CardLabel className="card-label-smaller">
            {t("LEGAL_PETITIONER_TYPE")}
            <span className="check-page-link-button"> *</span>
          </CardLabel>
  
          <Dropdown
            className="form-field"
            option={petitionerTypeMenu}
            optionKey="i18nKey"
            selected={petitionerTypeMenu.find(
              (e) => e.code === petitioner?.type
            )}
            select={(v) => update("type", v.code)}
            onBlur={onBlur}
            t={t}
          />
        </LabelFieldPair>
  
        <LabelFieldPair>
          <CardLabel className="card-label-smaller">
            {t("LEGAL_PETITIONER_NAME")}
            <span className="check-page-link-button"> *</span>
          </CardLabel>
          <div className="field">
          <TextInput
            className="form-field"
            value={petitioner?.name || ""}
            onChange={(e) => update("name", e.target.value)}
            onBlur={onBlur}
          />
          </div>
        </LabelFieldPair>
  
        <LabelFieldPair>
          <CardLabel className="card-label-smaller">
            {t("LEGAL_PETITIONER_MOBILE")}
          </CardLabel>
          <div className="field">
          <TextInput
            className="form-field"
            value={petitioner?.mobile || ""}
            onChange={(e) => update("mobile", e.target.value)}
            onBlur={onBlur}
          />
          </div>
        </LabelFieldPair>
  
        <LabelFieldPair>
          <CardLabel className="card-label-smaller">
            {t("LEGAL_PETITIONER_EMAIL")}
          </CardLabel>
          <div className="field">
          <TextInput
            className="form-field"
            value={petitioner?.email || ""}
            onChange={(e) => update("email", e.target.value)}
            onBlur={onBlur}
          />
          </div>
        </LabelFieldPair>
  
        {formState?.touched?.[config.key] ? (
          <CardLabelError
            style={{
              width: "70%",
              marginLeft: "30%",
              fontSize: "12px",
              marginTop: "-21px"
            }}
          >
            {formState?.errors?.[config.key]?.message}
          </CardLabelError>
        ) : null}
      </React.Fragment>
    );
  };
  
  export default PetitionerDetails;
  