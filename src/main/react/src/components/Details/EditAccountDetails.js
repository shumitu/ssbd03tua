import React, {useEffect, useRef, useState} from 'react';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import {useTranslation} from 'react-i18next';
import {useDispatch, useSelector} from 'react-redux';
import Row from 'react-bootstrap/Row';
import Form from 'react-bootstrap/Form';
import {Formik} from 'formik';
import ReCAPTCHA from 'react-google-recaptcha';
import {buildBreadcrumb, changeTitle} from '../redux/actions/actions';
import ProgressBar from '../misc/ProgressBar';
import {fetchAccountDetails, fetchMyAccountDetails} from '../redux/actions/Account/AccountDetails';
import FormControl from '../misc/FormControls/FormControl';
import {lettersOnly, mottoRegex} from '../utils/Validators';
import PopUp from '../misc/Snackbars';
import GenericAlert from '../misc/GenericAlert';
import MokAPI from '../API/MokAPI';
import {
  AccountDetailsBreadcrumb,
  AccountListBreadcrumb,
  EditAccountBreadcrumb,
  EditMyAccountBreadcrumb,
  HomeBreadcrumb,
  MyAccountBreadcrumb,
  SITE_KEY,
} from '../../resources/AppConstants';
import {GenericRefreshButton} from '../List/TableButtons';

const EditAccountDetails = ({ match, adminMode = false }) => {
  const { t } = useTranslation(['edit_account', 'error']);
  const dispatch = useDispatch();
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);

  const data = useSelector((state) => state.accountDetails.data);
  const isFetching = useSelector((state) => state.accountDetails.isLoading);
  const fetchError = useSelector((state) => state.accountDetails.error);

  const [captcha, setCaptcha] = useState(null);

  const captchaRef = useRef(null);

  useEffect(() => {
    setSuccess(false);
    dispatch(changeTitle(t('page_title')));
    if (!adminMode) {
      dispatch(buildBreadcrumb([HomeBreadcrumb, MyAccountBreadcrumb, EditMyAccountBreadcrumb]));
      dispatch(fetchMyAccountDetails());
    } else {
      const accountDetailsBreadcrumb = AccountDetailsBreadcrumb(match.params.email);
      const editAccountBreadcrumb = EditAccountBreadcrumb(match.params.email);
      dispatch(buildBreadcrumb([HomeBreadcrumb, AccountListBreadcrumb, accountDetailsBreadcrumb, editAccountBreadcrumb]));
      dispatch(fetchAccountDetails(match.params.email));
    }
  }, [dispatch, match, t, adminMode]);

  const refresh = () => {
    if (!adminMode) {
      dispatch(fetchMyAccountDetails());
    } else {
      dispatch(fetchAccountDetails(match.params.email));
    }
    setSuccess(false);
    setError(null);
  };

  const haveNames = () => {
    if (data.candidate) {
      if (data.candidate.firstName.trim() !== '' && data.candidate.lastName.trim() !== '') {
        return true;
      }
    }
    return false;
  };

  const handleSubmitCandidate = async ({ firstName, lastName, motto }) => {
    try {
      setError(null);
      const candidate = {
        etag: data.candidate.etag,
        firstName,
        lastName,
      };

      if (adminMode) {
        await MokAPI.updateOtherAccount(data.etag, match.params.email, candidate, motto);
        dispatch(fetchAccountDetails(match.params.email));
        setSuccess(true);
      } else {
        await MokAPI.updateOwnAccount(data.etag, match.params.email, candidate, motto, captcha);
        dispatch(fetchMyAccountDetails());
        setSuccess(true);
      }
    } catch (e) {
      setError(e);
    } finally {
      if (captchaRef.current !== null) { captchaRef.current.reset(); }
      setCaptcha(null);
    }
  };

  const handleSubmitMottoOnly = async ({ motto }) => {
    try {
      setError(null);
      if (adminMode) {
        await MokAPI.updateOtherAccount(data.etag, match.params.email, null, motto);
        dispatch(fetchAccountDetails(match.params.email));
        setSuccess(true);
      } else {
        await MokAPI.updateOwnAccount(data.etag, match.params.email, null, motto, captcha);
        dispatch(fetchMyAccountDetails());
        setSuccess(true);
      }
    } catch (e) {
      setError(e);
      if (captchaRef.current !== null) { captchaRef.current.reset(); }
    } finally {
      setCaptcha(null);
    }
  };

  const getFirstName = () => {
    if (data.candidate === null || data.candidate === undefined) return '';
    return data.candidate.firstName;
  };

  const getLastName = () => {
    if (data.candidate === null || data.candidate === undefined) return '';
    return data.candidate.lastName;
  };
  const firstName = getFirstName();
  const lastName = getLastName();
  const motto = useSelector((state) => state.accountDetails.data.motto);

  const yup = require('yup');

  const validationSchema = yup.object().shape({
    firstName: yup.string().trim().required(t('warning.first_name_warning')).max(16, t('error:account.wrong_firstname'))
      .matches(lettersOnly, t('warning.first_name_warning')),
    lastName: yup.string().trim().required(t('warning.last_name_warning')).max(32, t('error:account.wrong_lastname'))
      .matches(lettersOnly, t('warning.last_name_warning')),
    motto: yup.string().trim().required(t('warning.motto_warning')).max(128, t('error:account.motto_too_long'))
      .matches(mottoRegex, t('error:account.incorrect_motto')),
  });

  const validationSchemaMotto = yup.object().shape({
    motto: yup.string().required(t('warning.motto_warning')).max(128, t('error:account.motto_too_long')),
  });

  if (isFetching) return <ProgressBar />;

  return (
    <div className="details-form">
      {success && <PopUp text={t('success')} />}
      {haveNames()
        ? (
          <Formik
            onSubmit={handleSubmitCandidate}
            initialValues={{ firstName, lastName, motto }}
            validationSchema={validationSchema}
          >
            {({
              values, errors, handleChange, handleSubmit,
            }) => (
              <Form onSubmit={handleSubmit}>
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
                      <Form.Group controlId="motto">
                          <FormControl
                                label={t('motto')} value={values.motto}
                                isInvalid={errors.motto}
                                required
                                onChange={handleChange}
                                error={errors.motto}
                              />
                        </Form.Group>
                      {!adminMode
                                && (
                                <Form.Group controlId="captcha">
                                  <div className="center-captcha">
                                    <ReCAPTCHA
                                      ref={captchaRef}
                                      sitekey={SITE_KEY}
                                      onChange={(value) => setCaptcha(value)}
                                    />
                                  </div>
                                </Form.Group>
                                )}
                      <Button disabled={captcha == null && !adminMode} type="submit" block>
                          {t('save')}
                        </Button>
                    </Form>
            )}
          </Formik>
        )
        : (
          <Formik
            onSubmit={handleSubmitMottoOnly}
            initialValues={{ motto }}
            validationSchema={validationSchemaMotto}
          >
            {({
              values, errors, handleChange, handleSubmit,
            }) => (
              <Form onSubmit={handleSubmit}>
                      <Form.Group controlId="motto">
                            <FormControl
                              label={t('motto')} value={values.motto}
                              isInvalid={errors.motto}
                              required
                              onChange={handleChange}
                              error={errors.motto}
                            />
                          </Form.Group>
                      {!adminMode
                                        && (
                                        <Form.Group controlId="captcha">
                                          <div className="center-captcha">
                                            <ReCAPTCHA
                                              ref={captchaRef}
                                              sitekey={SITE_KEY}
                                              onChange={(value) => setCaptcha(value)}
                                            />
                                          </div>

                                        </Form.Group>
                                        )}
                      <Button disabled={captcha == null && !adminMode} type="submit" block>
                            {t('save')}
                          </Button>
                    </Form>
            )}
          </Formik>
        )}
      {
                    error
                    && (
                    <Row style={{ marginTop: '0.3rem', justifyContent: 'center' }}>
                      <Col md="auto" className="column-center">
                        <GenericAlert message={t(error.message)} />
                      </Col>
                      <Col md="auto" style={{ display: 'contents' }}>
                        <GenericRefreshButton onClick={refresh} />
                      </Col>
                    </Row>
                    )
                }
      {
                    fetchError
                    && (
                    <Row style={{ marginTop: '0.3rem' }}>
                      <Col md="auto" className="column-center">
                        <GenericAlert message={t(error.response.data.message)} />
                      </Col>
                    </Row>
                    )
                }
    </div>
  );
};

export default EditAccountDetails;
