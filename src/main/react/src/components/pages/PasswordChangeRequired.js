import React from 'react';
import {useTranslation} from 'react-i18next';
import ChangePassword from './ChangePassword';
import PopUp from '../misc/Snackbars';

const PasswordChangeRequired = () => {
  const { t } = useTranslation('password_change_required');
  return (
    <>
      <PopUp severity="info" autoHideDuration={50000} text={t('message')} />
      <ChangePassword />
    </>
  );
};

export default PasswordChangeRequired;
