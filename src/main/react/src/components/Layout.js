import React from 'react';
import PropTypes from 'prop-types';
import Container from 'react-bootstrap/Container';
import Footer from './Footer';

const Layout = ({ children }) => (
  <div>
    <main style={{ minHeight: '100%', alignItems: 'center', margin: 'auto' }}>
      <Container fluid className="full-height-container fit-width">
        {children}
      </Container>
    </main>
    <Footer />
  </div>
);

Layout.propTypes = {
  children: PropTypes.node.isRequired,
};

export default Layout;
