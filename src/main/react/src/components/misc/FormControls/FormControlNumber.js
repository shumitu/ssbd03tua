import Form from 'react-bootstrap/Form';
import React from 'react';
import {errorMsgStyle, noneStyle, okStyle} from './FormControl';

const FormControlNumber = (
  {
    label,
    isInvalid = false,
    error,
    required = false,
    placeholder = '',
    value = '',
    onChange,
    step = 'any',
    min, max,
  },
) => (
  <>
    <Form.Label>
      {label}
      {required && ' *'}
    </Form.Label>
    <Form.Control
      value={value}
      type="number"
      step={step}
      min={min}
      max={max}
      required={required}
      placeholder={placeholder}
      onChange={onChange}
      isInvalid={isInvalid || (value === '' && required)}
      isValid={!isInvalid}
    />
    {isInvalid ? <p style={errorMsgStyle}>{error}</p> : (value === '' && required)
      ? <p style={noneStyle}>Error</p> : <p style={okStyle}>Ok &#10003;</p>}
  </>
);

export default FormControlNumber;
