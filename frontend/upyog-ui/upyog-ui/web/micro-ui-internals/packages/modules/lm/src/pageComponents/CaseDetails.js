import {
    CardLabel,
    CardLabelError,
    Dropdown,
    LabelFieldPair,
    TextInput,
    TextArea
  } from "@upyog/digit-ui-react-components";
  import React, { useEffect, useState } from "react";
  
  const CaseDetails = ({
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
    const [title, setTitle] = useState(formData?.[config.key]?.title);
    const [description, setDescription] = useState(formData?.[config.key]?.description);
    const [priority, setPriority] = useState(formData?.[config.key]?.priority);
    const [department, setDepartment] = useState(formData?.[config.key]?.department);
  
    const priorityMenu = [
      { code: "LOW", i18nKey: "LEGAL_PRIORITY_LOW" },
      { code: "MEDIUM", i18nKey: "LEGAL_PRIORITY_MEDIUM" },
      { code: "HIGH", i18nKey: "LEGAL_PRIORITY_HIGH" }
    ];
  
    const departmentMenu = [
      { code: "REVENUE", i18nKey: "LEGAL_DEPT_REVENUE" },
      { code: "TAX", i18nKey: "LEGAL_DEPT_TAX" },
      { code: "ENGINEERING", i18nKey: "LEGAL_DEPT_ENGINEERING" }
    ];
  
    function goNext() {
      onSelect(config.key, {
        title,
        description,
        priority,
        department
      });
    }
  
    useEffect(() => {
      if (userType === "employee") {
        if (!title || !description || !department) {
          setError(config.key, {
            type: "required",
            message: t("CORE_COMMON_REQUIRED_ERRMSG")
          });
        } else {
          clearErrors(config.key);
          goNext();
        }
      }
    }, [title, description, department]);
  
    if (userType === "employee") {
      return (
        <React.Fragment>
        <LabelFieldPair>
            <CardLabel className="card-label-smaller">
              {t("LEGAL_DEPARTMENT")}
              <span className="check-page-link-button"> *</span>
            </CardLabel>
            <Dropdown
              className="form-field"
              option={departmentMenu}
              optionKey="i18nKey"
              selected={departmentMenu.find(e => e.code === department)}
              select={(v) => setDepartment(v.code)}
              t={t}
            />
          </LabelFieldPair>
          <LabelFieldPair>
            <CardLabel className="card-label-smaller">
              {t("LEGAL_CASE_TITLE")}
              <span className="check-page-link-button"> *</span>
            </CardLabel>
            <div className="field">
            <TextInput
                
                style={{ width: "100%" }}
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                onBlur={onBlur}
            />
            </div>

          </LabelFieldPair>
  
          <LabelFieldPair>
            <CardLabel className="card-label-smaller">
              {t("LEGAL_CASE_DESCRIPTION")}
              <span className="check-page-link-button"> *</span>
            </CardLabel>
            <TextArea
              className="form-field"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              onBlur={onBlur}
            />
          </LabelFieldPair>
  
          {/* <LabelFieldPair>
            <CardLabel className="card-label-smaller">
              {t("LEGAL_CASE_PRIORITY")}
              <span className="check-page-link-button"> *</span>
            </CardLabel>
            <Dropdown
              className="form-field"
              option={priorityMenu}
              optionKey="i18nKey"
              selected={priorityMenu.find(e => e.code === priority)}
              select={(v) => setPriority(v.code)}
              t={t}
            />
          </LabelFieldPair> */}
  
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
  
  export default CaseDetails;
  