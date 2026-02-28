import {
    CardLabel,
    CardLabelError,
    Dropdown,
    LabelFieldPair
  } from "@upyog/digit-ui-react-components";
  import React, { useEffect, useState } from "react";
  
  const SelectCaseCategory = ({
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
    const [category, setCategory] = useState(formData?.[config.key]);
  
    const categoryMenu = [
      { code: "LAND", i18nKey: "LEGAL_CASE_CATEGORY_LAND" },
      { code: "TAX", i18nKey: "LEGAL_CASE_CATEGORY_TAX" },
      { code: "SERVICE", i18nKey: "LEGAL_CASE_CATEGORY_SERVICE" }
    ];
  
    function selectCategory(value) {
      setCategory(value);
    }
  
    function goNext() {
      onSelect(config.key, category?.code);
    }
  
    useEffect(() => {
      if (userType === "employee") {
        if (!category) {
          setError(config.key, {
            type: "required",
            message: t("CORE_COMMON_REQUIRED_ERRMSG")
          });
        } else {
          clearErrors(config.key);
          goNext();
        }
      }
    }, [category]);
  
    if (userType === "employee") {
      return (
        <React.Fragment>
          <LabelFieldPair>
            <CardLabel className="card-label-smaller">
              {t("LEGAL_CASE_CATEGORY")}
              <span className="check-page-link-button"> *</span>
            </CardLabel>
  
            <Dropdown
              className="form-field"
              option={categoryMenu}
              optionKey="i18nKey"
              selected={categoryMenu.find(e => e.code === category)}
              select={selectCategory}
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
  
  export default SelectCaseCategory;
  