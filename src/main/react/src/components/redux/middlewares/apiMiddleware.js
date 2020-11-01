import axios from 'axios';
import {API} from '../actions/types';
import {accessDenied, apiEnd, apiError, apiStart,} from '../actions/api';
import {API_PATH} from '../../API/ApiConstants';

const apiMiddleware = ({ dispatch }) => (next) => (action) => {
  if (action !== undefined && action.type === API) {
    const {
      uri,
      method,
      data,
      onSuccess,
      onFailure,
      label,
      headers,
    } = action.payload;

    axios.defaults.baseURL = API_PATH;
    axios.defaults.headers.common['Content-Type'] = 'application/json';
    if (label) {
      dispatch(apiStart(label));
    }
    axios
      .request({
        url: uri,
        method,
        headers,
        data,
      })
      .then(({ data }) => {
        dispatch(onSuccess(data));
      })
      .catch((error) => {
        console.log(error);
        dispatch(apiError(error, label));
        dispatch(onFailure());
        if (error.response && (error.response.status === 403 || error.response.status === 401)) {
          dispatch(accessDenied(window.location.pathname));
        }
      })
      .finally(() => {
        if (label) {
          dispatch(apiEnd(label));
        }
      });
  }
  next(action);
};

export default apiMiddleware;
