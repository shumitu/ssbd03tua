import {apiAction} from '../api';
import {FETCH_STARSHIP_LIST, SET_OPERATIONAL, SET_STARSHIP_LIST} from '../types';
import {boolToString} from '../../../utils/DisplayConverters';

const mappedData = (data) => data.map((o) => ({
  id: o.id,
  name: o.name,
  crewCapacity: o.crewCapacity,
  yearOfManufacture: o.yearOfManufacture,
  operational: boolToString(o.operational),
}));

const mappedReducedData = (data) => data.map((o) => ({
  label: o.name,
  value: o.id,
}));

export const fetchAllStarshipList = () => apiAction({
  uri: '/starships/listAll',
  method: 'GET',
  onSuccess: setStarshipList,
  label: FETCH_STARSHIP_LIST,
});

export const fetchOperationalStarshipList = () => apiAction({
  uri: '/starships/listActive',
  method: 'GET',
  onSuccess: setStarshipList,
  label: FETCH_STARSHIP_LIST,
});

export const fetchStarshipsForOfferCreation = () => apiAction({
  uri: '/starships/listActive',
  method: 'GET',
  onSuccess: setStarshipsForOffer,
  label: FETCH_STARSHIP_LIST,
});

export const setOperational = (id, active) => apiAction({
  uri: '/starships/change-status',
  method: 'PATCH',
  data: { id, active },
  onSuccess: fetchAllStarshipList,
  onFailure: fetchAllStarshipList,
  label: SET_OPERATIONAL,
});

export const setStarshipList = (data) => ({
  type: SET_STARSHIP_LIST,
  payload: mappedData(data),
});

export const setStarshipsForOffer = (data) => ({
  type: SET_STARSHIP_LIST,
  payload: mappedReducedData(data),
});
