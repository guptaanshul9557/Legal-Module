import { PrivateRoute, BreadCrumb, BackButton } from "@upyog/digit-ui-react-components";
import React, { Fragment } from "react";
import { Switch, useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";



const EmployeeApp = ({ path }) => {
  const location = useLocation()
  const { t } = useTranslation();
  
  const NewApplication = Digit?.ComponentRegistryService?.getComponent("NewCaseRegistration");
  return (
    <Fragment>
    <Switch>
      <PrivateRoute path={`${path}/create`} component={() => <NewApplication parentUrl={path} />} />
    </Switch>
    </Fragment>
  )
}

export default EmployeeApp;