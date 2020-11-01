import {
  FETCH_APPLICATION_DETAILS,
  FETCH_MY_APPLICATION_DETAILS,
  SET_APPLICATION_DETAILS
} from '../types';
import {apiAction} from '../api';

export const fetchMyApplicationDetails = (id) => apiAction({
  uri: '/applications/getMyApplication',
  method: 'POST',
  data: { id },
  onSuccess: setApplicationDetails,
  label: FETCH_MY_APPLICATION_DETAILS,
});

export const fetchApplicationDetails = (id) => apiAction({
  uri: '/applications/getApplication',
  method: 'POST',
  data: { id },
  onSuccess: setApplicationDetails,
  label: FETCH_APPLICATION_DETAILS,
});

export const setApplicationDetails = (data) => {
  return {
    type: SET_APPLICATION_DETAILS,
    payload: {
      weight: data.weight,
      examination_code: data.examinationCode,
      motivational_letter: data.motivationalLetter,
      offer_id: data.offerId,
      offer_destination: data.offerDestination,
      candidate_first_name: data.candidateFirstName,
      candidate_last_name: data.candidateLastName,
      category_name: data.categoryName,
      etag: data.etag
    },
  };
};
