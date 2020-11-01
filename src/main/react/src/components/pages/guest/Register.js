import React, {useEffect, useRef, useState} from 'react';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import {useDispatch} from 'react-redux';
import {useTranslation} from 'react-i18next';
import ReCAPTCHA from 'react-google-recaptcha';
import {Formik} from 'formik';
import {buildBreadcrumb, changeTitle} from '../../redux/actions/actions';
import MokAPI from '../../API/MokAPI';
import PopUp from '../../misc/Snackbars';
import GenericAlert from '../../misc/GenericAlert';
import {HomeBreadcrumb, RegisterBreadcrumb, SITE_KEY} from '../../../resources/AppConstants';
import {ButtonWithConfirmDialog} from '../../misc/BootstrapButtons';
import FormControl from '../../misc/FormControls/FormControl';
import {equalTo, lettersOnly, mottoRegex} from '../../utils/Validators';
import ProgressBar from '../../misc/ProgressBar';
import i18n from '../../../i18n';

const Register = () => {
  const { t } = useTranslation(['register', 'error', 'edit_account']);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const dispatch = useDispatch();
  const [captcha, setCaptcha] = useState(null);
  const captchaRef = useRef(null);

  const handleSubmit = async (values, { resetForm }) => {
    try {
      setIsSubmitting(true);
      await MokAPI.register(values.email, values.password, values.firstName,
        values.lastName, values.motto, i18n.language, captcha);
      resetForm({});
      setError(null);
      setSuccess(true);
    } catch (e) {
      setSuccess(false);
      setError(e);
    } finally {
      if (captchaRef.current !== null) { captchaRef.current.reset(); }
      setCaptcha(null);
      setIsSubmitting(false);
    }
  };

  useEffect(() => {
    setSuccess(false);
    dispatch(buildBreadcrumb([HomeBreadcrumb, RegisterBreadcrumb]));
    dispatch(changeTitle(t('page_title')));
  }, [dispatch, t]);

  const yup = require('yup');
  yup.addMethod(yup.string, 'equalTo', equalTo);

  const validationSchema = yup.object().shape({
    email: yup.string().trim().email(t('register:warning.email')).required(t('error:change_password.required'))
      .max(254, t('error:account.email_too_long')),
    password: yup.string().trim().required(t('error:change_password.required')).min(8, t('register:warning.password_not_long_enough')),
    repeatPassword: yup.string().trim().required(t('error:change_password.required')).equalTo(yup.ref('password'), t('register:warning.password_not_identical'))
      .min(8, t('register:warning.password_not_long_enough')),
    firstName: yup.string().trim().required(t('edit_account:warning.first_name_warning')).max(16, t('error:account.wrong_firstname'))
      .matches(lettersOnly, t('edit_account:warning.first_name_warning')),
    lastName: yup.string().trim().required(t('edit_account:warning.last_name_warning')).max(32, t('error:account.wrong_lastname'))
      .matches(lettersOnly, t('edit_account:warning.last_name_warning')),
    motto: yup.string().trim().required(t('edit_account:warning.motto_warning')).max(128, t('error:account:motto_too_long'))
      .matches(mottoRegex, t('error:account.incorrect_motto')),

  });

  if (isSubmitting) {
    return <ProgressBar />;
  }
  return (
    <div className="details-form">
      {success && <PopUp text={t('register:success')} />}
      <Formik
        enableReinitialize
        onSubmit={handleSubmit}
        initialValues={{
          email: '', password: '', repeatPassword: '', firstName: '', lastName: '', motto: '',
        }}
        validationSchema={validationSchema}
      >
        {({
          values, errors, handleChange, handleSubmit,
        }) => (
          <Form onSubmit={handleSubmit}>
            <Form.Group controlId="email">
              <FormControl
                label={t('email')} value={values.email}
                isInvalid={errors.email}
                required
                onChange={handleChange}
                error={errors.email}
                type="email"
              />
            </Form.Group>
            <Form.Row>
              <Form.Group controlId="firstName" as={Col}>
                <FormControl
                      label={t('first_name')} value={values.firstName}
                      isInvalid={errors.firstName}
                      required
                      onChange={handleChange}
                      error={errors.firstName}
                    />
              </Form.Group>
              <Form.Group controlId="lastName" as={Col}>
                <FormControl
                      label={t('last_name')} value={values.lastName}
                      isInvalid={errors.lastName}
                      required
                      onChange={handleChange}
                      error={errors.lastName}
                    />
              </Form.Group>
            </Form.Row>
            <Form.Row>
              <Form.Group controlId="password" as={Col}>
                <FormControl
                      label={t('password')} value={values.password}
                      isInvalid={errors.password}
                      required
                      onChange={handleChange}
                      error={errors.password}
                      type="password"
                    />
              </Form.Group>
              <Form.Group controlId="repeatPassword" as={Col}>
                <FormControl
                      label={t('repeat_password')} value={values.repeatPassword}
                      isInvalid={errors.repeatPassword}
                      required
                      onChange={handleChange}
                      error={errors.repeatPassword}
                      type="password"
                    />
              </Form.Group>
            </Form.Row>
            <Form.Group controlId="motto">
              <FormControl
                label={t('motto')} value={values.motto}
                isInvalid={errors.motto}
                required
                onChange={handleChange}
                error={errors.motto}
              />
            </Form.Group>
            <Form.Group controlId="captcha">
              <div className="center-captcha">
                <ReCAPTCHA
                      ref={captchaRef}
                      sitekey={SITE_KEY}
                      onChange={(value) => setCaptcha(value)}
                    />
              </div>
            </Form.Group>
            <ButtonWithConfirmDialog
              onSubmit={handleSubmit}
              disabled={captcha == null || Object.keys(errors).length !== 0}
            >
              {t('create_account')}
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

export default Register;
