import React, {useEffect, useState} from 'react';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import {useDispatch} from 'react-redux';
import {Link} from 'react-router-dom';
import {useTranslation} from 'react-i18next';
import {Formik} from 'formik';
import {
  buildBreadcrumb,
  changeTitle,
  setCredentials,
  switchAccessLevel,
} from '../redux/actions/actions';
import MokAPI from '../API/MokAPI';
import history from '../misc/history';
import GenericAlert from '../misc/GenericAlert';
import {HomeBreadcrumb, LoginBreadcrumb, ROUTES} from '../../resources/AppConstants';
import ProgressBar from '../misc/ProgressBar';
import FormControl from '../misc/FormControls/FormControl';

const Login = () => {
  const { t } = useTranslation(['login', 'common', 'error']);
  const dispatch = useDispatch();
  const [error, setError] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = async (values, { resetForm }) => {
    try {
      setIsSubmitting(true);
      const auth = await MokAPI.login(values.email, values.password);
      dispatch(setCredentials(auth));
      dispatch(switchAccessLevel(auth.claims.auth[0]));
      history.push(ROUTES.AFTER_LOGIN);
    } catch (e) {
      resetForm({});
      setError(e);
      setIsSubmitting(false);
    }
  };

  useEffect(() => {
    dispatch(buildBreadcrumb([HomeBreadcrumb, LoginBreadcrumb]));
    dispatch(changeTitle(t('common:login')));
  }, [dispatch, t]);

  const yup = require('yup');

  const validationSchema = yup.object().shape({
    email: yup.string().email(t('error:validation.email')).required(t('error:change_password.required')),
    password: yup.string().required(t('error:change_password.required')).min(8, t('error:account.password_too_short')),
  });

  if (isSubmitting) {
    return <ProgressBar />;
  }
  return (
    <div className="basic-form">
      <Formik
        onSubmit={handleSubmit}
        initialValues={{ email: '', password: '' }}
        validationSchema={validationSchema}
      >
        {({
          values, errors, handleChange, handleSubmit,
        }) => (
          <Form onSubmit={handleSubmit}>
            <Form.Group controlId="email">
              <FormControl
                label={t('email')}
                value={values.email}
                isInvalid={errors.email}
                required
                onChange={handleChange}
                error={errors.email}
              />
            </Form.Group>
            <Form.Group controlId="password">
              <FormControl
                label={t('password')}
                value={values.password}
                isInvalid={errors.password}
                required
                type="password"
                onChange={handleChange}
                error={errors.password}
              />
            </Form.Group>
            <Button type="submit" block>{t('common:login')}</Button>
          </Form>
        )}
      </Formik>
      <Row style={{ justifyContent: 'space-between', alignContent: 'center' }}>
        <Col md="auto" style={{ alignContent: 'center' }}>
          <Link
            to={ROUTES.INIT_RESET_PASSWORD}
            style={{ textDecoration: 'none', color: 'rgb(48, 22, 93)' }}
          >
            {t('recoverPassword')}
          </Link>
        </Col>
        <Col md="auto" style={{ alignContent: 'center' }}>
          <Link to={ROUTES.REGISTER} style={{ textDecoration: 'none', color: 'rgb(48, 22, 93)' }}>
            {t('register')}
          </Link>
        </Col>
      </Row>
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

export default Login;
