import {BUILD_BREADCRUMB, RESET_BREADCRUMB} from '../actions/types';

const init_state = [{ to: '/', content: 'home' }];

export const breadcrumbReducer = (state = init_state, action) => {
  switch (action.type) {
    case BUILD_BREADCRUMB:
      return action.payload;
    case RESET_BREADCRUMB:
      return init_state;
    default:
      return state;
  }
};
