import React, {useEffect, useState} from 'react';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import {useDispatch} from 'react-redux';
import {useTranslation} from 'react-i18next';
import * as qs from 'query-string';
import {Formik} from 'formik';
import Form from 'react-bootstrap/Form';
import {buildBreadcrumb, changeTitle} from '../../redux/actions/actions';
import MokAPI from '../../API/MokAPI';
import PopUp from '../../misc/Snackbars';
import GenericAlert from '../../misc/GenericAlert';
import ProgressBar from '../../misc/ProgressBar';
import FormControl from '../../misc/FormControls/FormControl';
import {equalTo} from '../../utils/Validators';
import {ButtonWithConfirmDialog} from '../../misc/BootstrapButtons';
import {FinishResetPasswordBreadcrumb, HomeBreadcrumb} from '../../../resources/AppConstants';

const FinishResetPassword = ({ location }) => {
  const { t } = useTranslation(['finish_reset_password', 'error']);
  const dispatch = useDispatch();
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = async (values, { resetForm }) => {
    try {
      setIsSubmitting(true);
      const { token } = qs.parse(location.search);
      await MokAPI.confirmResetPassword(token, values.password);
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

  useEffect(() => {
    setSuccess(false);
    dispatch(buildBreadcrumb([HomeBreadcrumb, FinishResetPasswordBreadcrumb]));
    dispatch(changeTitle(t('page_title')));
  }, [dispatch, t]);

  const yup = require('yup');
  yup.addMethod(yup.string, 'equalTo', equalTo);
  const validationSchema = yup.object().shape({
    password: yup.string().required(t('error:change_password.required')).min(8, t('error:change_password.new_password_too_short')),
    repeatPassword: yup.string().required(t('error:change_password.required')).equalTo(yup.ref('password'), t('error:change_password.password_does_not_match')).min(8, t('error:change_password.new_password_too_short')),
  });

  if (isSubmitting) {
    return <ProgressBar />;
  }
  return (
    <div className="details-form">
      {success && <PopUp text={t('success')} />}
      <Formik
        onSubmit={handleSubmit}
        initialValues={{ email: '', repeatPassword: '' }}
        validationSchema={validationSchema}
      >
        {({
          values, errors, handleChange, handleSubmit,
        }) => (
          <Form onSubmit={handleSubmit}>
            <Form.Group controlId="password" as={Row}>
              <FormControl
                label={t('password')} value={values.password}
                isInvalid={errors.password}
                required
                onChange={handleChange}
                error={errors.password}
                type="password"
              />
            </Form.Group>
            <Form.Group controlId="repeatPassword" as={Row}>
              <FormControl
                label={t('repeat_password')} value={values.repeatPassword}
                isInvalid={errors.repeatPassword}
                required
                onChange={handleChange}
                error={errors.repeatPassword}
                type="password"
              />
            </Form.Group>
            <ButtonWithConfirmDialog onSubmit={handleSubmit}>{t('submit')}</ButtonWithConfirmDialog>
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

export default FinishResetPassword;
