import axios from 'axios';
import {ACCESS_DENIED, SET_AUTH} from '../actions/types';
import history from '../../misc/history';
import {ROUTES} from '../../../resources/AppConstants';

export const saveAuthToken = (store) => (next) => (action) => {
  if (action !== undefined && action.type === SET_AUTH) {
    axios.defaults.headers.common.Authorization = action.payload.bearer;
  }
  return next(action);
};

export const accessDeniedMiddleware = (store) => (next) => (action) => {
  if (action !== undefined && action.type === ACCESS_DENIED) {
    history.push(ROUTES.UNAUTHORIZED);
  }
  return next(action);
};
