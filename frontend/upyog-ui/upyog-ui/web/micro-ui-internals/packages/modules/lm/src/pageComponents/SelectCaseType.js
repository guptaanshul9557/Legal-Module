import {
  CardLabel,
  CardLabelError,
  Dropdown,
  LabelFieldPair,
  Loader,
  RadioButtons
} from "@upyog/digit-ui-react-components";
import FormStep from "../../../../react-components/src/molecules/FormStep";
import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

const SelectCaseType = ({
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
  const [caseType, setCaseType] = useState(formData?.[config.key]);

  /* ---------- CASE TYPE MENU (STATIC FOR NOW) ---------- */
  const caseTypeMenu = [
    { i18nKey: "LEGAL_CASE_TYPE_CIVIL", code: "CIVIL" },
    { i18nKey: "LEGAL_CASE_TYPE_CRIMINAL", code: "CRIMINAL" },
    { i18nKey: "LEGAL_CASE_TYPE_WRIT", code: "WRIT" }
  ];

  const { pathname } = useLocation();
  const presentInModify = pathname.includes("modify");

  /* ---------- SELECT HANDLER ---------- */
  function selectCaseType(value) {
    setCaseType(value);
  }

  /* ---------- GO NEXT ---------- */
  function goNext() {
    onSelect(config.key, caseType?.code);
  }

  /* ---------- EMPLOYEE AUTO FLOW ---------- */
  useEffect(() => {
    if (userType === "employee") {
      if (!caseType) {
        setError(config.key, {
          type: "required",
          message: t("CORE_COMMON_REQUIRED_ERRMSG")
        });
      } else {
        clearErrors(config.key);
        goNext();
      }
    }
  }, [caseType]);

  /* ---------- EMPLOYEE VIEW ---------- */
  if (userType === "employee") {
    return (
      <React.Fragment>
        <LabelFieldPair>
          <CardLabel className="card-label-smaller">
            {t("LEGAL_CASE_TYPE")}
            <span className="check-page-link-button"> *</span>
          </CardLabel>

          <Dropdown
            className="form-field"
            selected={caseTypeMenu.find(
              (e) => e.code === caseType
            )}
            option={caseTypeMenu}
            optionKey="i18nKey"
            select={selectCaseType}
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

  /* ---------- CITIZEN VIEW (FUTURE SAFE) ---------- */
};

export default SelectCaseType;
