import React, {useEffect, useState} from 'react';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import {useDispatch} from 'react-redux';
import {useTranslation} from 'react-i18next';
import * as qs from 'query-string';
import MokAPI from '../API/MokAPI';
import {buildBreadcrumb, changeTitle} from '../redux/actions/actions';
import GenericAlert from '../misc/GenericAlert';
import i18n from '../../i18n';
import ProgressBar from '../misc/ProgressBar';
import {ActivateAccountBreadcrumb, HomeBreadcrumb} from '../../resources/AppConstants';

const ActivateAccount = ({ location }) => {
  const { t } = useTranslation(['activate_account', 'error']);
  const dispatch = useDispatch();
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    dispatch(buildBreadcrumb([HomeBreadcrumb, ActivateAccountBreadcrumb]));
    dispatch(changeTitle(t('page_title')));
    setIsSubmitting(true);
    const { email } = qs.parse(location.search);
    const { token } = qs.parse(location.search);
    MokAPI.activateAccount(email, token, i18n.language).then(() => {
      setSuccess(true);
      setError(null);
    }).catch((e) => {
      setSuccess(false);
      setError(e);
    }).finally(() => setIsSubmitting(false));
  }, [dispatch, t, location]);

  if (isSubmitting) {
    return <ProgressBar />;
  }
  return (
    <div className="basic-form">
      {success
      && (
        <Row style={{ marginTop: '0.5rem' }}>
          <Col md="auto" className="column-center" style={{ width: '100%' }}>
            <GenericAlert message={t('success')} severity="success" />
          </Col>
        </Row>
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

export default ActivateAccount;
