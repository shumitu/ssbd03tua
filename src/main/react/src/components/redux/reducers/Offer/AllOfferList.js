import {
  API_END,
  API_ERROR,
  API_START,
  FETCH_ALL_OFFERS_LIST,
  REMOVE_OFFER,
  RESET_ALL_OFFERS_LIST_ERROR,
  SET_ALL_OFFERS_LIST,
  SET_CLOSED,
  SET_OPEN,
  SET_VISIBLE,
} from '../../actions/types';

const init_state = {
  data: [
    {
      id: '',
      price: '',
      isHidden: '',
      isOpen: '',
      totalWeight: '',
      destination: '',
    },
  ],
  isLoading: true,
  error: null,
};

export const allOffersListReducer = (state = init_state, action) => {
  switch (action.type) {
    case RESET_ALL_OFFERS_LIST_ERROR:
      return {
        ...state,
        error: null,
      };
    case SET_ALL_OFFERS_LIST:
      return {
        ...state,
        data: action.payload,
      };
    case API_START:
      if (action.payload === FETCH_ALL_OFFERS_LIST
                || action.payload === SET_VISIBLE
                || action.payload === SET_OPEN) {
        return {
          ...state,
          isLoading: true,
        };
      } return state;
    case API_END:
      if (action.payload === FETCH_ALL_OFFERS_LIST
                || action.payload === SET_VISIBLE
                || action.payload === SET_OPEN) {
        return {
          ...state,
          isLoading: false,
        };
      } return state;
    case API_ERROR:
      if (action.payload.label === FETCH_ALL_OFFERS_LIST || action.payload.label === SET_VISIBLE
            || action.payload.label === REMOVE_OFFER || action.payload.label === SET_OPEN
            || action.payload.label === SET_CLOSED) {
        return {
          ...state,
          error: action.payload.error.response,
        };
      } return state;
    default:
      return state;
  }
};
