import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import React from 'react';
import {useTranslation} from 'react-i18next';

export const PaginationRow = (
  {
    gotoPage, canPreviousPage, previousPage, nextPage, canNextPage,
    pageCount, pageIndex, pageOptions, pageSize, setPageSize,
  },
) => {
  const { t } = useTranslation('table');
  return (
    <Row
      className="align-items-center justify-content-start"
      style={{ justifySelf: 'start', paddingTop: '0.5rem', paddingBottom: '0.5rem' }}
    >
      <Col md="auto">
        <Button onClick={() => gotoPage(0)} disabled={!canPreviousPage}>
          {'<<'}
        </Button>
        {' '}
        <Button onClick={() => previousPage()} disabled={!canPreviousPage}>
          {'<'}
        </Button>
        {' '}
        <Button onClick={() => nextPage()} disabled={!canNextPage}>
          {'>'}
        </Button>
        {' '}
        <Button onClick={() => gotoPage(pageCount - 1)} disabled={!canNextPage}>
          {'>>'}
        </Button>
        {' '}
      </Col>
      <Col md="auto">
        {t('page')}
        {' '}
        <strong>
          {pageIndex + 1}
          {' '}
          {t('of')}
          {' '}
          {pageOptions.length}
        </strong>
        {' '}
      </Col>
      <Col md="auto">
        <Row className="align-items-center" noGutters>
          <Col style={{ marginRight: '0.5rem' }}>{t('goToPage')}</Col>
          <Col>
            <input
              className="form-control"
              style={{ width: 'auto' }}
              type="number"
              defaultValue={pageIndex + 1}
              onChange={(e) => {
                const page = e.target.value ? Number(e.target.value) - 1 : 0;
                gotoPage(page);
              }}
            />
          </Col>
        </Row>
      </Col>
      {' '}
      <Col md="auto">
        <select
          className="custom-select mr-sm-2"
          style={{ width: 'auto' }}
          value={pageSize}
          onChange={(e) => {
            setPageSize(Number(e.target.value));
          }}
        >
          {[10, 20, 30, 40, 50].map((pageSize) => (
            <option key={pageSize} value={pageSize}>
              {t('show', { pageSize })}
            </option>
          ))}
        </select>
      </Col>
    </Row>
  );
};

export const TableBody = ({
  getTableProps, headerGroups, getTableBodyProps, page, prepareRow,
}) => (
  <table className="table bg-table table-sm table-bordered" {...getTableProps()}>
    <thead>
      {headerGroups.map((headerGroup) => (
        <tr {...headerGroup.getHeaderGroupProps()}>
          {headerGroup.headers.map((column) => (
            <th {...column.getHeaderProps()}>
              <div className="d-flex" style={{ justifyContent: 'center', flexDirection: 'column' }}>
                <div style={{ textAlign: 'center' }}>
                        {column.render('Header')}
                      </div>
                <div className="d-flex" style={{ justifyContent: 'center' }}>
                        {column.canFilter ? column.render('Filter') : null}
                      </div>
              </div>
            </th>
          ))}
        </tr>
      ))}
    </thead>
    <tbody {...getTableBodyProps()}>
      {page.map((row, i) => {
        prepareRow(row);
        return (
          <tr {...row.getRowProps()}>
            {row.cells.map((cell) => <td {...cell.getCellProps()}>{cell.render('Cell')}</td>)}
          </tr>
        );
      })}
    </tbody>
  </table>
);
