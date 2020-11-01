import React from 'react';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import {Link} from 'react-router-dom';
import {useDispatch} from 'react-redux';
import {useTranslation} from 'react-i18next';
import CredentialsDisplay from '../../misc/CredentialsDisplay';
import {MenuButton} from '../../misc/MuiButtons';
import {ROUTES} from '../../../resources/AppConstants';
import handleLogout from '../../utils/HandleLogout';

const CandidateMenu = () => {
  const { t } = useTranslation(['menu', 'common']);
  const dispatch = useDispatch();

  return (
    <Row
      className="top-menu"
      style={{ background: 'linear-gradient(99deg, rgba(40,15,75,0.5) 0%, rgba(254,140,82,0.8) 66%)' }}
    >
      <div className="d-flex">
        <Col className="align-center-col col-md-auto"><Link to={ROUTES.ACTIVE_OFFERS}><MenuButton>{t('offers')}</MenuButton></Link></Col>
        <Col className="align-center-col col-md-auto">
          <Link
            to={ROUTES.OPERATIONAL_STARSHIPS}
          >
            <MenuButton>{t('list_starships')}</MenuButton>
          </Link>
        </Col>
        <Col className="align-center-col col-md-auto">
          <Link
            to={ROUTES.APPLICATIONS_OWN_LIST}
          >
            <MenuButton>{t('list_own_applications')}</MenuButton>
          </Link>
        </Col>
      </div>
      <div className="d-flex">
        <Col md="auto">
          <CredentialsDisplay />
        </Col>
        <Col className="align-center-col col-md-auto">
          <Link
            to={ROUTES.CHANGE_PASSWORD}
          >
            <MenuButton>{t('menu:changePassword')}</MenuButton>
          </Link>
        </Col>
        <Col className="align-center-col col-md-auto">
          <Link
            to={ROUTES.MY_ACCOUNT}
          >
            <MenuButton>{t('ownAccountDetails')}</MenuButton>
          </Link>
        </Col>
        <Col className="align-center-col col-md-auto">
          <MenuButton
            onClick={() => handleLogout(dispatch)}
          >
            {t('common:logout')}
          </MenuButton>
        </Col>
      </div>
    </Row>
  );
};

export default CandidateMenu;
