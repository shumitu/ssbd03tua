import {format, parseISO} from 'date-fns';
import {FETCH_OFFER_EMPLOYEE_DETAILS, FETCH_OFFER_GUEST_DETAILS, SET_OFFER_DETAILS} from '../types';
import {apiAction} from '../api';

export const fetchOfferGuestDetails = (id) => apiAction({
  uri: `/offers/${id}`,
  onSuccess: setOfferDetails,
  label: FETCH_OFFER_GUEST_DETAILS,
});

export const fetchOfferEmployeeDetails = (id) => apiAction({
  uri: `/offers/employee/${id}`,
  onSuccess: setOfferDetails,
  label: FETCH_OFFER_EMPLOYEE_DETAILS,
});

export const setOfferDetails = (data) => ({
  type: SET_OFFER_DETAILS,
  payload: {
    etag: data.etag,
    description: data.description,
    destination: data.destination,
    flightEndTime: format(parseISO(data.flightEndTime), 'yyyy-MM-dd, HH:mm:ss'),
    flightStartTime: format(parseISO(data.flightStartTime), 'yyyy-MM-dd, HH:mm:ss'),
    flightEndTimeNotParsed: parseISO(data.flightEndTime),
    flightStartTimeNotParsed: parseISO(data.flightStartTime),
    price: data.price,
    starship: 'starship' in data ? data.starship : null,
    hidden: 'hidden' in data ? data.hidden : null,
    open: 'open' in data ? data.open : null,
    totalCost: 'totalCost' in data ? data.totalCost : null,
    totalWeight: 'totalWeight' in data ? data.totalWeight : null,
  },
});
