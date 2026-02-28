import {
    CardLabel,
    CardLabelError,
    LabelFieldPair
  } from "@upyog/digit-ui-react-components";
  import React, { useEffect, useState } from "react";
  import MultiUploadWrapper from "../../../../react-components/src/molecules/MultiUploadWrapper";
  
  const SelectDocuments = ({
    t,
    config,
    onSelect,
    userType,
    formData,
    setError,
    clearErrors,
    formState
  }) => {
    const [documents, setDocuments] = useState(
      formData?.[config.key] || []
    );
    console.log("docccc",documents)
    const onUpload = (files) => {
      if (!files?.length) {
        setDocuments([]);
        return;
      }
  
      const formattedDocs = files.map(([fileName, fileData]) => {
        const fileStoreId = fileData?.fileStoreId?.fileStoreId;
  
        return {
          fileName,
          fileStoreId,
          documentUid: fileStoreId,
          documentType: fileName   
        };
      });
  
      setDocuments(formattedDocs);
    };
  
    useEffect(() => {
      if (userType === "employee") {
        if (!documents.length) {
          setError(config.key, {
            type: "required",
            message: t("CORE_COMMON_REQUIRED_ERRMSG")
          });
        } else {
          clearErrors(config.key);
          onSelect(config.key, documents); 
        }
      }
    }, [documents]);
  
    if (userType !== "employee") return null;
  
    return (
      <React.Fragment>
      <LabelFieldPair>
        <CardLabel className="card-label-smaller">
          {t("LEGAL_DOCUMENTS")}
          <span className="check-page-link-button"> *</span>
        </CardLabel>
        <div className="field">
          <MultiUploadWrapper
            tenantId={Digit.ULBService.getCurrentTenantId()}
            module="LEGAL"
            getFormState={onUpload}
            allowedMaxSizeInMB={5}
            acceptFiles="image/*,.pdf,.png,.jpeg,.jpg"
          />
        </div>
        </LabelFieldPair>
  
        {formState?.touched?.[config.key] ? (
          <CardLabelError>
            {formState?.errors?.[config.key]?.message}
          </CardLabelError>
        ) : null}
        </React.Fragment>
    );
  };
  
  export default SelectDocuments;
  