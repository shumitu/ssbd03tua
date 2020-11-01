import Form from 'react-bootstrap/Form';
import React from 'react';
import {noneStyle} from './FormControl';

const FormControlSelect = (
  {
    label,
    required = false,
    value = '',
    onChange,
    children,
  },
) => (
  <>
    <Form.Label>
      {label}
      {required && ' *'}
    </Form.Label>
    <Form.Control
      value={value}
      as="select"
      onChange={onChange}
      required={required}
    >
      {children}
    </Form.Control>
    <p style={noneStyle}>Error</p>
  </>
);

export default FormControlSelect;
