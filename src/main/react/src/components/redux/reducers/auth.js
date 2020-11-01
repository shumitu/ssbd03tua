import {CLEAR_AUTH, SET_AUTH, SET_FORCE_PASSWORD_CHANGE, SWITCH_ROLE,} from '../actions/types';
import {AUTH_ROLE} from '../../../resources/AppConstants';

const init_state = {
  bearer: '',
  claims: {
    auth: [AUTH_ROLE.GUEST],
    exp: '',
    sub: '',
    FORCE_PASSWORD_CHANGE: false,
  },
};

export const authReducer = (state = init_state, action) => {
  switch (action.type) {
    case SET_AUTH:
      return action.payload;
    case SET_FORCE_PASSWORD_CHANGE:
      return { ...state, claims: { ...state.claims, FORCE_PASSWORD_CHANGE: action.payload } };
    case CLEAR_AUTH:
      return init_state;
    default:
      return state;
  }
};

export const currentAccessLevelReducer = (state = AUTH_ROLE.GUEST, action) => {
  switch (action.type) {
    case SWITCH_ROLE:
      return action.payload;
    default:
      return state;
  }
};
