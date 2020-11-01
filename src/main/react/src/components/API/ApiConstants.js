import {API_CONTEXT, LOCAL_BACK_URL, LOCAL_FRONT_URL} from '../../resources/AppConstants';

/**
 * @return {string}
 */

function GET_API_PATH() {
  const { origin } = window.location;
  if (origin === LOCAL_FRONT_URL) return LOCAL_BACK_URL + API_CONTEXT;
  return origin + API_CONTEXT;
}

export const API_PATH = GET_API_PATH();
