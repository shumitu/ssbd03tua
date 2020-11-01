import {
  API_END,
  API_ERROR,
  API_START,
  FETCH_MY_REVIEW_DETAILS,
  FETCH_REVIEW_DETAILS,
  SET_REVIEW_DETAILS,
} from '../../actions/types';

const init_state = {
  data: {
    id: '',
    employee_number: '',
    name: '',
  },
  isLoading: true,
  error: null,
};

export const reviewDetailsReducer = (state = init_state, action) => {
  switch (action.type) {
    case SET_REVIEW_DETAILS:
      return {
        ...state,
        data: action.payload,
      };
    case API_START:
      if (action.payload === FETCH_MY_REVIEW_DETAILS || action.payload === FETCH_REVIEW_DETAILS) {
        return {
          ...state,
          isLoading: true,
        };
      } return state;
    case API_END:
      if (action.payload === FETCH_MY_REVIEW_DETAILS || action.payload === FETCH_REVIEW_DETAILS) {
        return {
          ...state,
          isLoading: false,
        };
      } return state;
    case API_ERROR:
      if (action.payload === FETCH_MY_REVIEW_DETAILS || action.payload === FETCH_REVIEW_DETAILS) {
        return {
          ...state,
          error: action.payload,
        };
      } return state;
    default:
      return state;
  }
};
