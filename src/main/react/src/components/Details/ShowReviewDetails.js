import React, {useEffect} from 'react';
import Form from 'react-bootstrap/Form';
import {useTranslation} from 'react-i18next';
import {useDispatch, useSelector} from 'react-redux';
import {buildBreadcrumb, changeTitle} from '../redux/actions/actions';
import {fetchMyReviewDetails, fetchReviewDetails} from '../redux/actions/Review/ReviewDetails';
import ProgressBar from '../misc/ProgressBar';
import {
    AllOffersListBreadcrumb,
  ApplicationDetailsBreadcrumb,
  ApplicationListBreadcrumb,
  ApplicationMyDetailsBreadcrumb,
  ApplicationsOwnListBreadcrumb,
    HomeBreadcrumb, OfferDetailsBreadcrumb,
  ReviewDetailsBreadcrumb,
  ReviewMyDetailsBreadcrumb,
} from '../../resources/AppConstants';

const ShowReviewDetails = ({ match }) => {
  const { t } = useTranslation('review_details');
  const dispatch = useDispatch();
  const data = useSelector((state) => state.reviewDetails.data);
  const isLoading = useSelector((state) => state.reviewDetails.isLoading);

  useEffect(() => {
    dispatch(changeTitle(t('page_title')));

    if (match.params.offerId) {
      const applicationListBreadcrumb = ApplicationListBreadcrumb(match.params.offerId);
      const applicationDetailsBreadcrumb = ApplicationDetailsBreadcrumb(match.params.offerId, match.params.id);
      const reviewDetailsBreadcrumb = ReviewDetailsBreadcrumb(match.params.offerId, match.params.id);
        dispatch(buildBreadcrumb([HomeBreadcrumb, AllOffersListBreadcrumb, OfferDetailsBreadcrumb(match.params.offerId), applicationListBreadcrumb, applicationDetailsBreadcrumb, reviewDetailsBreadcrumb]));
      dispatch(fetchReviewDetails(match.params.id));
    } else {
      const applicationMyDetailsBreadcrumb = ApplicationMyDetailsBreadcrumb(match.params.id);
      const reviewMyDetailsBreadcrumb = ReviewMyDetailsBreadcrumb(match.params.id);
      dispatch(buildBreadcrumb([HomeBreadcrumb, ApplicationsOwnListBreadcrumb, applicationMyDetailsBreadcrumb, reviewMyDetailsBreadcrumb]));
      dispatch(fetchMyReviewDetails(match.params.id));
    }
  }, [dispatch, match, t]);

  if (isLoading) return <ProgressBar />;

  return (
    <div className="details-form">
      <Form>
        <Form.Group controlId="id">
          <Form.Label>{t('id')}</Form.Label>
          <Form.Control readOnly type="plaintext" defaultValue={data.id} />
        </Form.Group>
        <Form.Group controlId="employee_number">
          <Form.Label>{t('employee_number')}</Form.Label>
          <Form.Control readOnly type="plaintext" defaultValue={data.employee_number} />
        </Form.Group>
        <Form.Group controlId="name">
          <Form.Label>{t('name')}</Form.Label>
          <Form.Control readOnly type="plaintext" defaultValue={data.name} />
        </Form.Group>
      </Form>
    </div>
  );
};

export default ShowReviewDetails;
