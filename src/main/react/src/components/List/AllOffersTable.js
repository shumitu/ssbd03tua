import React from 'react';
import {useFilters, usePagination, useRowSelect, useTable,} from 'react-table';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import BlockIcon from '@material-ui/icons/Block';
import CheckIcon from '@material-ui/icons/Check';
import DeleteIcon from '@material-ui/icons/Delete';
import LockIcon from '@material-ui/icons/Lock';
import LockOpenIcon from '@material-ui/icons/LockOpen';

import {useTranslation} from 'react-i18next';
import {useHistory} from 'react-router';
import {useDispatch, useSelector} from 'react-redux';
import GenericAlert from '../misc/GenericAlert';
import PopUp from '../misc/Snackbars';
import {PaginationRow, TableBody} from './TableRender';
import {
  defaultColumnWithFilter,
  filterTypesHelper,
  isOneItemSelected,
  makeCheckBox,
} from './TableUtils';
import {
  GenericDetailsButton,
  GenericEditButton,
  GenericRefreshButton,
  GenericTableButton
} from './TableButtons';
import {
  fetchAllOffersList,
  sendRemove,
  setClosed,
  setOpen,
  setVisible,
} from '../redux/actions/Offer/ListAllOffers';
import {RESET_ALL_OFFERS_LIST_ERROR} from '../redux/actions/types';
import {AUTH_ROLE} from '../../resources/AppConstants';
import AssignStarshipToOffer from '../pages/employee/AssignStarshipToOffer';

function AllOffersTable({ columns, data }) {
  const dispatch = useDispatch();
  const accessLevel = useSelector((state) => state.currentAccessLevel);
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
  const { t } = useTranslation(['list_all_offers', 'common']);

  const error = useSelector((state) => state.allOffersList.error);
  const [success, setSuccess] = React.useState(false);

  const changeOfferVisibility = (status, data) => {
    dispatch({ type: RESET_ALL_OFFERS_LIST_ERROR });
    dispatch(setVisible(data[0].col0, status));
  };

  const setOfferClosed = (data) => {
    dispatch({ type: RESET_ALL_OFFERS_LIST_ERROR });
    dispatch(setClosed(data[0].col0));
  };
  const removeOffer = (data) => {
    dispatch({ type: RESET_ALL_OFFERS_LIST_ERROR });
    dispatch(sendRemove(data[0].col0));
  };

  const refresh = () => {
    dispatch({ type: RESET_ALL_OFFERS_LIST_ERROR });
    dispatch(fetchAllOffersList());
  };

  const getShowButton = (selectedRowIds, selectedFlatRows) => {
    const data = selectedFlatRows.map((d) => d.original);
    const isOneSelected = isOneItemSelected(selectedRowIds);
    if ((data[0] !== undefined) && (isOneSelected && data[0].col2 === t('common:Yes'))) {
      return (
        <Button
          variant="success"
          disabled={!isOneSelected}
          onClick={() => changeOfferVisibility(true, data)}
        >
          <CheckIcon />
          {' '}
          {t('show')}
        </Button>
      );
    }
    return (
      <Button
        variant="danger"
        disabled={!isOneSelected}
        onClick={() => changeOfferVisibility(false, data)}
      >
        <BlockIcon />
        {' '}
        {t('hide')}
      </Button>

    );
  };

  const getCloseButton = (selectedRowIds, selectedFlatRows) => {
    const data = selectedFlatRows.map((d) => d.original);
    const isOneSelected = isOneItemSelected(selectedRowIds);
    if ((data[0] !== undefined) && (isOneSelected && data[0].col3 === t('common:Yes'))) {
      return (
        <Button
          variant="danger"
          disabled={!isOneSelected}
          onClick={() => setOfferClosed(data)}
        >
          <LockIcon />
          {' '}
          {t('close')}
        </Button>
      );
    }
    return (
      <Button
        variant="success"
        disabled={!isOneSelected}
        onClick={() => openOffer(data)}
      >
        <LockOpenIcon />
        {' '}
        {t('open')}
      </Button>

    );
  };

  const getRemoveButton = (selectedRowIds, selectedFlatRows) => {
    const data = selectedFlatRows.map((d) => d.original);
    const isOneSelected = isOneItemSelected(selectedRowIds);
    if ((data[0] !== undefined) && (isOneSelected && data[0].col2 === t('common:Yes')
            && data[0].col3 === t('common:No'))) {
      return (
        <Button
          variant="danger"
          disabled={!isOneSelected}
          onClick={() => removeOffer(data)}
        >
          <DeleteIcon />
          {' '}
          {t('remove')}
        </Button>
      );
    }
    return (
      <Button
        variant="danger"
        disabled
        onClick={() => removeOffer(data)}
      >
        <DeleteIcon />
        {' '}
        {t('remove')}
      </Button>
    );
  };

  React.useEffect(() => {
    setSuccess(false);
  }, []);

  const history = useHistory();

  const openOffer = (data) => {
    dispatch({ type: RESET_ALL_OFFERS_LIST_ERROR });
    dispatch(setOpen(data[0].col0));
  };

  const getDetailsURI = () => {
    const data = selectedFlatRows.map((d) => d.original.col0);
    return `/offers/${data[0]}`;
  };

  const getEditURI = () => {
    const data = selectedFlatRows.map((d) => d.original.col0);
    return `/offers/edit/${data[0]}`;
  };

  const getAssignURI = () => {
    const data = selectedFlatRows.map((d) => d.original.col0);
    return `/offers/assign/${data[0]}`;
  };

  if (accessLevel === AUTH_ROLE.EMPLOYEE) {
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
            <GenericEditButton
              disabled={!isOneItemSelected(selectedRowIds)}
              onClick={() => history.push(getEditURI())}
            />
          </Col>
          <Col className="align-center-col col-md-auto">
            {getRemoveButton(selectedRowIds, selectedFlatRows)}
          </Col>
          <Col className="align-center-col col-md-auto">
            {getShowButton(selectedRowIds, selectedFlatRows)}
          </Col>
          <Col className="align-center-col col-md-auto">
            {getCloseButton(selectedRowIds, selectedFlatRows)}
          </Col>
          <Col className="align-center-col col-md-auto">
            <GenericTableButton disabled={!isOneItemSelected(selectedRowIds)} onClick={() => history.push(getAssignURI())}>{t('assign_starship')}</GenericTableButton>
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

export default AllOffersTable;
