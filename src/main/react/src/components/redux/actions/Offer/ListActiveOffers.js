import {FETCH_ACTIVE_OFFERS_LIST, SET_ACTIVE_OFFERS_LIST} from '../types';
import {boolToString} from '../../../utils/DisplayConverters';
import {apiAction} from '../api';

const mappedData = (data) => data.map((o) => ({
  id: o.id,
  price: `${o.price} PLN`,
  isOpen: boolToString(o.open),
  destination: o.destination,
}));

export const fetchActiveOffersList = () => apiAction({
  uri: '/offers/listActive',
  method: 'GET',
  onSuccess: setActiveOffersList,
  label: FETCH_ACTIVE_OFFERS_LIST,
});

export const setActiveOffersList = (data) => ({
  type: SET_ACTIVE_OFFERS_LIST,
  payload: mappedData(data),
});
