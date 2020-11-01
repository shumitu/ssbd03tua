import React from 'react';
import Alert from '@material-ui/lab/Alert';

const GenericAlert = ({ message, severity = 'error' }) => (
  <Alert severity={severity} style={{ marginTop: '0.2rem', width: 'max-content' }}>{message}</Alert>
);

export default GenericAlert;
