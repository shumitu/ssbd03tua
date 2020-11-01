import {format, parseISO} from 'date-fns';
import {FETCH_ACCOUNT_DETAILS, FETCH_MY_ACCOUNT_DETAILS, SET_ACCOUNT_DETAILS} from '../types';
import {apiAction} from '../api';

export const setAccountDetails = (data) => {
  const sucLogin = data.lastSuccessfulLogin === null || data.lastSuccessfulLogin === undefined
    ? null : format(parseISO(data.lastSuccessfulLogin), 'yyyy-MM-dd, HH:mm:ss');
  const unsLogin = data.lastUnsuccessfulLogin === null || data.lastUnsuccessfulLogin === undefined
    ? null : format(parseISO(data.lastUnsuccessfulLogin), 'yyyy-MM-dd, HH:mm:ss');
  return {
    type: SET_ACCOUNT_DETAILS,
    payload: {
      etag: data.etag,
      email: data.email,
      motto: data.motto,
      confirmed: data.confirmed,
      active: data.active,
      passwordChangeRequired: data.passwordChangeRequired,
      accessLevelList: data.accessLevelList,
      lastSuccessfulLogin: sucLogin,
      lastUnsuccessfulLogin: unsLogin,
      candidate: data.candidate,
      employee: data.employee,
    },
  };
};

export const fetchMyAccountDetails = () => apiAction({
  uri: '/accounts/my-account',
  onSuccess: setAccountDetails,
  label: FETCH_MY_ACCOUNT_DETAILS,
});

export const fetchAccountDetails = (email) => apiAction({
  uri: '/accounts/account',
  method: 'POST',
  data: { email },
  onSuccess: setAccountDetails,
  label: FETCH_ACCOUNT_DETAILS,
});
