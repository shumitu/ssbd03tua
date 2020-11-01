import Form from 'react-bootstrap/Form';
import React from 'react';

export const errorMsgStyle = {
  fontWeight: 'bold', fontSize: '0.8rem', color: '#dc3545', margin: '0 0 0 0.2rem',
};
export const okStyle = {
  fontWeight: 'bold', fontSize: '0.8rem', color: '#28a745', margin: '0 0 0 0.2rem',
};
export const noneStyle = {
  fontWeight: 'bold', fontSize: '0.8rem', color: 'rgba(0,0,0,0)', margin: '0',
};

const FormControl = (
  {
    label,
    type = 'plaintext',
    isInvalid = false,
    error,
    required = false,
    placeholder = '',
    value = '',
    onChange,
  },
) => (
  <>
    <Form.Label>
      {label}
      {required && ' *'}
    </Form.Label>
    <Form.Control
      value={value}
      type={type}
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

export default FormControl;
