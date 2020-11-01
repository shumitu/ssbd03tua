import React, {useEffect, useState} from 'react';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import {useDispatch} from 'react-redux';
import {useTranslation} from 'react-i18next';
import {Formik} from 'formik';
import {buildBreadcrumb, changeTitle} from '../../redux/actions/actions';
import MokAPI from '../../API/MokAPI';
import GenericAlert from '../../misc/GenericAlert';
import PopUp from '../../misc/Snackbars';
import i18n from '../../../i18n';
import ProgressBar from '../../misc/ProgressBar';
import FormControl from '../../misc/FormControls/FormControl';
import {ButtonWithConfirmDialog} from '../../misc/BootstrapButtons';
import {HomeBreadcrumb, InitResetPasswordBreadcrumb} from '../../../resources/AppConstants';

const InitResetPassword = () => {
  const { t } = useTranslation(['init_reset_password', 'error', 'register']);
  const dispatch = useDispatch();
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = async (values, { resetForm }) => {
    try {
      setIsSubmitting(true);
      await MokAPI.initResetPassword(values.email, i18n.language);
      resetForm({});
      setError(null);
      setSuccess(true);
    } catch (e) {
      setSuccess(false);
      setError(e);
    } finally {
      setIsSubmitting(false);
    }
  };

  useEffect(() => {
    setSuccess(false);
    dispatch(buildBreadcrumb([HomeBreadcrumb, InitResetPasswordBreadcrumb]));
    dispatch(changeTitle(t('page_title')));
  }, [dispatch, t]);

  const yup = require('yup');
  const validationSchema = yup.object().shape({
    email: yup.string().trim().email(t('register:warning.email')).required(t('error:change_password.required'))
      .max(254, t('error.account.email_too_long')),
  });

  if (isSubmitting) {
    return <ProgressBar />;
  }
  return (
    <div className="details-form">
      {success && <PopUp text={t('success')} />}
      <Formik
        onSubmit={handleSubmit}
        initialValues={{ email: '' }}
        validationSchema={validationSchema}
      >
        {({
          values, errors, handleChange, handleSubmit,
        }) => (
          <Form onSubmit={handleSubmit}>
            <Form.Group controlId="email" as={Row}>
              <FormControl
                label={t('email')} value={values.email}
                isInvalid={errors.email}
                required
                onChange={handleChange}
                error={errors.email}
                type="email"
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

export default InitResetPassword;
