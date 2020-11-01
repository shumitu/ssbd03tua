import {
  FETCH_ALL_OFFERS_LIST,
  REMOVE_OFFER,
  SET_ALL_OFFERS_LIST,
  SET_CLOSED,
  SET_OPEN,
  SET_VISIBLE,
} from '../types';
import {boolToString} from '../../../utils/DisplayConverters';
import {apiAction} from '../api';

const mappedData = (data) => data.map((o) => ({
  id: o.id,
  price: `${o.price} PLN`,
  isHidden: boolToString(o.hidden),
  isOpen: boolToString(o.open),
  totalWeight: `${o.totalWeight} kg`,
  destination: o.destination,
}));

export const fetchAllOffersList = () => apiAction({
  uri: '/offers/listAll',
  method: 'GET',
  onSuccess: setAllOffersList,
  label: FETCH_ALL_OFFERS_LIST,
});

export const setAllOffersList = (data) => ({
  type: SET_ALL_OFFERS_LIST,
  payload: mappedData(data),
});

export const setOpen = (id) => apiAction({
  uri: '/offers/openOffer',
  method: 'PATCH',
  data: { id },
  onSuccess: fetchAllOffersList,
  onFailure: fetchAllOffersList,
  label: SET_OPEN,
});

export const setVisible = (id, visible) => apiAction({
  uri: '/offers/visibility',
  method: 'PATCH',
  data: { id, visible },
  onSuccess: fetchAllOffersList,
  onFailure: fetchAllOffersList,
  label: SET_VISIBLE,
});

export const setClosed = (id) => apiAction({
  uri: '/offers/closeOffer',
  method: 'PATCH',
  data: { id },
  onSuccess: fetchAllOffersList,
  onFailure: fetchAllOffersList,
  label: SET_CLOSED,
});

export const sendRemove = (offerID) => apiAction({
  uri: '/offers/remove',
  method: 'POST',
  data: { offerID },
  onSuccess: fetchAllOffersList,
  onFailure: fetchAllOffersList,
  label: REMOVE_OFFER,
});
