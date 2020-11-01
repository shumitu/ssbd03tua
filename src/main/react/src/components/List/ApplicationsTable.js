import {useDispatch, useSelector} from 'react-redux';
import React from 'react';
import {useFilters, usePagination, useRowSelect, useTable,} from 'react-table';
import {useTranslation} from 'react-i18next';
import {useHistory} from 'react-router';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import {
  defaultColumnWithFilter,
  filterTypesHelper,
  isOneItemSelected,
  makeCheckBox,
} from './TableUtils';
import PopUp from '../misc/Snackbars';
import GenericAlert from '../misc/GenericAlert';
import {PaginationRow, TableBody} from './TableRender';
import {GenericDetailsButton, GenericRefreshButton} from './TableButtons';
import {fetchApplicationsList} from '../redux/actions/Application/ListApplications';
import {RESET_APPLICATION_LIST_ERROR} from '../redux/actions/types';

function ApplicationTable({ columns, data, offerId }) {
  const dispatch = useDispatch();

  const filterTypes = React.useMemo(
    filterTypesHelper,
    [],
  );
  const defaultColumn = React.useMemo(
    defaultColumnWithFilter,
    [],
  );
  const {
    getTableProps, getTableBodyProps, headerGroups, rows, prepareRow, selectedFlatRows, page,
    canPreviousPage, canNextPage, pageOptions, pageCount, gotoPage, nextPage, previousPage, setPageSize,
    state: { pageIndex, pageSize, selectedRowIds },
  } = useTable({
    data, columns, defaultColumn, filterTypes, initialState: { pageIndex: 0, pageSize: 10 },
  },
  useFilters, useRowSelect, usePagination,
  (hooks) => {
    hooks.visibleColumns.push(makeCheckBox);
  });
  const { t } = useTranslation(['list_accounts', 'common']);

  const error = useSelector((state) => state.applicationsList.error);
  const [success, setSuccess] = React.useState(false);

  const history = useHistory();

  const getDetailsURI = () => {
    const data = selectedFlatRows.map((d) => d.original.col0);
    return `/getApplication/${offerId}/${data[0]}`;
  };

  const refresh = () => {
    dispatch({ type: RESET_APPLICATION_LIST_ERROR });
    dispatch(fetchApplicationsList(offerId));
  };

  return (
    <div className="full-height-container bg-main small-table" style={{ marginBottom: '3rem' }}>
      {success && <PopUp text={t('success')} />}
      {error && <GenericAlert message={t(error.data.message)} />}
      <Row
        className="align-items-center justify-content-start"
        style={{ justifySelf: 'start', paddingTop: '0.5rem', paddingBottom: '0.5rem' }}
      >
        <Col className="align-center-col col-md-auto">
          <GenericDetailsButton
            disabled={!isOneItemSelected(selectedRowIds)}
            onClick={() => history.push(getDetailsURI())}
          />
        </Col>
        <Col className="align-center-col col-md-auto">
          <GenericRefreshButton onClick={refresh} />
        </Col>
      </Row>
      <TableBody
        getTableBodyProps={getTableBodyProps}
        getTableProps={getTableProps}
        headerGroups={headerGroups}
        page={page}
        prepareRow={prepareRow}
      />
      <PaginationRow
        gotoPage={gotoPage}
        canNextPage={canNextPage}
        canPreviousPage={canPreviousPage}
        nextPage={nextPage}
        pageCount={pageCount}
        pageIndex={pageIndex}
        pageOptions={pageOptions}
        pageSize={pageSize}
        previousPage={previousPage}
        setPageSize={setPageSize}
      />
    </div>
  );
}

export default ApplicationTable;
