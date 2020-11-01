import {
  API_END,
  API_ERROR,
  API_START,
  FETCH_APPLICATION_LIST,
  RESET_APPLICATION_LIST_ERROR,
  SET_APPLICATION_LIST,
} from '../../actions/types';

const init_state = {
  data: [
    {
      id: 0,
      firstName: '',
      lastName: '',
      weight: 0,
      creationDate: '',
      categoryName: '',
    },
  ],
  isLoading: true,
  error: null,
};

export const applicationListReducer = (state = init_state, action) => {
  switch (action.type) {
    case RESET_APPLICATION_LIST_ERROR:
      return {
        ...state,
        error: null,
      };
    case SET_APPLICATION_LIST:
      return {
        ...state,
        data: action.payload,
      };
    case API_START:
      if (action.payload === FETCH_APPLICATION_LIST) {
        return {
          ...state,
          isLoading: true,
        };
      } return state;
    case API_END:
      if (action.payload === FETCH_APPLICATION_LIST) {
        return {
          ...state,
          isLoading: false,
        };
      } return state;
    case API_ERROR:
      if (action.payload.label === FETCH_APPLICATION_LIST) {
        return {
          ...state,
          error: action.payload.error.response,
        };
      } return state;
    default:
      return state;
  }
};
