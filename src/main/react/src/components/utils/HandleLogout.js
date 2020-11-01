import store from '../redux/store';
import history from '../misc/history';
import {clearAuth, resetBreadcrumb, switchAccessLevel} from '../redux/actions/actions';
import {AUTH_ROLE} from '../../resources/AppConstants';

const handleLogout = (dispatch) => {
  store().persistor.purge().then(() => {
    history.push('/');
    dispatch(switchAccessLevel(AUTH_ROLE.GUEST));
    dispatch(clearAuth());
    dispatch(resetBreadcrumb());
  });
};

export default handleLogout;
