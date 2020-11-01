import React, { useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch, useSelector } from 'react-redux';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import { Link } from 'react-router-dom';
import Badge from 'react-bootstrap/Badge';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import { buildBreadcrumb, changeTitle } from '../redux/actions/actions';
import {
  ActiveOffersListBreadcrumb,
  AllOffersListBreadcrumb,
  HomeBreadcrumb,
  OfferDetailsBreadcrumb,
} from '../../resources/AppConstants';
import {
  fetchOfferEmployeeDetails,
  fetchOfferGuestDetails,
} from '../redux/actions/Offer/OfferDetails';
import ProgressBar from '../misc/ProgressBar';
import GenericAlert from '../misc/GenericAlert';
import { RESET_OFFER_DETAILS_ERROR } from '../redux/actions/types';
import history from '../misc/history';

const ShowOfferDetails = ({ match, employeeMode = false}) => {
  const { t } = useTranslation(['offer_details', 'error']);
  const dispatch = useDispatch();
  const error = useSelector((state) => state.offerDetails.error);
  const isLoading = useSelector((state) => state.offerDetails.isLoading);
  const data = useSelector((state) => state.offerDetails.data);

  useEffect(() => {
    dispatch(changeTitle(t('page_title')));
    dispatch({ type: RESET_OFFER_DETAILS_ERROR });
    if (employeeMode) {
      dispatch(buildBreadcrumb([HomeBreadcrumb, AllOffersListBreadcrumb, OfferDetailsBreadcrumb(match.params.offerId)]));
      if (match !== undefined) {
        dispatch(fetchOfferEmployeeDetails(match.params.offerId));
      }
    } else {
      dispatch(buildBreadcrumb([HomeBreadcrumb, ActiveOffersListBreadcrumb, OfferDetailsBreadcrumb(match.params.offerId)]));
      if (match !== undefined) {
        dispatch(fetchOfferGuestDetails(match.params.offerId));
      }
    }
  }, [dispatch, match, t, employeeMode]);

  const getBadge = (val) => {
    if (val) return '✔';
    return '✖';
  };

  const getBadgeVariant = (val) => {
    if (val) return 'success';
    return 'danger';
  };

  const EmployeeDetails = () => (
    <>
      <Form.Row>
        <Form.Group as={Col}>
          <Form.Label>{`${t('total_cost')} [PLN]`}</Form.Label>
          <Form.Control readOnly defaultValue={data.totalCost} />
        </Form.Group>
        <Form.Group as={Col}>
          <Form.Label>{`${t('total_weight')} [kg]`}</Form.Label>
          <Form.Control readOnly defaultValue={data.totalWeight} />
        </Form.Group>
      </Form.Row>
      <Form.Row>
        <Form.Group
          as={Col}
          style={{
            display: 'flex',
            flexDirection: 'row',
            alignItems: 'center',
            height: 'fit-content',
          }}
        >
          <Form.Label>{t('hidden')}</Form.Label>
          <h2 style={{ marginLeft: '1rem' }}>
            <Badge variant={getBadgeVariant(data.hidden)}>
              {getBadge(data.hidden)}
            </Badge>
          </h2>
        </Form.Group>
        <Form.Group
          as={Col}
          style={{
            display: 'flex',
            flexDirection: 'row',
            alignItems: 'center',
            height: 'fit-content',
          }}
        >
          <Form.Label>{t('open')}</Form.Label>
          <h2 style={{ marginLeft: '1rem' }}>
            <Badge variant={getBadgeVariant(data.open)}>
              {getBadge(data.open)}
            </Badge>
          </h2>
        </Form.Group>
      </Form.Row>
      <Row>
        <Col stye={{ width: '100%' }}>
          <Link to={`/applications/list/${match.params.offerId}`}>
            <Button
              variant="primary"
              block
            >
              {t('offer_applications')}
            </Button>
          </Link>
        </Col>
      </Row>
    </>
  );

  if (isLoading) {
    return <ProgressBar />;
  }
  if (error) {
    return (
      <div className="details-form" style={{ maxWidth: '50vw' }}>
        {error && <GenericAlert message={t(error.data.message)} />}
      </div>
    );
  }
  return (
    <div className="details-form" style={{ maxWidth: '50vw' }}>
      <Row style={{ justifyContent: 'center', marginBottom: '2vh' }}>
        <Col md="auto" style={{ fontSize: '3.5vh' }}>
          {data.destination}
        </Col>
      </Row>
      <Form.Row>
        <Form.Group as={Col}>
          <Form.Label>{t('description')}</Form.Label>
          <Form.Control readOnly defaultValue={data.description} as="textarea" />
        </Form.Group>
      </Form.Row>
      <Form.Row>
        <Form.Group as={Col}>
          <Form.Label>{t('departure')}</Form.Label>
          <Form.Control readOnly defaultValue={data.flightStartTime} />
        </Form.Group>
        <Form.Group as={Col}>
          <Form.Label>{t('return')}</Form.Label>
          <Form.Control readOnly defaultValue={data.flightEndTime} />
        </Form.Group>
      </Form.Row>
      <Form.Row>
        <Form.Group as={Col}>
          <Form.Label>{`${t('price')} [PLN]`}</Form.Label>
          <Form.Control readOnly defaultValue={data.price} />
        </Form.Group>
        <Form.Group as={Col}>
          <Form.Label>{t('ship')}</Form.Label>
          <Form.Control
            readOnly
            defaultValue={data.starship ? data.starship.name : t('no_ship')}
            onClick={() => history.push(`/starships/details/${data.starship.id}`)}
            style={{ cursor: 'pointer' }}
          />
        </Form.Group>
      </Form.Row>
      {employeeMode && <EmployeeDetails />}
    </div>
  );
};

export default ShowOfferDetails;
