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
import {defaultColumnWithFilter, filterTypesHelper, isOneItemSelected, makeCheckBox,} from './TableUtils';
import {PaginationRow, TableBody} from './TableRender';

import {GenericDetailsButton, GenericEditButton, GenericRefreshButton} from './TableButtons';
import GenericAlert from '../misc/GenericAlert';
import PopUp from '../misc/Snackbars';
import {
    fetchAllStarshipList,
    fetchOperationalStarshipList,
    setOperational,
} from '../redux/actions/Starship/ListStarships';
import {RESET_STARSHIP_LIST_ERROR} from '../redux/actions/types';
import {AUTH_ROLE} from '../../resources/AppConstants';

function StarshipsTable({columns, data}) {
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
        state: {pageIndex, pageSize, selectedRowIds},
    } = useTable({
            data, columns, defaultColumn, filterTypes, initialState: {pageIndex: 0, pageSize: 10},
        },
        useFilters, useRowSelect, usePagination,
        (hooks) => {
            hooks.visibleColumns.push(makeCheckBox);
        });

    const {t} = useTranslation(['list_starships', 'common']);

    const error = useSelector((state) => state.starshipList.error);
    const accessLevel = useSelector((state) => state.currentAccessLevel);
    const [success, setSuccess] = React.useState(false);

    const changeStarshipStatus = (status, data) => {
        dispatch({type: RESET_STARSHIP_LIST_ERROR});
        dispatch(setOperational(data[0].col0, status));
    };

    const getOperationalButton = (selectedRowIds, selectedFlatRows) => {
        const data = selectedFlatRows.map((d) => d.original);
        const isOneSelected = isOneItemSelected(selectedRowIds);
        if ((data[0] !== undefined) && (isOneSelected && data[0].col4 === t('common:No'))) {
            return (
                <Button
                    variant="success"
                    disabled={!isOneSelected}
                    onClick={() => changeStarshipStatus(true, data)}
                >
                    <CheckIcon/>
                    {' '}
                    {t('activate')}
                </Button>
            );
        }
        return (
            <Button
                variant="danger"
                disabled={!isOneSelected}
                onClick={() => changeStarshipStatus(false, data)}
            >
                <BlockIcon/>
                {' '}
                {t('deactivate')}
            </Button>

        );
    };

    React.useEffect(() => {
        setSuccess(false);
    }, []);

    const history = useHistory();

    const getDetailsURI = () => {
        const data = selectedFlatRows.map((d) => d.original.col0);
        return `/starships/details/${data[0]}`;
    };

    const getEditURI = () => {
        const data = selectedFlatRows.map((d) => d.original.col0);
        return `/starships/edit/${data[0]}`;
    };
    if (accessLevel === AUTH_ROLE.EMPLOYEE) {
        return (

            <div className="full-height-container bg-main" style={{marginBottom: '3rem'}}>
                {success && <PopUp text={t('success')}/>}
                {error && <GenericAlert message={t(error.data.message)}/>}
                <Row
                    className="align-items-center justify-content-start"
                    style={{justifySelf: 'start', paddingTop: '0.5rem', paddingBottom: '0.5rem'}}
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
                        {getOperationalButton(selectedRowIds, selectedFlatRows)}
                    </Col>
                    <Col className="align-center-col col-md-auto">
                        <GenericRefreshButton onClick={() => dispatch(fetchAllStarshipList())}/>
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
    if (accessLevel === AUTH_ROLE.ADMIN) {
        return (

            <div className="full-height-container bg-main" style={{marginBottom: '3rem'}}>
                {success && <PopUp text={t('success')}/>}
                {error && <GenericAlert message={t(error.data.message)}/>}
                <Row
                    className="align-items-center justify-content-start"
                    style={{justifySelf: 'start', paddingTop: '0.5rem', paddingBottom: '0.5rem'}}
                >
                    <Col className="align-center-col col-md-auto">
                        <GenericDetailsButton
                            disabled={!isOneItemSelected(selectedRowIds)}
                            onClick={() => history.push(getDetailsURI())}
                        />
                    </Col>
                    <Col className="align-center-col col-md-auto">
                        <GenericRefreshButton onClick={() => dispatch(fetchAllStarshipList())}/>
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

        <div className="full-height-container bg-main" style={{marginBottom: '3rem'}}>
            {success && <PopUp text={t('success')}/>}
            {error && <GenericAlert message={t(error.data.message)}/>}
            <Row
                className="align-items-center justify-content-start"
                style={{justifySelf: 'start', paddingTop: '0.5rem', paddingBottom: '0.5rem'}}
            >
                <Col className="align-center-col col-md-auto">
                    <GenericDetailsButton
                        disabled={!isOneItemSelected(selectedRowIds)}
                        onClick={() => history.push(getDetailsURI())}
                    />
                </Col>
                <Col className="align-center-col col-md-auto">
                    <GenericRefreshButton onClick={() => dispatch(fetchOperationalStarshipList())}/>
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

export default StarshipsTable;
