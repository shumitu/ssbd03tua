import i18n from '../../i18n';
import {AUTH_ROLE} from '../../resources/AppConstants';

export const boolToString = (bool) => (bool ? i18n.t('Yes') : i18n.t('No'));

const convertAccessLevel = (accessLevel) => {
  if (accessLevel === AUTH_ROLE.ADMIN) { return (Object.keys(AUTH_ROLE)[3]); }
  if (accessLevel === AUTH_ROLE.EMPLOYEE) { return (Object.keys(AUTH_ROLE)[2]); }
  if (accessLevel === AUTH_ROLE.CANDIDATE) { return (Object.keys(AUTH_ROLE)[1]); }
};

export function AccessLevelListToString(accessLevelList) {
  const mappedList = accessLevelList.map((level) => i18n.t(convertAccessLevel(level)));
  return mappedList.join(', ');
}


export const weightToString = (weight) => `${weight.toString()} kg`;
