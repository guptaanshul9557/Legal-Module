export const caseRegistrationConfig = [
  {
    head: "LEGAL_CASE_REGISTRATION",
    body: [
      {
        type: "component",
        route: "caseType",
        isMandatory: true,
        component: "SelectCaseType",
        withoutLabel: true,
        texts: {
          header: "LEGAL_CASE_TYPE"
        },
        key: "caseType",
        nextStep: "caseCategory",
        hideInEmployee: false
      },
      {
        type: "component",
        route: "caseCategory",
        isMandatory: true,
        component: "SelectCaseCategory",
        withoutLabel: true,
        texts: {
          header: "LEGAL_CASE_CATEGORY"
        },
        key: "caseCategory",
        nextStep: "courtDetails",
        hideInEmployee: false
      },
      {
        type: "component",
        route: "courtDetails",
        isMandatory: true,
        component: "SelectCourtDetails",
        withoutLabel: true,
        texts: {
          header: "LEGAL_COURT_DETAILS"
        },
        key: "courtDetails",
        nextStep: "filingDate",
        hideInEmployee: false
      },
      {
        type: "component",
        route: "filingDate",
        isMandatory: true,
        component: "FilingDate",
        withoutLabel: true,
        texts: {
          header: "LEGAL_FILING_DATE"
        },
        key: "filingDate",
        nextStep: "caseDetails",
        hideInEmployee: false
      }
    ]
  },
  {
    head:"LEGAL_PETITIONER_DETAILS",
    body:[
      {
        type: "component",
        route: "petitioner",
        isMandatory: true,
        component: "PetitionerDetails",
        withoutLabel: true,
        texts: {
          header: "LEGAL_PETITIONER_DETAILS"
        },
        key: "petitioner",
        nextStep: "caseDetails",
        hideInEmployee: false
      }
    ]
  },

  {
    head: "LEGAL_CASE_DETAILS",
    body: [
      {
        type: "component",
        route: "caseDetails",
        isMandatory: true,
        component: "CaseDetails",
        withoutLabel: true,
        texts: {
          header: "LEGAL_CASE_DETAILS"
        },
        key: "caseDetails",
        nextStep: "advocate",
        hideInEmployee: false
      }
    ]
  },

  {
    head: "LEGAL_ASSIGNMENT_DETAILS",
    body: [
      {
        type: "component",
        route: "advocate",
        isMandatory: true,
        component: "SelectAdvocate",
        withoutLabel: true,
        texts: {
          header: "LEGAL_ASSIGN_ADVOCATE"
        },
        key: "advocate",
        nextStep: "ulbOfficer",
        hideInEmployee: false
      },
      {
        type: "component",
        route: "ulbOfficer",
        isMandatory: true,
        component: "SelectULBOfficer",
        withoutLabel: true,
        texts: {
          header: "LEGAL_ASSIGN_ULB_OFFICER"
        },
        key: "ulbOfficer",
        nextStep: "documents",
        hideInEmployee: false
      },
      
    ]
  },
  {
    head: "LEGAL_DOCUMENTS",
    body:[
      {
        type: "component",
        route: "documents",
        isMandatory: true,
        component: "SelectDocuments",
        withoutLabel: true,
        texts: {
          header: "LEGAL_ADDITIONAL_DETAILS"
        },
        key: "documents",
        nextStep: null,
        hideInEmployee: false
      }
    ]
  }
];
