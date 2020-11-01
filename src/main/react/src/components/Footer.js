import React, {Suspense} from 'react';
import Row from 'react-bootstrap/Row';
import Breadcrumb from './misc/Breadcrumb';

const Footer = () => (
  <footer className="footer">
    <Row style={{ justifyContent: 'space-between', width: '100%' }}>
      <Suspense fallback={null}><Breadcrumb /></Suspense>
      <div className="footer-text">
        ©
        {new Date().getFullYear()}
        {' '}
        SSDB03
      </div>
    </Row>
  </footer>
);

export default Footer;
