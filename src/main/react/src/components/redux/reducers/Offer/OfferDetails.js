import {
  API_END,
  API_ERROR,
  API_START,
  FETCH_OFFER_EMPLOYEE_DETAILS,
  FETCH_OFFER_GUEST_DETAILS,
  RESET_OFFER_DETAILS_ERROR,
  SET_OFFER_DETAILS,
} from '../../actions/types';

const init_state = {
  data:
        {
          etag: '',
          description: '',
          destination: '',
          flightEndTime: null,
          flightStartTime: null,
          flightEndTimeNotParsed: null,
          flightStartTimeNotParsed: null,
          price: 0,
          starship: {
            id: -1,
            name: '',
          },
          hidden: null,
          open: null,
          totalCost: 0,
          totalWeight: 0,
        },
  isLoading: true,
  error: null,
};

export const offerDetailsReducer = (state = init_state, action) => {
  switch (action.type) {
    case RESET_OFFER_DETAILS_ERROR:
      return {
        ...state,
        error: null,
      };
    case SET_OFFER_DETAILS:
      return {
        ...state,
        data: action.payload,
      };
    case API_START:
      if (action.payload === FETCH_OFFER_GUEST_DETAILS || action.payload === FETCH_OFFER_EMPLOYEE_DETAILS) {
        return {
          ...state,
          isLoading: true,
        };
      } return state;
    case API_END:
      if (action.payload === FETCH_OFFER_GUEST_DETAILS || action.payload === FETCH_OFFER_EMPLOYEE_DETAILS) {
        return {
          ...state,
          isLoading: false,
        };
      } return state;
    case API_ERROR:
      if (action.payload === FETCH_OFFER_GUEST_DETAILS || action.payload === FETCH_OFFER_EMPLOYEE_DETAILS) {
        return {
          ...state,
          error: action.payload,
        };
      } return state;
    default:
      return state;
  }
};
