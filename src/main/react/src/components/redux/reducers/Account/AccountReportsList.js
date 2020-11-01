import {
  API_END,
  API_ERROR,
  API_START,
  FETCH_ACCOUNT_REPORTS_LIST,
  SET_ACCOUNT_REPORTS_LIST,
} from '../../actions/types';

const init_state = {
  data: [
    {
      email: '',
      lastSuccessfulLogin: '',
      lastUnsuccessfulLogin: '',
      ipAddress: '',
    },
  ],
  isLoading: true,
  error: null,
};

export const accountReportsListReducer = (state = init_state, action) => {
  switch (action.type) {
    case SET_ACCOUNT_REPORTS_LIST:
      return {
        ...state,
        data: action.payload,
      };
    case API_START:
      if (action.payload === FETCH_ACCOUNT_REPORTS_LIST) {
        return {
          ...state,
          isLoading: true,
        };
      } return state;
    case API_END:
      if (action.payload === FETCH_ACCOUNT_REPORTS_LIST) {
        return {
          ...state,
          isLoading: false,
        };
      } return state;
    case API_ERROR:
      if (action.payload === FETCH_ACCOUNT_REPORTS_LIST) {
        return {
          ...state,
          error: action.payload,
        };
      } return state;
    default:
      return state;
  }
};
