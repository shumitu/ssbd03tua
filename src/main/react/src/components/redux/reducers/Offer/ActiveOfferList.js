import {
  API_END,
  API_ERROR,
  API_START,
  FETCH_ACTIVE_OFFERS_LIST,
  RESET_ACTIVE_OFFERS_LIST_ERROR,
  SET_ACTIVE_OFFERS_LIST,
} from '../../actions/types';

const init_state = {
  data: [
    {
      id: '',
      price: '',
      isOpen: '',
      destination: '',
    },
  ],
  isLoading: true,
  error: null,
};

export const activeOffersListReducer = (state = init_state, action) => {
  switch (action.type) {
    case RESET_ACTIVE_OFFERS_LIST_ERROR:
      return {
        ...state,
        error: null,
      };
    case SET_ACTIVE_OFFERS_LIST:
      return {
        ...state,
        data: action.payload,
      };
    case API_START:
      if (action.payload === FETCH_ACTIVE_OFFERS_LIST) {
        return {
          ...state,
          isLoading: true,
        };
      } return state;
    case API_END:
      if (action.payload === FETCH_ACTIVE_OFFERS_LIST) {
        return {
          ...state,
          isLoading: false,
        };
      } return state;
    case API_ERROR:
      if (action.payload.label === FETCH_ACTIVE_OFFERS_LIST) {
        return {
          ...state,
          error: action.payload.error.response,
        };
      } return state;
    default:
      return state;
  }
};
