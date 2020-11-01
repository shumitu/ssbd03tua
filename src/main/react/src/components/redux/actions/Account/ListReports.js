import {format, parseISO} from 'date-fns';
import {apiAction} from '../api';
import {FETCH_ACCOUNT_REPORTS_LIST, SET_ACCOUNT_REPORTS_LIST} from '../types';

const mappedData = (data) => data.map((o) => ({
  email: o.email,
  lastSuccessfulLogin: o.lastSuccessfulLogin === undefined ? '----------' : format(parseISO(o.lastSuccessfulLogin), 'yyyy-MM-dd, HH:mm:ss'),
  lastUnsuccessfulLogin: o.lastUnsuccessfulLogin === undefined ? '----------' : format(parseISO(o.lastUnsuccessfulLogin), 'yyyy-MM-dd, HH:mm:ss'),
  ipAddress: o.ipAddress === undefined ? '----------' : o.ipAddress,
}));

export const fetchAccountReportsList = () => apiAction({
  uri: '/accounts/listReports',
  onSuccess: setAccountReportsList,
  label: FETCH_ACCOUNT_REPORTS_LIST,
});

export const setAccountReportsList = (data) => ({
  type: SET_ACCOUNT_REPORTS_LIST,
  payload: mappedData(data),
});
