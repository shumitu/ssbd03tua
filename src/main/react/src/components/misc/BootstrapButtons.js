import React, {useState} from 'react';
import {useTranslation} from 'react-i18next';
import Button from 'react-bootstrap/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogActions from '@material-ui/core/DialogActions';

export const ButtonWithConfirmDialog = ({
  disabled = false, onSubmit = () => void 0, text = 'are_you_sure', canSubmit = true, children,
}) => {
  const [open, setOpen] = useState(false);
  const { t } = useTranslation('confirm_dialog');
  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = async () => {
    if (canSubmit) {
      onSubmit();
      setOpen(false);
    }
  };

  return (
    <>
      <Button variant="primary" onClick={handleClickOpen} disabled={disabled} block>
        {children}
      </Button>
      <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
        <DialogTitle>{t(text)}</DialogTitle>
        <DialogActions style={{ justifyContent: 'center', display: 'flex' }}>
          <Button onClick={handleClose} variant="danger" style={{ minWidth: '6rem', width: '6rem' }}>
            {t('no')}
          </Button>
          <Button onClick={handleSubmit} variant="success" type="submit" style={{ minWidth: '6rem', width: '6rem' }}>
            {t('yes')}
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};
