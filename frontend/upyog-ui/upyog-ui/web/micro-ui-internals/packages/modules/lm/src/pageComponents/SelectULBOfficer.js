import {
    CardLabel,
    CardLabelError,
    Dropdown,
    LabelFieldPair
  } from "@upyog/digit-ui-react-components";
  import React, { useEffect, useState } from "react";
  
  const SelectULBOfficer = ({
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
    const [officer, setOfficer] = useState(formData?.[config.key]);
  
    const officerMenu = [
      { officerId: "O1", name: "Legal Officer" },
      { officerId: "O2", name: "Revenue Officer" }
    ];
  
    function selectOfficer(value) {
      setOfficer(value);
    }
  
    function goNext() {
      onSelect(config.key, officer);
    }
  
    useEffect(() => {
      if (userType === "employee") {
        if (!officer) {
          setError(config.key, {
            type: "required",
            message: t("CORE_COMMON_REQUIRED_ERRMSG")
          });
        } else {
          clearErrors(config.key);
          goNext();
        }
      }
    }, [officer]);
  
    if (userType === "employee") {
      return (
        <React.Fragment>
          <LabelFieldPair>
            <CardLabel className="card-label-smaller">
              {t("LEGAL_ULB_OFFICER")}
              <span className="check-page-link-button"> *</span>
            </CardLabel>
  
            <Dropdown
              className="form-field"
              option={officerMenu}
              optionKey="name"
              selected={officer}
              select={selectOfficer}
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
  
  export default SelectULBOfficer;
  