import {Button} from '@material-ui/core';
import withStyles from '@material-ui/core/styles/withStyles';

export const MenuButton = withStyles({
  root: {
    background: 'linear-gradient(169deg, rgba(220,219,250,1) 0%, rgba(164,153,232,1) 100%)',
    '&:hover': {
      filter: 'brightness(90%)',
    },
    borderRadius: '0.2rem',
    border: 0,
    color: 'black',
    padding: '0.4rem',
    fontFamily: 'inherit',
  },
  label: {
    color: '#2f1658',
    textTransform: 'none',
    fontSize: '16px',
    fontWeight: '600',
  },
})(Button);
