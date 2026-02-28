import {
    CardLabel,
    CardLabelError,
    Dropdown,
    LabelFieldPair
  } from "@upyog/digit-ui-react-components";
  import React, { useEffect, useState } from "react";
  
  const SelectCourtDetails = ({
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
    const [court, setCourt] = useState(formData?.[config.key]);
  
    const courtMenu = [
      { code: "DISTRICT", i18nKey: "LEGAL_COURT_DISTRICT" },
      { code: "HIGH", i18nKey: "LEGAL_COURT_HIGH" },
      { code: "SUPREME", i18nKey: "LEGAL_COURT_SUPREME" }
    ];
  
    function selectCourt(value) {
      setCourt(value);
    }
  
    function goNext() {
      onSelect(config.key, court?.code);
    }
  
    useEffect(() => {
      if (userType === "employee") {
        if (!court) {
          setError(config.key, {
            type: "required",
            message: t("CORE_COMMON_REQUIRED_ERRMSG")
          });
        } else {
          clearErrors(config.key);
          goNext();
        }
      }
    }, [court]);
  
    if (userType === "employee") {
      return (
        <React.Fragment>
          <LabelFieldPair>
            <CardLabel className="card-label-smaller">
              {t("LEGAL_COURT")}
              <span className="check-page-link-button"> *</span>
            </CardLabel>
  
            <Dropdown
              className="form-field"
              option={courtMenu}
              optionKey="i18nKey"
              selected={courtMenu.find(e => e.code === court)}
              select={selectCourt}
              onBlur={onBlur}
              t={t}
            />
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
    }

  };
  
  export default SelectCourtDetails;
  