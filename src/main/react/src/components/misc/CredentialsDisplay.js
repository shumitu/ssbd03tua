import React from 'react';
import {useDispatch, useSelector} from 'react-redux';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import {useTranslation} from 'react-i18next';
import {switchAccessLevel} from '../redux/actions/actions';
import UserAvatar from './UserAvatar';
import history from './history';
import MokAPI from '../API/MokAPI';
import {AUTH_ROLE} from '../../resources/AppConstants';

const CredentialsDisplay = () => {
  const accessLevel = useSelector((state) => state.currentAccessLevel);
  const user = useSelector((state) => state.auth.claims);
  const { t } = useTranslation('common');
  const dispatch = useDispatch();

  const changeAccessLevel = (accessLevel) => {
    history.push('/');
    dispatch(switchAccessLevel(accessLevel));
    MokAPI.changeAccessLevel(accessLevel);
  };

  return (
    <div className="d-flex">
      <UserAvatar />
      <div className="nav-bar-credentials">
        <div>
          {t('user', { name: user.sub })}
        </div>
        <Select
          className="credentials-select"
          onChange={(event) => changeAccessLevel(event.target.value)}
          value={accessLevel}
        >
          {/* eslint-disable-next-line array-callback-return */}
          {user.auth.map((accessLevel) => {
            if (accessLevel === AUTH_ROLE.ADMIN) {
              return (
                <MenuItem
                  key="admin"
                  className="menu-item"
                  style={{ backgroundColor: 'red', color: 'black' }}
                  value={accessLevel}
                >
                  {t(Object.keys(AUTH_ROLE)[3])}
                </MenuItem>
              );
            }
            if (accessLevel === AUTH_ROLE.EMPLOYEE) {
              return (
                <MenuItem
                  key="manager"
                  className="menu-item"
                  style={{ backgroundColor: 'blue', color: 'black' }}
                  value={accessLevel}
                >
                  {t(Object.keys(AUTH_ROLE)[2])}
                </MenuItem>
              );
            }
            if (accessLevel === AUTH_ROLE.CANDIDATE) {
              return (
                <MenuItem
                  key="candidate"
                  className="menu-item"
                  style={{ backgroundColor: 'yellow', color: 'black' }}
                  value={accessLevel}
                >
                  {t(Object.keys(AUTH_ROLE)[1])}
                </MenuItem>
              );
            }
          })}
        </Select>
      </div>
    </div>
  );
};

export default CredentialsDisplay;
