import React from 'react';
import {useFilters, usePagination, useRowSelect, useTable,} from 'react-table';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import CheckIcon from '@material-ui/icons/Check';
import {AUTH_ROLE} from '../../resources/AppConstants';
import {useTranslation} from 'react-i18next';
import {useHistory} from 'react-router';
import {useDispatch, useSelector} from 'react-redux';
import {
  defaultColumnWithFilter,
  filterTypesHelper,
  isOneItemSelected,
  makeCheckBox,
} from './TableUtils';
import {PaginationRow, TableBody} from './TableRender';

import {GenericDetailsButton, GenericRefreshButton} from './TableButtons';
import GenericAlert from '../misc/GenericAlert';
import PopUp from '../misc/Snackbars';
import {fetchActiveOffersList} from '../redux/actions/Offer/ListActiveOffers';

function ActiveOffersTable({ columns, data }) {
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
      canPreviousPage, canNextPage, pageOptions, pageCount, gotoPage, nextPage, previousPage,
      setPageSize,
      state: { pageIndex, pageSize, selectedRowIds },
    } = useTable({
      data, columns, defaultColumn, filterTypes, initialState: { pageIndex: 0, pageSize: 10 },
    },
    useFilters, useRowSelect, usePagination,
    (hooks) => {
      hooks.visibleColumns.push(makeCheckBox);
    });
    const { t } = useTranslation(['list_active_offers', 'common']);

    const error = useSelector((state) => state.activeOffersList.error);
    const [success, setSuccess] = React.useState(false);

    React.useEffect(() => {
      setSuccess(false);
    }, []);

    const history = useHistory();    
    
    const applicationAllowed = isOneItemSelected(selectedRowIds) && 
          (selectedFlatRows.map((d) => d.original.col2)[0] === 'Yes' || 
           selectedFlatRows.map((d) => d.original.col2)[0] === 'Tak');

    const getDetailsURI = () => {
      const data = selectedFlatRows.map((d) => d.original.col0);
      return `/offers/${data[0]}`;
    };

    const getApplyURI = () => {
      const data = selectedFlatRows.map((d) => d.original.col0);
      return `/applications/applyToOffer/${data[0]}`;
    };
  
    const getApplyButton = () => {
        if (accessLevel === AUTH_ROLE.CANDIDATE) {
            return (
                <Button
                variant="success"
                disabled={ !applicationAllowed }
                onClick={ () => { history.push(getApplyURI()); } }
                >
                    <CheckIcon />
                    {' '}
                    {t('apply')}
                </Button>
            )
        }
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
                    <GenericRefreshButton onClick={() => dispatch(fetchActiveOffersList())} />
                </Col>
                <Col className="align-center-col col-md-auto">
                    { getApplyButton() }
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

export default ActiveOffersTable;
