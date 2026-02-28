export const showHidingLinksForBPA = (roles = []) => {
    const userInfo = Digit.UserService.getUser();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    let checkedRoles = [];
    const rolearray = roles?.map((role) => {
      userInfo?.info?.roles?.map((item) => {
        if (item.code == role && item.tenantId === tenantId) {
          checkedRoles.push(item);
        }
      });
    });
    return checkedRoles?.length;
  };