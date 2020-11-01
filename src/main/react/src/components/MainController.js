import React, {Suspense} from 'react';
import GuestMainView from './pages/guest/GuestMainView';
import AdminMainView from './pages/admin/AdminMainView';
import EmployeeMainView from './pages/employee/EmployeeMainView';
import CandidateMainView from './pages/candidate/CandidateMainView';
import '../i18n';
import {useSelector} from 'react-redux';
import PasswordChangeRequired from './pages/PasswordChangeRequired';
import {AUTH_ROLE} from '../resources/AppConstants';

const getBody = (accessLevel, passwordChangeRequired) => {
  if (passwordChangeRequired) {
    return <Suspense fallback={null}><PasswordChangeRequired /></Suspense>;
  }
  switch (accessLevel) {
    case (AUTH_ROLE.ADMIN):
      return <AdminMainView />;
    case (AUTH_ROLE.CANDIDATE):
      return <CandidateMainView />;
    case (AUTH_ROLE.EMPLOYEE):
      return <EmployeeMainView />;
    default:
      return <GuestMainView />;
  }
};

const MainController = () => {
  const accessLevel = useSelector((state) => state.currentAccessLevel);
  const passwordChangeRequired = useSelector((state) => state.auth.claims.FORCE_PASSWORD_CHANGE);

  return (
    <>
      {getBody(accessLevel, passwordChangeRequired)}
    </>
  );
};

export default MainController;
