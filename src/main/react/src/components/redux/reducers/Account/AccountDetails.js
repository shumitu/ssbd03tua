import {
  API_END,
  API_ERROR,
  API_START,
  FETCH_ACCOUNT_DETAILS,
  FETCH_MY_ACCOUNT_DETAILS,
  SET_ACCOUNT_DETAILS,
} from '../../actions/types';

const init_state = {
  data: {
    etag: '',
    email: '',
    motto: '',
    confirmed: '',
    active: '',
    passwordChangeRequired: '',
    accessLevelList: [],
    lastSuccessfulLogin: '',
    lastUnsuccessfulLogin: '',
    candidate: {
      etag: '',
      firstName: '',
      lastName: '',
    },
    employee: {
      etag: '',
      employeeNumber: null,
    },
  },
  isLoading: true,
  error: null,
};

export const accountDetailsReducer = (state = init_state, action) => {
  switch (action.type) {
    case SET_ACCOUNT_DETAILS:
      return {
        ...state,
        data: action.payload,
      };
    case API_START:
      if (action.payload === FETCH_MY_ACCOUNT_DETAILS || action.payload === FETCH_ACCOUNT_DETAILS) {
        return {
          ...state,
          isLoading: true,
        };
      } return state;
    case API_END:
      if (action.payload === FETCH_MY_ACCOUNT_DETAILS || action.payload === FETCH_ACCOUNT_DETAILS) {
        return {
          ...state,
          isLoading: false,
        };
      } return state;
    case API_ERROR:
      if (action.payload === FETCH_MY_ACCOUNT_DETAILS || action.payload === FETCH_ACCOUNT_DETAILS) {
        return {
          ...state,
          error: action.payload,
        };
      } return state;
    default:
      return state;
  }
};
