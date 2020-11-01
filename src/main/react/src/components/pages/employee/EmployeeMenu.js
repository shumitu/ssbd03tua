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

const EmployeeMenu = () => {
  const { t } = useTranslation(['menu', 'common']);
  const dispatch = useDispatch();

  return (
    <Row
      className="top-menu"
      style={{ background: 'linear-gradient(90deg, rgba(241,68,131,0.8) 0%, rgba(210,43,111,0.5) 100%)' }}
    >
      <div className="d-flex">
        <Col className="align-center-col col-md-auto">
          <Link
            to={ROUTES.ALL_STARSHIPS}
          >
            <MenuButton>{t('list_starships')}</MenuButton>
          </Link>
        </Col>
        <Col className="align-center-col col-md-auto">
          <Link to={ROUTES.ADD_STARSHIP}><MenuButton>{t('add_starship')}</MenuButton></Link>
        </Col>
        <Col className="align-center-col col-md-auto">
          <Link
            to={ROUTES.ALL_OFFERS}
          >
            <MenuButton>{t('offers')}</MenuButton>
          </Link>
        </Col>
        <Col className="align-center-col col-md-auto">
          <Link to={ROUTES.ADD_OFFER}><MenuButton>{t('add_offer')}</MenuButton></Link>
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

export default EmployeeMenu;
