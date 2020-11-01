import React, {useEffect} from 'react';
import Form from 'react-bootstrap/Form';
import Col from 'react-bootstrap/Col';
import Badge from 'react-bootstrap/Badge';
import Button from 'react-bootstrap/Button';
import {useTranslation} from 'react-i18next';
import {Link} from 'react-router-dom';
import {useDispatch, useSelector} from 'react-redux';
import {buildBreadcrumb, changeTitle} from '../redux/actions/actions';
import {fetchAccountDetails, fetchMyAccountDetails} from '../redux/actions/Account/AccountDetails';
import ProgressBar from '../misc/ProgressBar';
import {
  AccountDetailsBreadcrumb,
  AccountListBreadcrumb,
  HomeBreadcrumb,
  MyAccountBreadcrumb,
  ROUTES,
} from '../../resources/AppConstants';

const EmailForm = ({ data }) => {
  const { t } = useTranslation('account_details');
  if (data.employee) {
    return (
      <Form.Row>
        <Form.Group as={Col} controlId="email">
          <Form.Label>{t('email')}</Form.Label>
          <Form.Control readOnly type="plaintext" defaultValue={data.email} />
        </Form.Group>
        <Form.Group as={Col} controlId="number">
          <Form.Label>{t('employee_number')}</Form.Label>
          <Form.Control readOnly type="plaintext" defaultValue={data.employee.employeeNumber} />
        </Form.Group>
      </Form.Row>
    );
  }
  return (
    <Form.Group controlId="email">
      <Form.Label>{t('email')}</Form.Label>
      <Form.Control readOnly type="plaintext" defaultValue={data.email} />
    </Form.Group>
  );
};

const NamesForm = ({ firstName, lastName }) => {
  const { t } = useTranslation('account_details');
  if (firstName && lastName) {
    return (
      <Form.Row>
        <Form.Group as={Col} controlId="firstName">
          <Form.Label>{t('first_name')}</Form.Label>
          <Form.Control readOnly type="plaintext" defaultValue={firstName} />
        </Form.Group>
        <Form.Group as={Col} controlId="lastName">
          <Form.Label>{t('last_name')}</Form.Label>
          <Form.Control readOnly type="plaintext" defaultValue={lastName} />
        </Form.Group>
      </Form.Row>
    );
  }
};

const ShowAccountDetails = ({ match }) => {
  const { t } = useTranslation('account_details');
  const dispatch = useDispatch();
  const data = useSelector((state) => state.accountDetails.data);
  const isLoading = useSelector((state) => state.accountDetails.isLoading);

  useEffect(() => {
    dispatch(changeTitle(t('page_title')));
    if (match === undefined) {
      dispatch(buildBreadcrumb([HomeBreadcrumb, MyAccountBreadcrumb]));
      dispatch(fetchMyAccountDetails());
    } else {
      const accountDetailsBreadcrumb = AccountDetailsBreadcrumb(match.params.email);
      dispatch(buildBreadcrumb([HomeBreadcrumb, AccountListBreadcrumb, accountDetailsBreadcrumb]));
      dispatch(fetchAccountDetails(match.params.email));
    }
  }, [dispatch, match, t]);

  const getBadge = (val) => {
    if (val) return '✔';
    return '✖';
  };

  const getBadgeVariant = (val) => {
    if (val) return 'success';
    return 'danger';
  };

  const haveNames = () => {
    if (data.candidate) {
      if (data.candidate.firstName.trim() !== '' && data.candidate.lastName.trim() !== '') {
        return true;
      }
    }
    return false;
  };

  if (isLoading) return <ProgressBar />;

  return (
    <div className="details-form">
      <Form>
        {haveNames() ? (
          <NamesForm
            firstName={data.candidate.firstName}
            lastName={data.candidate.lastName}
          />
        ) : null}
        <EmailForm data={data} />
        <Form.Group controlId="motto">
          <Form.Label>{t('motto')}</Form.Label>
          <Form.Control readOnly type="plaintext" defaultValue={data.motto} />
        </Form.Group>
        <Form.Row>
          {data.lastSuccessfulLogin
          && (
            <Form.Group as={Col} controlId="lastSuccLogin">
              <Form.Label>{t('last_successful_login')}</Form.Label>
              <Form.Control readOnly type="plaintext" defaultValue={data.lastSuccessfulLogin} />
            </Form.Group>
          )}
          {data.lastUnsuccessfulLogin
          && (
            <Form.Group as={Col} controlId="lastFailLogin">
              <Form.Label>{t('last_unsuccessful_login')}</Form.Label>
              <Form.Control
                readOnly
                type="plaintext"
                defaultValue={data.lastUnsuccessfulLogin}
              />
            </Form.Group>
          )}
        </Form.Row>
        <hr className="mt-2 mb-3" />
        <Form.Row>
          <Form.Group as={Col} controlId="admin">
            <Badge variant={getBadgeVariant(data.accessLevelList.includes('ADMIN'))}>
              {getBadge(data.accessLevelList.includes('ADMIN'))}
            </Badge>
            {' '}
            {t('is_admin')}
          </Form.Group>
          <Form.Group as={Col} controlId="employee">
            <Badge
              variant={getBadgeVariant(data.accessLevelList.includes('EMPLOYEE'))}
            >
              {getBadge(data.accessLevelList.includes('EMPLOYEE'))}
            </Badge>
            {' '}
            {t('is_employee')}
          </Form.Group>
          <Form.Group as={Col} controlId="candidate">
            <Badge
              variant={getBadgeVariant(data.accessLevelList.includes('CANDIDATE'))}
            >
              {getBadge(data.accessLevelList.includes('CANDIDATE'))}
            </Badge>
            {' '}
            {t('is_candidate')}
          </Form.Group>
        </Form.Row>
        <hr className="mt-2 mb-3" />
        <Form.Row>
          <Form.Group as={Col} controlId="activated">
            <Badge variant={getBadgeVariant(data.active)}>{getBadge(data.active)}</Badge>
            {' '}
            {t('is_activated')}
          </Form.Group>
          <Form.Group as={Col} controlId="confirmed">
            <Badge variant={getBadgeVariant(data.confirmed)}>{getBadge(data.confirmed)}</Badge>
            {' '}
            {t('is_confirmed')}
          </Form.Group>
          <Form.Group as={Col} controlId="changePass">
            <Badge
              variant={getBadgeVariant(data.passwordChangeRequired)}
            >
              {getBadge(data.passwordChangeRequired)}
            </Badge>
            {' '}
            {t('is_change_pass')}
          </Form.Group>
        </Form.Row>
        <hr className="mt-2 mb-3" />
        {match === undefined ? (
          <Link to={ROUTES.EDIT_MY_ACCOUNT}>
            <Button
              variant="primary"
              block
            >
              {t('edit')}
            </Button>
          </Link>
        )
          : (
            <Link to={`${ROUTES.ACCOUNTS}/edit/${match.params.email}`}>
              <Button
                variant="primary"
                block
              >
                {t('edit')}
              </Button>
            </Link>
          )}
      </Form>
    </div>
  );
};

export default ShowAccountDetails;
