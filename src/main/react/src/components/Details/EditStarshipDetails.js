import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useTranslation } from 'react-i18next';
import Form from 'react-bootstrap/Form';
import { Formik } from 'formik';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import ProgressBar from '../misc/ProgressBar';
import PopUp from '../misc/Snackbars';
import FormControl from '../misc/FormControls/FormControl';
import FormControlNumber from '../misc/FormControls/FormControlNumber';
import GenericAlert from '../misc/GenericAlert';
import { mottoRegex } from '../utils/Validators';
import { AllStarshipListBreadcrumb, EditStarshipBreadcrumb, HomeBreadcrumb } from '../../resources/AppConstants';
import { buildBreadcrumb, changeTitle } from '../redux/actions/actions';
import { fetchStarshipEmployeeDetails } from '../redux/actions/Starship/StarshipDetails';
import StarshipAPI from '../API/StarshipAPI';
import { GenericRefreshButton } from '../List/TableButtons';

const EditStarshipDetails = ({ match }) => {
  const { t } = useTranslation(['add_starship', 'common', 'error', 'edit_starship']);
  const dispatch = useDispatch();
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);
  const isFetching = useSelector((state) => state.starshipDetails.isLoading);
  const fetchError = useSelector((state) => state.starshipDetails.error);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const name = useSelector((state) => state.starshipDetails.data.name);
  const maximumWeight = useSelector((state) => state.starshipDetails.data.maximumWeight);
  const crewCapacity = useSelector((state) => state.starshipDetails.data.crewCapacity);
  const fuelCapacity = useSelector((state) => state.starshipDetails.data.fuelCapacity);
  const maximumSpeed = useSelector((state) => state.starshipDetails.data.maximumSpeed);
  const yearOfManufacture = useSelector((state) => state.starshipDetails.data.yearOfManufacture);
  const etag = useSelector((state) => state.starshipDetails.data.etag);

  useEffect(() => {
    setSuccess(false);
    const editStarshipBreadcrumb = EditStarshipBreadcrumb(match.params.starshipId);
    dispatch(buildBreadcrumb([HomeBreadcrumb, AllStarshipListBreadcrumb, editStarshipBreadcrumb]));
    dispatch(changeTitle(t('edit_starship:page_title')));
    dispatch(fetchStarshipEmployeeDetails(match.params.starshipId));
  }, [dispatch, match, t]);

  const refresh = () => {
    dispatch(fetchStarshipEmployeeDetails(match.params.starshipId));
    setSuccess(false);
    setError(null);
  };

  const yup = require('yup');
  const validationSchema = yup.object().shape({
    name: yup.string()
      .trim()
      .required(t('error:validation.required'))
      .matches(mottoRegex, t('error:starship.name.incorrect'))
      .max(32, t('error:starship.name.too_long')),
    crewCapacity: yup.number().integer(t('error:validation.number.integer')).required(t('error:validation.number.incorrect')).moreThan(0, t('error:starship.not_positive_value')),
    fuelCapacity: yup.number().required(t('error:validation.number.incorrect')).moreThan(0, t('error:starship.not_positive_value')),
    maximumSpeed: yup.number().required(t('error:validation.number.incorrect')).moreThan(0, t('error:starship.not_positive_value')),
    yearOfManufacture: yup.number().integer(t('error:validation.number.integer')).required(t('error:validation.number.incorrect')).moreThan(2000, t('error:starship.year.incorrect')),
  });

  const onSubmit = (values, { resetForm }) => {
    setIsSubmitting(true);
    StarshipAPI.editStarship(
      etag, match.params.starshipId,
      values.name, values.crewCapacity,
      values.maximumWeight, values.fuelCapacity,
      values.maximumSpeed, values.yearOfManufacture,
    )
      .then(() => {
        resetForm({});
        setError(null);
        setSuccess(true);
        dispatch(fetchStarshipEmployeeDetails(match.params.starshipId));
      }).catch((e) => {
        setSuccess(false);
        setError(e);
      }).finally(() => setIsSubmitting(false));
  };

  if (isFetching || isSubmitting) {
    return <ProgressBar />;
  }
  return (
    <div className="details-form">
      {success && <PopUp text={t('edit_starship:success')} />}
      <Formik
        onSubmit={onSubmit}
        initialValues={{
          name, maximumWeight, crewCapacity, fuelCapacity, maximumSpeed, yearOfManufacture,
        }}
        validationSchema={validationSchema}
      >
        {({
          values, errors, handleChange, handleSubmit,
        }) => (
          <Form onSubmit={handleSubmit}>
            <Form.Row>
              <Form.Group controlId="name" as={Col}>
                <FormControl
                  label={t('name')}
                  value={values.name}
                  isInvalid={errors.name}
                  required
                  onChange={handleChange}
                  error={errors.name}
                />
              </Form.Group>
            </Form.Row>
            <Form.Row>
              <Form.Group controlId="crewCapacity" as={Col}>
                <FormControlNumber
                  label={t('crewCapacity')}
                  value={values.crewCapacity}
                  isInvalid={errors.crewCapacity}
                  required
                  onChange={handleChange}
                  error={errors.crewCapacity}
                  min={0}
                  step={1}
                />
              </Form.Group>
              <Form.Group controlId="maximumWeight" as={Col}>
                <FormControlNumber
                  label={`${t('maximumWeight')} [kg]`}
                  value={values.maximumWeight}
                  isInvalid={errors.maximumWeight}
                  required
                  onChange={handleChange}
                  error={errors.maximumWeight}
                  min={0}
                  step="any"
                />
              </Form.Group>
            </Form.Row>
            <Form.Row>
              <Form.Group controlId="maximumSpeed" as={Col}>
                <FormControlNumber
                  label={`${t('maximumSpeed')} [km/h]`}
                  value={values.maximumSpeed}
                  isInvalid={errors.maximumSpeed}
                  required
                  onChange={handleChange}
                  error={errors.maximumSpeed}
                  min={0}
                  step="any"
                />
              </Form.Group>
              <Form.Group controlId="yearOfManufacture" as={Col}>
                <FormControlNumber
                  label={t('yearOfManufacture')}
                  value={values.yearOfManufacture}
                  isInvalid={errors.yearOfManufacture}
                  required
                  onChange={handleChange}
                  error={errors.yearOfManufacture}
                  min={2000}
                  max={new Date().getFullYear()}
                  step={1}
                />
              </Form.Group>
            </Form.Row>
            <Form.Row>
              <Form.Group controlId="fuelCapacity" as={Col}>
                <FormControlNumber
                  label={`${t('fuelCapacity')} [l]`}
                  value={values.fuelCapacity}
                  isInvalid={errors.fuelCapacity}
                  required
                  onChange={handleChange}
                  error={errors.fuelCapacity}
                  min={0}
                  step="any"
                />
              </Form.Group>
            </Form.Row>
            <Button disabled={Object.keys(errors).length !== 0} type="submit" block>
              {t('edit_starship:submit')}
            </Button>
          </Form>
        )}
      </Formik>
      {error
      && (
        <Row style={{ marginTop: '0.5rem', justifyContent: 'center' }}>
          <Col md="auto" className="column-center">
            <GenericAlert message={t(error.message)} />
          </Col>
          <Col md="auto" style={{ display: 'contents' }}>
            <GenericRefreshButton onClick={refresh} />
          </Col>
        </Row>
      )}
      {fetchError
      && (
        <Row style={{ marginTop: '0.3rem' }}>
          <Col md="auto" className="column-center">
            <GenericAlert message={t(error.response.data.message)} />
          </Col>
        </Row>
      )}
    </div>
  );
};

export default EditStarshipDetails;
