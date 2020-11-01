import React from 'react';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import {connect} from 'react-redux';
import flowRight from 'lodash/fp/flowRight';
import {withTranslation} from 'react-i18next';
import {format, parseISO} from 'date-fns';
import {
  buildBreadcrumb,
  changeTitle,
  setCredentials,
  switchAccessLevel,
} from '../../redux/actions/actions';
import GenericAlert from '../../misc/GenericAlert';
import MokAPI from '../../API/MokAPI';
import {AfterLoginBreadcrumb, HomeBreadcrumb} from '../../../resources/AppConstants';

const mapDispatchToProps = {
  changeTitle,
  setCredentials,
  switchAccessLevel,
  buildBreadcrumb,
};

class Login extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      lastSuccessfulLogin: '',
      lastUnsuccessfulLogin: '',
      timestampsError: null,
    };
  }

  componentDidMount() {
    const { t } = this.props;
    this.props.buildBreadcrumb([HomeBreadcrumb, AfterLoginBreadcrumb]);
    this.props.changeTitle(t('after_login:page_title'));
    this.getTimestamps(this);
  }

  async getTimestamps(that) {
    const { t } = this.props;
    try {
      const timestamps = await MokAPI.getLoginTimestamps();
      that.setState({
        lastSuccessfulLogin: timestamps.lastSuccessfulLogin === undefined
          ? t('after_login:no_successful_timestamp')
          : format(parseISO(timestamps.lastSuccessfulLogin), 'yyyy-MM-dd, HH:mm:ss'),
        lastUnsuccessfulLogin: timestamps.lastUnsuccessfulLogin === undefined
          ? t('after_login:no_unsuccessful_timestamp')
          : format(parseISO(timestamps.lastUnsuccessfulLogin), 'yyyy-MM-dd, HH:mm:ss'),
      });
    } catch (e) {
      this.setState({ timestampsError: e });
    }
  }

  render() {
    const { t } = this.props;
    return (
      <div className="details-form">
        <Form>
          <Form.Row>
            <Form.Group as={Col} controlId="lastSuccLogin">
              <Form.Label>{t('last_successful_login')}</Form.Label>
              <Form.Control readOnly type="plaintext" defaultValue={this.state.lastSuccessfulLogin} />
            </Form.Group>
            <Form.Group as={Col} controlId="lastFailLogin">
              <Form.Label>{t('last_unsuccessful_login')}</Form.Label>
              <Form.Control readOnly type="plaintext" defaultValue={this.state.lastUnsuccessfulLogin} />
            </Form.Group>
          </Form.Row>
        </Form>
        {
                    !this.state.timestampsError ? null
                      : (
                        <Row>
                          <Col md="auto" style={{ width: '100%' }}>
                            <GenericAlert message={t(this.state.timestampsError.message)} />
                          </Col>
                        </Row>
                      )
                }
      </div>
    );
  }
}

export default flowRight(
  withTranslation(['after_login', 'error']),
  connect(null, mapDispatchToProps),
)(Login);
