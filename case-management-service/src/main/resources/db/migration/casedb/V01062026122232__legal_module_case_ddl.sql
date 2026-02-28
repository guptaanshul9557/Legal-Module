CREATE TABLE eg_lm_case (
    caseid              VARCHAR(64) PRIMARY KEY,
    tenantid            VARCHAR(64) NOT NULL,

    casetype            VARCHAR(64),
    casecategory        VARCHAR(64),

    title                VARCHAR(256),
    description          TEXT,
    department           VARCHAR(128),

    courttype           VARCHAR(64),
    courtname           VARCHAR(128),

    nexthearingdate    BIGINT,

    workflowid          VARCHAR(64) NOT NULL,

    additionaldetails   JSONB,
    status            VARCHAR(32),

    createdby           VARCHAR(64),
    createdtime         BIGINT,
    lastmodifiedby     VARCHAR(64),
    lastmodifiedtime   BIGINT
);

CREATE INDEX idx_lm_case_tenant
    ON eg_lm_case (tenantid, caseid);

CREATE INDEX idx_lm_case_hearing
    ON eg_lm_case (nexthearingdate);

    
    
    CREATE TABLE eg_lm_case_advocate (
    advocateid                      VARCHAR(64) PRIMARY KEY,
    caseid                 VARCHAR(64) NOT NULL,
   
    name                    VARCHAR(128) NOT NULL,
    barregistrationnumber VARCHAR(64),
    mobilenumber           VARCHAR(20),
    email                   VARCHAR(128),
    role                    VARCHAR(64),
    activecasecount         VARCHAR(10),
    isgovernmentadvocate  BOOLEAN,

    createdby              VARCHAR(64),
    createdtime            BIGINT,
    lastmodifiedby        VARCHAR(64),
    lastmodifiedtime      BIGINT,

    CONSTRAINT fk_case_advocate_case
        FOREIGN KEY (caseid)
        REFERENCES eg_lm_case(caseid)
        ON DELETE CASCADE,

    CONSTRAINT uk_case_advocate
        UNIQUE (caseid, advocateid)
);



CREATE TABLE eg_lm_case_petitioner (
     petitionerid     VARCHAR(64) PRIMARY KEY,
    caseid           VARCHAR(64) NOT NULL,
    name              VARCHAR(128) NOT NULL,
    type              VARCHAR(32),
    mobilenumber     VARCHAR(20),
    email             VARCHAR(128),

    createdby        VARCHAR(64),
    createdtime      BIGINT,
    lastmodifiedby  VARCHAR(64),
    lastmodifiedtime BIGINT,

    CONSTRAINT fk_case_petitioner_case
        FOREIGN KEY (caseid)
        REFERENCES eg_lm_case(caseid)
        ON DELETE CASCADE,

    CONSTRAINT uk_case_petitioner
        UNIQUE (caseid, petitionerid)
);


CREATE TABLE eg_lm_case_respondent (
   respondentid      VARCHAR(64) PRIMARY KEY,
    caseid            VARCHAR(64) NOT NULL,
    name               VARCHAR(128) NOT NULL,
    type               VARCHAR(32),
    department         VARCHAR(128),
    mobilenumber      VARCHAR(20),
    email              VARCHAR(128),
    address            JSONB,

    createdby         VARCHAR(64),
    createdtime       BIGINT,
    lastmodifiedby   VARCHAR(64),
    lastmodifiedtime BIGINT,

    CONSTRAINT fk_case_respondent_case
        FOREIGN KEY (caseid)
        REFERENCES eg_lm_case(caseid)
        ON DELETE CASCADE,

    CONSTRAINT uk_case_respondent
        UNIQUE (caseid, respondentid)
);


CREATE TABLE eg_lm_case_document (
    id               VARCHAR(64) PRIMARY KEY,
    caseid          VARCHAR(64) NOT NULL,

    documenttype    VARCHAR(64) NOT NULL,
    filestoreid    VARCHAR(128) NOT NULL,
    documentuid     VARCHAR(64),
    status           VARCHAR(32),

    createdby       VARCHAR(64),
    createdtime     BIGINT,
    lastmodifiedby VARCHAR(64),
    lastmodifiedtime BIGINT,

    CONSTRAINT fk_case_document_case
        FOREIGN KEY (caseid)
        REFERENCES eg_lm_case(caseid)
        ON DELETE CASCADE
);



CREATE TABLE eg_lm_judgement (
    judgementid      VARCHAR(64) PRIMARY KEY,
    caseid           VARCHAR(64) NOT NULL,

    remark            TEXT,
    orderdetail      TEXT,

    createdby        VARCHAR(64),
    createdtime      BIGINT,
    lastmodifiedby  VARCHAR(64),
    lastmodifiedtime BIGINT,

    CONSTRAINT fk_judgement_case
        FOREIGN KEY (caseid)
        REFERENCES eg_lm_case(caseid)
        ON DELETE CASCADE
);
