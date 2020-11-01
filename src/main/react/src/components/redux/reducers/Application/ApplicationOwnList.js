import {
  API_END,
  API_ERROR,
  API_START,
  CANCEL_APPLICATION,
  FETCH_OWN_APPLICATION_LIST,
  RESET_OWN_APPLICATION_LIST_ERROR,
  SET_OWN_APPLICATION_LIST,
} from '../../actions/types';

const init_state = {
  data: [
    {
      id: 0,
      creationDate: '',
      destination: '',
      startTime: '',
      endTime: '',
      categoryName: '',
    },
  ],
  isLoading: true,
  error: null,
};

export const applicationOwnListReducer = (state = init_state, action) => {
  switch (action.type) {
    case RESET_OWN_APPLICATION_LIST_ERROR:
      return {
        ...state,
        error: null,
      };
    case SET_OWN_APPLICATION_LIST:
      return {
        ...state,
        data: action.payload,
      };
    case API_START:
      if (action.payload === FETCH_OWN_APPLICATION_LIST || action.payload === CANCEL_APPLICATION) {
        return {
          ...state,
          isLoading: true,
        };
      } return state;
    case API_END:
      if (action.payload === FETCH_OWN_APPLICATION_LIST || action.payload === CANCEL_APPLICATION) {
        return {
          ...state,
          isLoading: false,
        };
      } return state;
    case API_ERROR:
      if (action.payload.label === FETCH_OWN_APPLICATION_LIST || action.payload.label === CANCEL_APPLICATION) {
        return {
          ...state,
          error: action.payload.error.response,
        };
      } return state;
    default:
      return state;
  }
};
