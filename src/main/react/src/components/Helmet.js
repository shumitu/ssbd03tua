import Helmet from 'react-helmet';
import {connect} from 'react-redux';
import React from 'react';

function mapStateToProps(state) {
  return {
    title: state.siteTitle,
  };
}

class HelmetWrapper extends React.PureComponent {
  render() {
    return (
      <Helmet>
        <meta charSet="utf-8" />
        <title>{this.props.title}</title>
      </Helmet>
    );
  }
}

export default connect(mapStateToProps)(HelmetWrapper);
