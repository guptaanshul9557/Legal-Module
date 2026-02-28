import {
    CardLabel,
    CardLabelError,
    LabelFieldPair,
    MultiSelectDropdown,
    RemoveableTag
  } from "@upyog/digit-ui-react-components";
  import React, { useEffect, useState } from "react";
  
  const SelectAdvocate = ({
    t,
    config,
    onSelect,
    userType,
    formData,
    setError,
    clearErrors,
    formState
  }) => {
    const [advocates, setAdvocates] = useState(formData?.[config.key] || []);
  
    const advocateMenu = [
      { advocateId: "A1", name: "John Doe", labelKey: "LEGAL_ADVOCATE_JOHN" },
      { advocateId: "A2", name: "Sarah Lee", labelKey: "LEGAL_ADVOCATE_SARAH" }
    ];
  
    const selectAdvocates = (selected) => {
      const values = [];
      selected?.forEach((item) => values.push(item?.[1]));
      setAdvocates(values);
    };
  
    const removeAdvocate = (index) => {
      const updated = advocates.filter((_, i) => i !== index);
      setAdvocates(updated);
    };
  
    useEffect(() => {
      if (userType === "employee") {
        if (!advocates.length) {
          setError(config.key, {
            type: "required",
            message: t("CORE_COMMON_REQUIRED_ERRMSG")
          });
        } else {
          clearErrors(config.key);
          onSelect(config.key, advocates);
        }
      }
    }, [advocates]);
  
    if (userType !== "employee") return null;
  
    return (
      <React.Fragment>
        <LabelFieldPair>
          <CardLabel className="card-label-smaller">
            {t("LEGAL_ASSIGN_ADVOCATE")}
            <span className="check-page-link-button"> *</span>
          </CardLabel>
  
          <div className="form-field">
            <MultiSelectDropdown
              options={advocateMenu}
              optionsKey="labelKey"
              selected={advocates}
              onSelect={selectAdvocates}
              defaultUnit="Selected"
              t={t}
            />
  
            <div className="tag-container">
              {advocates.map((adv, index) => (
                <RemoveableTag
                  key={index}
                  text={adv.name}
                  onClick={() => removeAdvocate(index)}
                />
              ))}
            </div>
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
  
  export default SelectAdvocate;
  