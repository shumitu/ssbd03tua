import React, {useState} from 'react';
import Button from 'react-bootstrap/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import {useTranslation} from 'react-i18next';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import {makeStyles} from '@material-ui/core/styles';
import {useDispatch} from 'react-redux';
import GenericAlert from '../../misc/GenericAlert';
import MokAPI from '../../API/MokAPI';
import {GenericTableButton} from '../../List/TableButtons';
import {fetchAccountListFiltered} from '../../redux/actions/Account/ListAccounts';
import {AUTH_ROLE} from '../../../resources/AppConstants';

const useStyles = makeStyles((theme) => ({
  formControl: {
    margin: theme.spacing(1),
    minWidth: '16rem',
  },
  selectEmpty: {
    marginTop: theme.spacing(2),
  },
}));

const ManageAccessLevels = ({ disabled = false, email = '', accessLevels = '' }) => {
  const [open, setOpen] = React.useState(false);
  const { t } = useTranslation(['manage_access_levels', 'common', 'error']);
  const handleClickOpen = () => {
    setOpen(true);
  };

  const dispatch = useDispatch();
  const classes = useStyles();
  const handleClose = () => {
    setOpen(false);
    setError(null);
  };

  const [error, setError] = useState(null);
  const [accessLevel, setAccessLevel] = React.useState('');

  const handleChange = (event) => {
    setAccessLevel(event.target.value);
  };

  const canSubmit = () => !(accessLevel === '');

  const handleSubmitGrant = async () => {
    if (canSubmit()) {
      try {
        await MokAPI.grantAccessLevel(email, accessLevel);
        setOpen(false);
        dispatch(fetchAccountListFiltered());
      } catch (e) {
        setError(e);
      }
    }
  };

  const handleSubmitRevoke = async () => {
    if (canSubmit()) {
      try {
        await MokAPI.revokeAccessLevel(email, accessLevel);
        setOpen(false);
        dispatch(fetchAccountListFiltered());
      } catch (e) {
        setError(e);
      }
    }
  };

  return (
    <>
      <GenericTableButton onClick={handleClickOpen} disabled={disabled}>
        {t('button')}
      </GenericTableButton>
      <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
        <DialogTitle>{email}</DialogTitle>
        <DialogContent>
          <DialogContentText>
            {t('activeAccessLevels', { accessLevels })}
          </DialogContentText>
          <Row>
            <Col>
              <FormControl className={classes.formControl}>
                <InputLabel>{t('access_level')}</InputLabel>
                <Select onChange={handleChange} value={accessLevel}>
                  {Object.entries(AUTH_ROLE).slice(1).map(([key, value]) => (
                    <MenuItem
                      className="menu-item"
                      key={key}
                      value={value}
                    >
                      {t(`common:${key}`)}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Col>
          </Row>
          {
            error
            && (
              <Row>
                <Col md="auto" style={{ width: '100%' }}>
                  <GenericAlert message={t(error.message)} />
                </Col>
              </Row>
            )
          }
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} variant="warning">
            {t('cancel')}
          </Button>
          <Button onClick={handleSubmitRevoke} variant="danger" disabled={!canSubmit()}>
            {t('revoke')}
          </Button>
          <Button onClick={handleSubmitGrant} variant="success" disabled={!canSubmit()}>
            {t('grant')}
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default ManageAccessLevels;
