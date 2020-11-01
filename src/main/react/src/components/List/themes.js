import {Checkbox, TextField} from '@material-ui/core';
import withStyles from '@material-ui/core/styles/withStyles';

const checkBoxStyles = (theme) => ({
  root: {
    '&$checked': {
      color: '#3D70B2',
    },
  },
  checked: {},
});

const filterTextBoxStyles = (theme) => ({
  root: {
    '& > *': {
      margin: theme.spacing(1),
      width: '25ch',
      height: '2.5rem',
    },
  },
});

export const StyledCheckbox = withStyles(checkBoxStyles)(Checkbox);
export const FilterTextField = withStyles(filterTextBoxStyles)(TextField);
