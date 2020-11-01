import {ACCESS_DENIED, API, API_END, API_ERROR, API_START,} from './types';

export const apiStart = (label) => ({
  type: API_START,
  payload: label,
});

export const apiEnd = (label) => ({
  type: API_END,
  payload: label,
});

export const accessDenied = (url) => ({
  type: ACCESS_DENIED,
  payload: {
    url,
  },
});

export const apiError = (error, label) => ({
  type: API_ERROR,
  payload: { label, error },
});

export function apiAction({
  uri = '', method = 'GET', data = null, onSuccess = () => ({ type: null }), onFailure = () => ({ type: null }), label = '',
}) {
  return {
    type: API,
    payload: {
      uri,
      method,
      data,
      onSuccess,
      onFailure,
      label,
    },
  };
}
