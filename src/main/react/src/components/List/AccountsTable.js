import React from 'react';
import {useFilters, usePagination, useRowSelect, useTable,} from 'react-table';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import BlockIcon from '@material-ui/icons/Block';
import CheckIcon from '@material-ui/icons/Check';
import {useTranslation} from 'react-i18next';
import {useHistory} from 'react-router';
import {useDispatch, useSelector} from 'react-redux';
import Spinner from 'react-bootstrap/Spinner';
import {
  defaultColumnWithFilter,
  filterTypesHelper,
  isOneItemSelected,
  makeCheckBox,
} from './TableUtils';
import {PaginationRow, TableBody} from './TableRender';

import {
  GenericDetailsButton,
  GenericEditButton,
  GenericRefreshButton,
  GenericTableButton,
} from './TableButtons';
import GenericAlert from '../misc/GenericAlert';
import PopUp from '../misc/Snackbars';
import {fetchAccountListFiltered, setActive} from '../redux/actions/Account/ListAccounts';
import ManageAccessLevels from '../pages/admin/ManageAccessLevels';
import i18n from '../../i18n';
import MokAPI from '../API/MokAPI';
import {RESET_ACCOUNT_LIST_ERROR} from '../redux/actions/types';

function AccountsTable({ columns, data, filterPhrase }) {
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

  const error = useSelector((state) => state.accountList.error);
  const [success, setSuccess] = React.useState(false);
  const [sendLinkError, setSendLinkError] = React.useState(false);
  const [sendLinkSubmitting, setSendLinkSubmitting] = React.useState(false);

  const changeAccountStatus = (status, data) => {
    dispatch({ type: RESET_ACCOUNT_LIST_ERROR });
    dispatch(setActive(data[0].col0, status));
  };

  const getActivationButton = (selectedRowIds, selectedFlatRows) => {
    const data = selectedFlatRows.map((d) => d.original);
    const isOneSelected = isOneItemSelected(selectedRowIds);
    if ((data[0] !== undefined) && (isOneSelected && data[0].col1 === t('common:No'))) {
      return (
        <Button
          variant="success"
          disabled={!isOneSelected}
          onClick={() => changeAccountStatus(true, data)}
        >
          <CheckIcon />
          {' '}
          {t('activate')}
        </Button>
      );
    }
    return (
      <Button
        variant="danger"
        disabled={!isOneSelected}
        onClick={() => changeAccountStatus(false, data)}
      >
        <BlockIcon />
        {' '}
        {t('deactivate')}
      </Button>

    );
  };

  const getConfirmationButton = (selectedRowIds, selectedFlatRows) => {
    if (sendLinkSubmitting) {
      return (
        <Button variant="success" disabled>
          <Spinner
            as="span"
            animation="border"
            size="sm"
            role="status"
            aria-hidden="true"
          />
          {' '}
          {t('sending')}
        </Button>
      );
    }
    const data = selectedFlatRows.map((d) => d.original);
    const isOneSelected = isOneItemSelected(selectedRowIds);
    if ((data[0] !== undefined) && (isOneSelected && data[0].col2 === t('common:No'))) {
      return (
        <Button
          variant="success"
          disabled={!isOneSelected}
          onClick={() => confirmAccount(data[0].col0, i18n.language)}
        >
          <CheckIcon />
          {' '}
          {t('send_confirmation')}
        </Button>
      );
    }
    return (
      <Button variant="danger" disabled>
        <BlockIcon />
        {' '}
        {t('already_confirmed')}
      </Button>

    );
  };

  const confirmAccount = async (email, locale) => {
    try {
      setSendLinkSubmitting(true);
      await MokAPI.sendConfirmationLink(email, locale);
      setSuccess(true);
    } catch (e) {
      setSuccess(false);
      setSendLinkError(e);
    } finally {
      setSendLinkSubmitting(false);
    }
  };

  React.useEffect(() => {
    setSuccess(false);
    setSendLinkError(null);
  }, []);

  const history = useHistory();

  const getDetailsURI = () => {
    const data = selectedFlatRows.map((d) => d.original.col0);
    return `/accounts/${data[0]}`;
  };

  const getEditURI = () => {
    const data = selectedFlatRows.map((d) => d.original.col0);
    return `/accounts/edit/${data[0]}`;
  };

  const changeUsersPassword = () => {
    const data = selectedFlatRows.map((d) => d.original.col0);
    return `/change_users_password/${data[0]}`;
  };

  return (
    <div className="full-height-container bg-main" style={{ marginBottom: '3rem', width: '100%' }}>
      {success && <PopUp text={t('success')} />}
      {sendLinkError && <PopUp severity="error" text={t(sendLinkError.message)} autoHideDuration={75000} />}
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
          <GenericEditButton
            disabled={!isOneItemSelected(selectedRowIds)}
            onClick={() => history.push(getEditURI())}
          />
        </Col>
        <Col className="align-center-col col-md-auto">
          {getActivationButton(selectedRowIds, selectedFlatRows)}
        </Col>
        <Col className="align-center-col col-md-auto">
          {getConfirmationButton(selectedRowIds, selectedFlatRows)}
        </Col>
        <Col className="align-center-col col-md-auto">
          <GenericTableButton
            to={changeUsersPassword}
            disabled={!isOneItemSelected(selectedRowIds)}
            onClick={() => history.push(changeUsersPassword())}
          >
            {t('change_password')}
          </GenericTableButton>
        </Col>
        <Col className="align-center-col col-md-auto">
          <ManageAccessLevels disabled={!isOneItemSelected(selectedRowIds)} email={selectedFlatRows.map((d) => d.original.col0)[0]} accessLevels={selectedFlatRows.map((d) => d.original.col4)[0]} />
        </Col>
        <Col className="align-center-col col-md-auto">
          <GenericRefreshButton onClick={() => dispatch(fetchAccountListFiltered(filterPhrase))} />
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

export default AccountsTable;
