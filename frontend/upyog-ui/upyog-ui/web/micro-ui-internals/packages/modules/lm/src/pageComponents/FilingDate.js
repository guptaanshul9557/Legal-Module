import {
    CardLabel,
    LabelFieldPair,
    TextInput
  } from "@upyog/digit-ui-react-components";
  import React, { useEffect, useState } from "react";
  
  const FilingDate = ({
    t,
    config,
    onSelect,
    userType,
    formData
  }) => {
    const [filingDate] = useState(
      formData?.[config.key] ||
        new Date().toISOString().split("T")[0]
    );
    console.log("filind",filingDate)
  
    useEffect(() => {
      if (userType === "employee" && filingDate) {
        onSelect(config.key, filingDate);
      }
    }, [filingDate]);
  
    if (userType !== "employee") return null;
  
    return (
      <LabelFieldPair>
        <CardLabel className="card-label-smaller">
          {t("LEGAL_FILING_DATE")}
        </CardLabel>
        <div className="field">
          <TextInput
            className="form-field"
            value={filingDate}
            disable={true}
          />
        </div>
      </LabelFieldPair>
    );
  };
  
  export default FilingDate;
  