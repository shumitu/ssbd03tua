import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {useTranslation} from 'react-i18next';
import {Field, Formik} from 'formik';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import {MuiPickersUtilsProvider} from '@material-ui/pickers';
import {KeyboardDateTimePicker} from 'material-ui-formik-components';
import DateFnsUtils from '@date-io/date-fns';
import plLocale from 'date-fns/locale/pl';
import {buildBreadcrumb, changeTitle} from '../redux/actions/actions';
import {EditOfferBreadcrumb, HomeBreadcrumb} from '../../resources/AppConstants';
import ProgressBar from '../misc/ProgressBar';
import PopUp from '../misc/Snackbars';
import GenericAlert from '../misc/GenericAlert';
import FormControl from '../misc/FormControls/FormControl';
import FormControlNumber from '../misc/FormControls/FormControlNumber';
import {ButtonWithConfirmDialog} from '../misc/BootstrapButtons';
import {mottoRegex} from '../utils/Validators';
import OfferAPI from '../API/OfferAPI';
import enLocale from 'date-fns/locale/en-GB';
import i18n from '../../i18n';
import {GenericRefreshButton} from '../List/TableButtons';
import {fetchOfferEmployeeDetails} from '../redux/actions/Offer/OfferDetails';

const EditOfferDetails = ({ match }) => {
  const { t } = useTranslation(['edit_offer', 'common', 'error']);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const dispatch = useDispatch();
  const offerDetails = useSelector((state) => state.offerDetails);

  useEffect(() => {
    setSuccess(false);
    const editOfferBreadcrumb = EditOfferBreadcrumb(match.params.offerId);
    dispatch(buildBreadcrumb([HomeBreadcrumb, editOfferBreadcrumb]));
    dispatch(changeTitle(t('page_title')));
    dispatch(fetchOfferEmployeeDetails(match.params.offerId));
  }, [dispatch, match, t]);

  const refresh = () => {
    dispatch(fetchOfferEmployeeDetails(match.params.offerId));
    setSuccess(false);
    setError(null);
  };

  const handleSubmit = (values, { resetForm }) => {
    setIsSubmitting(true);
    console.log(values);
    OfferAPI.updateOffer(
      offerDetails.data.etag, match.params.offerId, values.flightStartTime, values.flightEndTime,
      values.destination, values.description, values.price, values.totalCost,
    ).then(() => {
      resetForm({});
      setError(null);
      dispatch(fetchOfferEmployeeDetails(match.params.offerId));
      setSuccess(true);
    }).catch((e) => {
      setSuccess(false);
      setError(e);
    }).finally(() => setIsSubmitting(false));
  };

  const yup = require('yup');
  const validationSchema = yup.object().shape({
    destination: yup.string()
      .trim()
      .required(t('error:validation.required'))
      .matches(mottoRegex, t('error:offer.incorrect_destination'))
      .max(256, t('error:offer.too_long')),
    description: yup.string()
      .trim()
      .required(t('error:validation.required'))
      .matches(mottoRegex, t('error:offer.incorrect_description')),
    price: yup.number()
      .integer(t('error:validation.number.integer'))
      .required(t('error:validation.number.incorrect'))
      .moreThan(0, t('error:validation.number.not_positive_value')),
    totalCost: yup.number()
      .integer(t('error:validation.number.integer'))
      .required(t('error:validation.number.incorrect'))
      .moreThan(0, t('error:validation.number.not_positive_value')),
  });

  if (isSubmitting || offerDetails.isLoading) {
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
          destination: offerDetails.data.destination,
          price: offerDetails.data.price,
          description: offerDetails.data.description,
          totalCost: offerDetails.data.totalCost,
          flightStartTime: offerDetails.data.flightStartTimeNotParsed,
          flightEndTime: offerDetails.data.flightEndTimeNotParsed,
        }}
      >
        {({
          values, errors, handleChange, handleSubmit,
        }) => (
          <Form onSubmit={handleSubmit} noValidate>
            <Form.Row>
              <Form.Group controlId="destination" as={Col}>
                <FormControl
                          label={t('destination')} value={values.destination}
                          isInvalid={errors.destination}
                          required
                          onChange={handleChange}
                          error={errors.destination}
                        />
              </Form.Group>
            </Form.Row>
            <Form.Row>
              <Form.Group controlId="description" as={Col}>
                <FormControl
                          label={t('description')} value={values.description}
                          isInvalid={errors.description}
                          required
                          onChange={handleChange}
                          error={errors.description}
                        />
              </Form.Group>
            </Form.Row>
            <Form.Row>
              <Form.Group controlId="price" as={Col}>
                <FormControlNumber
                          label={t('price')} value={values.price}
                          isInvalid={errors.price}
                          required
                          onChange={handleChange}
                          error={errors.price}
                          min={0}
                          step={1}
                        />
              </Form.Group>
              <Form.Group controlId="totalCost" as={Col}>
                <FormControlNumber
                          label={t('cost')} value={values.totalCost}
                          isInvalid={errors.totalCost}
                          required
                          onChange={handleChange}
                          error={errors.totalCost}
                          min={0}
                          step="any"
                        />
              </Form.Group>
            </Form.Row>
            <Form.Row>
              <Form.Group controlId="startDate" as={Col}>
                <MuiPickersUtilsProvider
                          utils={DateFnsUtils}
                          locale={(i18n.language === 'en-US' || i18n.language === 'en-GB')
            ? enLocale : plLocale}
                        >
                          <Field
            required
            variant="inline"
            minDate={new Date()}
            name="flightStartTime"
            component={KeyboardDateTimePicker}
            label={t('start_time')}
            format="dd/MM/yyyy, HH:mm:ss"
            size="big"
            ampm={false}
            inputVariant="outlined"
          />
                        </MuiPickersUtilsProvider>

              </Form.Group>
              <Form.Group controlId="endDate" as={Col}>
                <MuiPickersUtilsProvider
                          utils={DateFnsUtils}
                          locale={(i18n.language === 'en-US' || i18n.language === 'en-GB')
            ? enLocale : plLocale}
                        >
                          <Field
            variant="inline"
            required
            minDate={new Date(new Date().setDate(new Date().getDate() + 1))}
            name="flightEndTime"
            component={KeyboardDateTimePicker}
            label={t('end_time')}
            format="dd/MM/yyyy, HH:mm:ss"
            size="big"
            ampm={false}
            inputVariant="outlined"
          />
                        </MuiPickersUtilsProvider>
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
      {
                    error
                && (
                <Row style={{ marginTop: '0.5rem', justifyContent: 'center' }}>
                  <Col md="auto" className="column-center" style={{ width: '100%' }}>
                    <GenericAlert message={t(error.message)} />
                  </Col>
                  <Col md="auto" style={{ display: 'contents' }}>
                    <GenericRefreshButton onClick={refresh} />
                  </Col>
                </Row>
                )
                }
    </div>
  );
};

export default EditOfferDetails;
