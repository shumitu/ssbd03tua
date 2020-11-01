import {format, parseISO} from 'date-fns';
import {apiAction} from '../api';
import {CANCEL_APPLICATION, FETCH_OWN_APPLICATION_LIST, SET_OWN_APPLICATION_LIST,} from '../types';
import i18n from "../../../../i18n";

const mappedData = (data) => data.map((o) => ({
  id: o.id,
  creationDate: format(parseISO(o.creationTime), 'yyyy-MM-dd, HH:mm:ss'),
  destination: o.destination,
  startTime: format(parseISO(o.startTime), 'yyyy-MM-dd, HH:mm:ss'),
  endTime: format(parseISO(o.endTime), 'yyyy-MM-dd, HH:mm:ss'),
  categoryName: o.reviewed ? i18n.t(`application_category:${o.categoryName}`) : i18n.t('application_category:not_yet_reviewed')
}));

export const fetchOwnApplicationsList = () => apiAction({
  uri: '/applications/listOwnApplications',
  method: 'GET',
  data: null,
  onSuccess: setOwnApplicationsList,
  label: FETCH_OWN_APPLICATION_LIST,
});

export const setOwnApplicationsList = (data) => ({
  type: SET_OWN_APPLICATION_LIST,
  payload: mappedData(data),
});

export const cancelApplication = (id) => apiAction({
  uri: '/applications/cancel',
  method: 'POST',
  data: { id },
  onSuccess: fetchOwnApplicationsList,
  onFailure: fetchOwnApplicationsList,
  label: CANCEL_APPLICATION,
});
