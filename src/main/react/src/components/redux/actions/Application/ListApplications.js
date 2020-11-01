import {format, parseISO} from 'date-fns';
import {apiAction} from '../api';
import {FETCH_APPLICATION_LIST, SET_APPLICATION_LIST} from '../types';
import {weightToString} from '../../../utils/DisplayConverters';
import i18n from "../../../../i18n";

const mappedData = (data) => data.map((o) => ({
  id: o.id,
  firstName: o.firstName,
  lastName: o.lastName,
  weight: weightToString(o.weight),
  creationDate: format(parseISO(o.creationDate), 'yyyy-MM-dd, HH:mm:ss'),
  categoryName: o.reviewed ? i18n.t(`application_category:${o.categoryName}`) : i18n.t('application_category:not_yet_reviewed')
}));

export const fetchApplicationsList = (offerId) => apiAction({
  uri: '/applications/listApplications',
  method: 'POST',
  data: { id: offerId },
  onSuccess: setApplicationsList,
  label: FETCH_APPLICATION_LIST,
});

export const setApplicationsList = (data) => ({
  type: SET_APPLICATION_LIST,
  payload: mappedData(data),
});
