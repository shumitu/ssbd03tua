import React from 'react';
import {DefaultColumnFilter, getDataRow, IndeterminateCheckbox} from './TableHelper';

export const filterTypesHelper = () => ({
  text: (rows, id, filterValue) => rows.filter((row) => {
    const rowValue = row.values[id];
    return rowValue !== undefined
      ? String(rowValue)
        .toLowerCase()
        .startsWith(String(filterValue).toLowerCase())
      : true;
  }),
});

export const defaultColumnWithFilter = () => ({
  Filter: DefaultColumnFilter,
});

export const makeCheckBox = (columns) => [
  {
    id: 'selection',
    Header: ({ getToggleAllRowsSelectedProps }) => (
      <div>
        <IndeterminateCheckbox {...getToggleAllRowsSelectedProps()} />
      </div>
    ),
    Cell: ({ row }) => (
      <div className="d-flex" style={{ justifyContent: 'center' }}>
        <IndeterminateCheckbox {...row.getToggleRowSelectedProps()} />
      </div>
    ),
  },
  ...columns,
];

export const getHeader = (header, data) => {
  if (header && Array.isArray(header) && header.length > 0) {
    return header.map((key, index) => ({
      Header: key,
      accessor: `col${index.toString()}`,
    }));
  }

  const keys = Object.keys(data[0]);
  return keys.map((key, index) => ({
    Header: key,
    accessor: `col${index.toString()}`,
  }));
};

export const getData = (data) => {
  const d = data;
  return d.map((row) => {
    const keys = Object.keys(d[0]);
    return getDataRow(row, keys);
  });
};

export const isOneItemSelected = (selectedRowIds) => Object.keys(selectedRowIds).length === 1;

export const isNothingSelected = (selectedRowIds) => Object.keys(selectedRowIds).length === 0;
