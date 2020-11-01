import {FETCH_MY_REVIEW_DETAILS, FETCH_REVIEW_DETAILS, SET_REVIEW_DETAILS} from '../types';
import {apiAction} from '../api';

export const fetchMyReviewDetails = (id) => apiAction({
  uri: '/applications/getMyReview',
  method: 'POST',
  data: { id },
  onSuccess: setReviewDetails,
  label: FETCH_MY_REVIEW_DETAILS,
});

export const fetchReviewDetails = (id) => apiAction({
  uri: '/applications/getReview',
  method: 'POST',
  data: { id },
  onSuccess: setReviewDetails,
  label: FETCH_REVIEW_DETAILS,
});

export const setReviewDetails = (data) => {
  console.log(data);

  return {
    type: SET_REVIEW_DETAILS,
    payload: {
      id: data.id,
      employee_number: data.employeeNumber,
      name: data.categoryName,
    },
  };
};
