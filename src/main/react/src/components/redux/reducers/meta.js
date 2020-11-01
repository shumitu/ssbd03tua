export const titleReducer = (state = 'Loty Kosmiczne', action) => {
  switch (action.type) {
    case 'CHANGE_TITLE':
      return action.payload;
    default:
      return state;
  }
};
