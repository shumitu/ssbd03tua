import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Avatar from '@material-ui/core/Avatar';
import {blue, red, yellow} from '@material-ui/core/colors';
import {useSelector} from 'react-redux';
import {AUTH_ROLE} from '../../resources/AppConstants';

const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
    '& > *': {
      margin: 0,
      marginRight: '1rem',
    },
  },
  yellow: {
    color: theme.palette.getContrastText(yellow[500]),
    backgroundColor: yellow[500],
  },
  blue: {
    color: theme.palette.getContrastText(blue[500]),
    backgroundColor: blue[500],
  },
  red: {
    color: theme.palette.getContrastText(red[500]),
    backgroundColor: red[500],
  },
}));

function getNumberBasedOfAccessLevel(accessLevel) {
  switch (accessLevel) {
    case AUTH_ROLE.CANDIDATE:
      return 0;
    case AUTH_ROLE.EMPLOYEE:
      return 1;
    case AUTH_ROLE.ADMIN:
      return 2;
    default:
      return 0;
  }
}

export const LetterAvatar = () => {
  const classes = useStyles();
  const letter = useSelector((state) => state.auth.claims.sub.substring(0, 1));
  const accessLevel = useSelector((state) => state.currentAccessLevel);
  const colors = [classes.yellow, classes.blue, classes.red];
  return (
    <div className={classes.root}>
      <Avatar className={colors[getNumberBasedOfAccessLevel(accessLevel)]}>{letter}</Avatar>
    </div>
  );
};

export default LetterAvatar;
