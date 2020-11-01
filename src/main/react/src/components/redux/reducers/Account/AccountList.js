import {
  API_END,
  API_ERROR,
  API_START,
  FETCH_ACCOUNT_LIST,
  RESET_ACCOUNT_LIST_ERROR,
  SET_ACCOUNT_LIST,
  SET_ACTIVE,
} from '../../actions/types';

const init_state = {
  data: [
    {
      email: '',
      active: '',
      confirmed: '',
      passwordChangeRequired: '',
      accessLevelList: '',
    },
  ],
  isLoading: true,
  error: null,
};

export const accountListReducer = (state = init_state, action) => {
  switch (action.type) {
    case RESET_ACCOUNT_LIST_ERROR:
      return {
        ...state,
        error: null,
      };
    case SET_ACCOUNT_LIST:
      return {
        ...state,
        data: action.payload,
      };
    case API_START:
      if (action.payload === FETCH_ACCOUNT_LIST || action.payload === SET_ACTIVE) {
        return {
          ...state,
          isLoading: true,
        };
      } return state;
    case API_END:
      if (action.payload === FETCH_ACCOUNT_LIST || action.payload === SET_ACTIVE) {
        return {
          ...state,
          isLoading: false,
        };
      } return state;
    case API_ERROR:
      if (action.payload.label === FETCH_ACCOUNT_LIST || action.payload.label === SET_ACTIVE) {
        return {
          ...state,
          error: action.payload.error.response,
        };
      } return state;
    default:
      return state;
  }
};
