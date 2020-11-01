import {apiAction} from '../api';
import {FETCH_ACCOUNT_LIST, SET_ACCOUNT_LIST, SET_ACTIVE} from '../types';
import {AccessLevelListToString, boolToString} from '../../../utils/DisplayConverters';

const mappedData = (data) => data.map((o) => ({
  email: o.email,
  active: boolToString(o.active),
  confirmed: boolToString(o.confirmed),
  passwordChangeRequired: boolToString(o.passwordChangeRequired),
  accessLevelList: AccessLevelListToString(o.accessLevelList),
}));

export const setAccountList = (data) => ({
  type: SET_ACCOUNT_LIST,
  payload: mappedData(data),
});

export const fetchAccountListFiltered = (filterPhrase) => apiAction({
  uri: '/accounts/list',
  method: 'POST',
  data: { filterPhrase },
  onSuccess: setAccountList,
  label: FETCH_ACCOUNT_LIST,
});

export const setActive = (email, active) => apiAction({
  uri: '/accounts/block',
  method: 'PATCH',
  data: { email, active },
  onSuccess: fetchAccountListFiltered,
  onFailure: fetchAccountListFiltered,
  label: SET_ACTIVE,
});
