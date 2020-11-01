import React, { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { Formik } from 'formik';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import PopUp from '../../misc/Snackbars';
import ProgressBar from '../../misc/ProgressBar';
import { AddStarshipBreadcrumb, HomeBreadcrumb } from '../../../resources/AppConstants';
import { buildBreadcrumb, changeTitle } from '../../redux/actions/actions';
import GenericAlert from '../../misc/GenericAlert';
import FormControl from '../../misc/FormControls/FormControl';
import FormControlNumber from '../../misc/FormControls/FormControlNumber';
import FormControlSelect from '../../misc/FormControls/FormControlSelect';
import { ButtonWithConfirmDialog } from '../../misc/BootstrapButtons';
import { mottoRegex } from '../../utils/Validators';
import StarshipAPI from '../../API/StarshipAPI';

const AddStarship = () => {
  const { t } = useTranslation(['add_starship', 'common', 'error']);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const dispatch = useDispatch();

  useEffect(() => {
    setSuccess(false);
    dispatch(buildBreadcrumb([HomeBreadcrumb, AddStarshipBreadcrumb]));
    dispatch(changeTitle(t('page_title')));
  }, [dispatch, t]);

  const handleSubmit = (values, { resetForm }) => {
    setIsSubmitting(true);
    StarshipAPI.addStarship(
      values.name, values.crewCapacity,
      values.maximumWeight, values.fuelCapacity,
      values.maximumSpeed, values.yearOfManufacture,
      values.operational,
    )
      .then(() => {
        resetForm({});
        setError(null);
        setSuccess(true);
      }).catch((e) => {
        setSuccess(false);
        setError(e);
      }).finally(() => setIsSubmitting(false));
  };

  const yup = require('yup');
  const validationSchema = yup.object().shape({
    name: yup.string()
      .trim()
      .required(t('error:validation.required'))
      .matches(mottoRegex, t('error:starship.name.incorrect'))
      .max(32, t('error:starship.name.too_long')),
    operational: yup.boolean().required(t('error:validation.required')),
    crewCapacity: yup.number().integer(t('error:validation.number.integer')).required(t('error:validation.number.incorrect')).moreThan(0, t('error:starship.not_positive_value')),
    fuelCapacity: yup.number().required(t('error:validation.number.incorrect')).moreThan(0, t('error:starship.not_positive_value')),
    maximumSpeed: yup.number().required(t('error:validation.number.incorrect')).moreThan(0, t('error:starship.not_positive_value')),
    yearOfManufacture: yup.number().integer(t('error:validation.number.integer')).required(t('error:validation.number.incorrect')).moreThan(2000, t('error:starship.year.incorrect')),
  });

  if (isSubmitting) {
    return <ProgressBar />;
  }
  return (
    <div className="details-form">
      {success && <PopUp text={t('success')} />}
      <Formik
        validationSchema={validationSchema}
        enableReinitialize
        onSubmit={handleSubmit}
        initialValues={{
          name: '',
          crewCapacity: 0,
          maximumWeight: 0.0,
          fuelCapacity: 0.0,
          maximumSpeed: 0.0,
          yearOfManufacture: 2001,
          operational: true,
        }}
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
              <Form.Group controlId="operational" as={Col}>
                <FormControlSelect
                  label={t('operational')}
                  value={values.operational}
                  required
                  onChange={handleChange}
                >
                  <option value>{t('common:Yes')}</option>
                  <option value={false}>{t('common:No')}</option>
                </FormControlSelect>
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
            <ButtonWithConfirmDialog
              onSubmit={handleSubmit}
              disabled={Object.keys(errors).length !== 0}
            >
              {t('submit')}
            </ButtonWithConfirmDialog>
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

export default AddStarship;
