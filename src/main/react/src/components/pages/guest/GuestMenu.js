import React from 'react';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import {Link} from 'react-router-dom';
import {useTranslation} from 'react-i18next';
import HelpIcon from '@material-ui/icons/Help';
import {MenuButton} from '../../misc/MuiButtons';
import {ROUTES} from '../../../resources/AppConstants';

const GuestMenu = () => {
  const { t } = useTranslation(['menu', 'common']);
  return (
    <Row className="top-menu" style={{ background: 'linear-gradient(90deg, rgba(110, 88, 231, 0.8) 0%, rgba(85, 61, 185, 0.5) 100%)' }}>
      <div className="d-flex">
        <Col className="align-center-col col-md-auto"><Link to={ROUTES.ACTIVE_OFFERS}><MenuButton>{t('offers')}</MenuButton></Link></Col>
        <Col className="align-center-col col-md-auto">
          <Link
            to={ROUTES.OPERATIONAL_STARSHIPS}
          >
            <MenuButton>{t('list_starships')}</MenuButton>
          </Link>
        </Col>
      </div>
      <div className="d-flex">
        <Col className="align-center-col col-md-auto"><Link to={ROUTES.LOGIN}><MenuButton>{t('common:login')}</MenuButton></Link></Col>
        <Col className="align-center-col col-md-auto"><Link to={ROUTES.CONTACT}><MenuButton><HelpIcon /></MenuButton></Link></Col>
      </div>
    </Row>
  );
};

export default GuestMenu;
