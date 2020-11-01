import React, {useEffect, useRef, useState} from 'react';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import {useDispatch, useSelector} from 'react-redux';
import {useTranslation} from 'react-i18next';
import {Formik} from 'formik';
import ReCAPTCHA from 'react-google-recaptcha';
import {buildBreadcrumb, changeTitle, setForcePasswordChange} from '../redux/actions/actions';
import MokAPI from '../API/MokAPI';
import GenericAlert from '../misc/GenericAlert';
import PopUp from '../misc/Snackbars';
import FormControl from '../misc/FormControls/FormControl';
import {equalTo, notEqualTo} from '../utils/Validators';
import {ButtonWithConfirmDialog} from '../misc/BootstrapButtons';

import {
  AccountListBreadcrumb,
  ChangePasswordBreadcrumb,
  ChangeUsersPasswordBreadcrumb,
  HomeBreadcrumb,
  SITE_KEY,
} from '../../resources/AppConstants';
import ProgressBar from '../misc/ProgressBar';

const ChangePassword = ({ match }) => {
  const { t } = useTranslation(['change_password', 'error']);
  const dispatch = useDispatch();
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);
  let email = useSelector((state) => state.auth.claims.sub);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [captcha, setCaptcha] = useState(null);

  const captchaRef = useRef(null);

  if (match) {
    email = match.params.email;
  }

  useEffect(() => {
    setSuccess(false);
    if (match) {
      const bc = ChangeUsersPasswordBreadcrumb(match.params.email);
      dispatch(buildBreadcrumb([HomeBreadcrumb, AccountListBreadcrumb, bc]));
    } else {
      dispatch(buildBreadcrumb([HomeBreadcrumb, ChangePasswordBreadcrumb]));
    }
    dispatch(changeTitle(t('page_title')));
  }, [match, dispatch, t]);

  const handleSubmit = async (values, { resetForm }) => {
    try {
      setIsSubmitting(true);
      await MokAPI.changeOwnPassword(email, values.oldPassword, values.newPassword, captcha);
      dispatch(setForcePasswordChange(false));
      resetForm({});
      setSuccess(true);
      setError(null);
    } catch (e) {
      setSuccess(false);
      setError(e);
    } finally {
      if (captchaRef.current !== null) {
        captchaRef.current.reset();
      }
      setCaptcha(null);
      setIsSubmitting(false);
    }
  };

  const handleSubmitAdmin = async (values, { resetForm }) => {
    try {
      setIsSubmitting(true);
      await MokAPI.changeSomeonesPassword(email, values.newPassword);
      resetForm({});
      setSuccess(true);
      setError(null);
    } catch (e) {
      setSuccess(false);
      setError(e);
    } finally {
      setIsSubmitting(false);
    }
  };

  const yup = require('yup');
  yup.addMethod(yup.string, 'equalTo', equalTo);
  yup.addMethod(yup.string, 'notEqualTo', notEqualTo);

  const validationSchema = yup.object().shape({
    oldPassword: yup.string().trim().min(8, t('error:change_password.old_password_too_short')).required(t('error:change_password.required')),
    newPassword: yup.string().trim().required(t('error:change_password.required')).notEqualTo(yup.ref('oldPassword'), t('error:change_password.password_is_the_same'))
      .min(8, t('error:change_password.new_password_too_short')),
    repeatNewPassword: yup.string().trim().required(t('error:change_password.required')).equalTo(yup.ref('newPassword'), t('error:change_password.password_does_not_match'))
      .notEqualTo(yup.ref('oldPassword'), t('error:change_password.password_is_the_same'))
      .min(8, t('error:change_password.new_password_too_short')),

  });

  const validationSchemaAdmin = yup.object().shape({
    newPassword: yup.string().required(t('error:change_password.required')).min(8, t('error:change_password.new_password_too_short')),
    repeatNewPassword: yup.string().required(t('error:change_password.required')).equalTo(yup.ref('newPassword'), t('error:change_password.password_does_not_match')),
  });

  if (isSubmitting) {
    return <ProgressBar />;
  }
  return (
    <div className="details-form">
      <h2>{t('page_title')}</h2>
      {success && <PopUp text={t('password_changed')} />}
      {match !== undefined && (
        <Row>
          <Col md="auto" style={{ width: '100%' }}>
            <Form.Group>
              <Form.Label>{t('change_password:users_email')}</Form.Label>
              <Form.Control plaintext readOnly defaultValue={match.params.email} />
            </Form.Group>
          </Col>
        </Row>
      )}
      {match === undefined
        ? (
          <Formik
            onSubmit={handleSubmit}
            initialValues={{ oldPassword: '', newPassword: '', repeatNewPassword: '' }}
            validationSchema={validationSchema}
          >
            {({
              values, errors, handleChange, handleSubmit,
            }) => (
              <Form onSubmit={handleSubmit}>
                <Form.Group controlId="oldPassword" as={Row}>
                  <FormControl
                    label={t('old_password')}
                    value={values.oldPassword}
                    isInvalid={errors.oldPassword}
                    required
                    onChange={handleChange}
                    error={errors.oldPassword}
                    type="password"
                  />
                </Form.Group>
                <Form.Group controlId="newPassword" as={Row}>
                  <FormControl
                    label={t('new_password')}
                    value={values.newPassword}
                    isInvalid={errors.newPassword}
                    required
                    onChange={handleChange}
                    error={errors.newPassword}
                    type="password"
                  />
                </Form.Group>
                <Form.Group controlId="repeatNewPassword" as={Row}>
                  <FormControl
                    label={t('repeat_password')}
                    value={values.repeatNewPassword}
                    isInvalid={errors.repeatNewPassword}
                    required
                    onChange={handleChange}
                    error={errors.repeatNewPassword}
                    type="password"
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
                  disabled={captcha == null}
                  onSubmit={handleSubmit}
                >
                  {t('submit')}
                </ButtonWithConfirmDialog>
              </Form>
            )}
          </Formik>
        )
        : (
          <Formik
            onSubmit={handleSubmitAdmin}
            initialValues={{ newPassword: '', repeatNewPassword: '' }}
            validationSchema={validationSchemaAdmin}
          >
            {({
              values, errors, handleChange, handleSubmit,
            }) => (
              <Form onSubmit={handleSubmit}>
                <Form.Group controlId="newPassword" as={Row}>
                  <FormControl
                    label={t('new_password')}
                    value={values.newPassword}
                    isInvalid={errors.newPassword}
                    required
                    onChange={handleChange}
                    error={errors.newPassword}
                    type="password"
                  />
                </Form.Group>
                <Form.Group controlId="repeatNewPassword" as={Row}>
                  <FormControl
                    label={t('repeat_password')}
                    value={values.repeatNewPassword}
                    isInvalid={errors.repeatNewPassword}
                    required
                    onChange={handleChange}
                    error={errors.repeatNewPassword}
                    type="password"
                  />
                </Form.Group>
                <ButtonWithConfirmDialog onSubmit={handleSubmit}>{t('submit')}</ButtonWithConfirmDialog>
              </Form>
            )}
          </Formik>
        )}
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

export default ChangePassword;
