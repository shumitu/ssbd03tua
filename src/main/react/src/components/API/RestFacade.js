import axios from 'axios';
import {API_PATH} from './ApiConstants';

const RestFacade = {

  setToken: (bearer) => {
    axios.defaults.headers.common.Authorization = bearer;
  },

  get: async (endpoint, headers, params) => await axios.get(API_PATH + endpoint, {
    headers,
    params,
  }),

  post: async (endpoint, data, headers, params) => await axios.post(API_PATH + endpoint, data, {
    headers,
    params,
  }),

  put: async (endpoint, data, headers, params) => await axios.put(API_PATH + endpoint, data, {
    headers,
    params,
  }),

  patch: async (endpoint, data, headers, params) => await axios.patch(API_PATH + endpoint, data, {
    headers,
    params,
  }),

};
export default RestFacade;
