import React, {useEffect} from 'react';
import Form from 'react-bootstrap/Form';
import Col from 'react-bootstrap/Col';
import {useTranslation} from 'react-i18next';
import {useDispatch, useSelector} from 'react-redux';
import Button from 'react-bootstrap/Button';
import {Link} from 'react-router-dom';
import {buildBreadcrumb, changeTitle} from '../redux/actions/actions';
import {
  fetchApplicationDetails,
  fetchMyApplicationDetails
} from '../redux/actions/Application/ApplicationDetails';
import ProgressBar from '../misc/ProgressBar';
import {
    AllOffersListBreadcrumb,
  ApplicationDetailsBreadcrumb,
  ApplicationListBreadcrumb,
  ApplicationMyDetailsBreadcrumb,
  ApplicationsOwnListBreadcrumb,
    HomeBreadcrumb, OfferDetailsBreadcrumb,
} from '../../resources/AppConstants';
import ManageAccessLevels from "../pages/admin/ManageAccessLevels";
import {isOneItemSelected} from "../List/TableUtils";
import ReviewApplication from "./ReviewApplication";

const NamesForm = ({ firstName, lastName }) => {
  const { t } = useTranslation('account_details');
  if (firstName && lastName) {
    return (
      <Form.Row>
        <Form.Group as={Col} controlId="firstName">
          <Form.Label>{t('first_name')}</Form.Label>
          <Form.Control readOnly type="plaintext" defaultValue={firstName} />
        </Form.Group>
        <Form.Group as={Col} controlId="lastName">
          <Form.Label>{t('last_name')}</Form.Label>
          <Form.Control readOnly type="plaintext" defaultValue={lastName} />
        </Form.Group>
      </Form.Row>
    );
  }
};

const OfferForm = ({id, destination}) => {
  const {t} = useTranslation('list_all_offers');
  if (id, destination) {
    return (
        <>
          <Form.Group as={Col} controlId="id">
            <Form.Label>{t('id')}</Form.Label>
            <Form.Control readOnly type="plaintext" defaultValue={id}/>
          </Form.Group>
          <Form.Group as={Col} controlId="destination">
            <Form.Label>{t('destination')}</Form.Label>
          <Form.Control readOnly type="plaintext" defaultValue={destination} />
        </Form.Group>
      </>
    );
  }
};

const ShowApplicationDetails = ({match, employeeMode = false}) => {
  const {t} = useTranslation('application_details');
  const dispatch = useDispatch();
  const data = useSelector((state) => state.applicationDetails.data);
  const isLoading = useSelector((state) => state.applicationDetails.isLoading);

  useEffect(() => {
    dispatch(changeTitle(t('page_title')));

    if (match.params.offerId) {
      const applicationListBreadcrumb = ApplicationListBreadcrumb(match.params.offerId);
      const applicationDetailsBreadcrumb = ApplicationDetailsBreadcrumb(match.params.offerId, match.params.id);
        dispatch(buildBreadcrumb([HomeBreadcrumb, AllOffersListBreadcrumb, OfferDetailsBreadcrumb(match.params.offerId), applicationListBreadcrumb, applicationDetailsBreadcrumb]));
      dispatch(fetchApplicationDetails(match.params.id));
    } else {
      const applicationMyDetailsBreadcrumb = ApplicationMyDetailsBreadcrumb(match.params.id);
      dispatch(buildBreadcrumb([HomeBreadcrumb, ApplicationsOwnListBreadcrumb, applicationMyDetailsBreadcrumb]));
      dispatch(fetchMyApplicationDetails(match.params.id));
    }
  }, [dispatch, match, t]);

  if (isLoading) return <ProgressBar />;

  return (
    <div className="details-form">
      <Form>
        <NamesForm firstName={data.candidate_first_name} lastName={data.candidate_last_name} />

        <OfferForm id={data.offer_id} destination={data.offer_destination} />

        <Form.Group controlId="weight">
          <Form.Label>{t('weight')}</Form.Label>
          <Form.Control readOnly type="plaintext" defaultValue={data.weight + ' kg'} />
        </Form.Group>
        <Form.Group controlId="examination_code">
          <Form.Label>{t('examination_code')}</Form.Label>
          <Form.Control readOnly type="plaintext" defaultValue={data.examination_code} />
        </Form.Group>
        <Form.Group controlId="motivational_letter">
          <Form.Label>{t('motivational_letter')}</Form.Label>
          <Form.Control readOnly type="plaintext" defaultValue={data.motivational_letter} />
        </Form.Group>

        {
            data.category_name ? (
                            <>
                              <Form.Group controlId="category_name">
                                <Form.Label>{t('category_name')}</Form.Label>
                                <Form.Control readOnly type="plaintext" defaultValue={data.category_name}/>
                              </Form.Group>

                              <Link
                                  to={`/getReview/${match.params.offerId ? match.params.offerId + '/' : ''}${match.params.id}`}>
                                <Button variant="primary">{t('review_details')}</Button>
                              </Link>
                            </>
                )
                : (employeeMode ? (
                        <>
                          <ReviewApplication applicationId={match.params.id} applicationEtag={data.etag}/>
                        </>
                    ) : (<></>)
                )
                    }

      </Form>
    </div>
  );
};

export default ShowApplicationDetails;
