import AddIcon from '@material-ui/icons/Add';
import DeleteForeverIcon from '@material-ui/icons/DeleteForever';
import EditIcon from '@material-ui/icons/Edit';
import React from 'react';
import SearchIcon from '@material-ui/icons/Search';
import RefreshIcon from '@material-ui/icons/Refresh';
import Button from 'react-bootstrap/Button';

export const GenericAddButton = ({ disabled = false, onClick = null }) => (
  <Button variant="success" disabled={disabled} onClick={onClick}><AddIcon /></Button>
);

export const GenericDeleteButton = ({ disabled = false, onClick = null }) => (
  <Button variant="danger" disabled={disabled} onClick={onClick}><DeleteForeverIcon /></Button>
);

export const GenericEditButton = ({ disabled = false, onClick = null }) => (
  <Button variant="warning" disabled={disabled} onClick={onClick}><EditIcon /></Button>
);

export const GenericTableButton = ({ disabled = false, onClick = null, children }) => (
  <Button variant="primary" disabled={disabled} onClick={onClick}>{children}</Button>
);

export const GenericDetailsButton = ({ disabled = false, onClick = null }) => (
  <Button variant="info" disabled={disabled} onClick={onClick}><SearchIcon /></Button>
);

export const GenericRefreshButton = ({ disabled = false, onClick = null }) => (
  <Button variant="info" disabled={disabled} onClick={onClick}><RefreshIcon /></Button>
);
