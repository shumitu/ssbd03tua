import {
  API_END,
  API_ERROR,
  API_START,
  FETCH_STARSHIP_LIST,
  RESET_STARSHIP_LIST_ERROR,
  SET_OPERATIONAL,
  SET_STARSHIP_LIST,
} from '../../actions/types';

const init_state = {
  data: [
    {
      id: '',
      name: '',
      crewCapacity: '',
      yearOfManufacture: '',
      operational: '',
    },
  ],
  isLoading: true,
  error: null,
};

export const starshipListReducer = (state = init_state, action) => {
  switch (action.type) {
    case RESET_STARSHIP_LIST_ERROR:
      return {
        ...state,
        error: null,
      };
    case SET_STARSHIP_LIST:
      return {
        ...state,
        data: action.payload,
      };
    case API_START:
      if (action.payload === FETCH_STARSHIP_LIST || action.payload === SET_OPERATIONAL) {
        return {
          ...state,
          isLoading: true,
        };
      } return state;
    case API_END:
      if (action.payload === FETCH_STARSHIP_LIST || action.payload === SET_OPERATIONAL) {
        return {
          ...state,
          isLoading: false,
        };
      } return state;
    case API_ERROR:
      if (action.payload.label === FETCH_STARSHIP_LIST || action.payload.label === SET_OPERATIONAL) {
        return {
          ...state,
          error: action.payload.error.response,
        };
      } return state;
    default:
      return state;
  }
};
