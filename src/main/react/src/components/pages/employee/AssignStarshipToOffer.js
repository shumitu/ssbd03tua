import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { Formik } from 'formik';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import VirtualizedSelect from 'react-virtualized-select';
import Spinner from 'react-bootstrap/Spinner';
import Button from 'react-bootstrap/Button';
import { buildBreadcrumb, changeTitle } from '../../redux/actions/actions';
import {
  AllOffersListBreadcrumb, AssignStarshipBreadcrumb,
  HomeBreadcrumb,
} from '../../../resources/AppConstants';
import ProgressBar from '../../misc/ProgressBar';
import PopUp from '../../misc/Snackbars';
import GenericAlert from '../../misc/GenericAlert';
import { fetchStarshipsForOfferCreation } from '../../redux/actions/Starship/ListStarships';
import 'react-select/dist/react-select.css';
import 'react-virtualized-select/styles.css';
import StarshipAPI from '../../API/StarshipAPI';

const AssignStarshipToOffer = ({ match }) => {
  const { t } = useTranslation(['assign_starship', 'common', 'error']);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const dispatch = useDispatch();
  const starshipList = useSelector((state) => state.starshipList);

  useEffect(() => {
    setSuccess(false);
    dispatch(buildBreadcrumb([HomeBreadcrumb, AllOffersListBreadcrumb,
      AssignStarshipBreadcrumb(match.params.offerId)]));
    dispatch(fetchStarshipsForOfferCreation());
    dispatch(changeTitle(t('page_title')));
  }, [dispatch, t]);

  const onSubmit = (values, { resetForm }) => {
    setIsSubmitting(true);
    StarshipAPI.assignToOffer(
      values.starshipId, match.params.offerId,
    ).then(() => {
      resetForm({});
      setError(null);
      setSuccess(true);
    }).catch((e) => {
      setSuccess(false);
      dispatch(fetchStarshipsForOfferCreation());
      setError(e);
    }).finally(() => setIsSubmitting(false));
  };

  const yup = require('yup');
  const validationSchema = yup.object().shape({
    starshipId: yup.number()
      .required(t('error:validation.required')),
  });

  if (starshipList.isLoading) {
    return <ProgressBar />;
  }
  return (
    <div className="details-form">
      {success && <PopUp text={t('success')} />}
      <Formik
        validationSchema={validationSchema}
        enableReinitialize
        onSubmit={onSubmit}
        initialValues={{
          starshipId: null,
        }}
      >
        {({
          values, errors, handleSubmit, setFieldValue,
        }) => (
          <Form onSubmit={handleSubmit} noValidate>
            <Form.Row>
              <Form.Group controlId="starshipId" as={Col}>
                <Form.Label>{t('label')}</Form.Label>
                <VirtualizedSelect
                  placeholder={t('choose_starship')}
                  clearable={false}
                  searchable={false}
                  options={starshipList.data}
                  onChange={(e) => setFieldValue('starshipId', e !== null ? e.value : null)}
                  value={values.starshipId}
                />
              </Form.Group>
            </Form.Row>
            <Button
              block
              type="submit"
              disabled={Object.keys(errors).length !== 0}
            >
              {isSubmitting ? (
                <Spinner
                  as="span"
                  animation="border"
                  size="sm"
                  role="status"
                  aria-hidden="true"
                />
              ) : t('submit')}
            </Button>
          </Form>
        )}
      </Formik>
      {error
      && (
        <Row style={{ marginTop: '0.5rem' }}>
          <Col md="auto" className="column-center" style={{ width: '100%' }}>
            <GenericAlert message={t(error.message)} />
          </Col>
        </Row>
      )}
    </div>
  );
};

export default AssignStarshipToOffer;
