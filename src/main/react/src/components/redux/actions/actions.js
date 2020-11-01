import {
  BUILD_BREADCRUMB,
  CHANGE_TITLE,
  CLEAR_AUTH,
  RESET_BREADCRUMB,
  SET_AUTH,
  SET_FORCE_PASSWORD_CHANGE,
  SWITCH_ROLE,
} from './types';

export function changeTitle(newTitle) {
  return { type: CHANGE_TITLE, payload: newTitle };
}

export function setCredentials(auth) {
  return { type: SET_AUTH, payload: auth };
}

export function switchAccessLevel(accessLevel) {
  return { type: SWITCH_ROLE, payload: accessLevel };
}

export function clearAuth() {
  return { type: CLEAR_AUTH };
}

export function setForcePasswordChange(payload) {
  return { type: SET_FORCE_PASSWORD_CHANGE, payload };
}

export function buildBreadcrumb(payload) {
  return { type: BUILD_BREADCRUMB, payload };
}

export function resetBreadcrumb() {
  return { type: RESET_BREADCRUMB };
}
