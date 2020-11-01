import {useDispatch, useSelector} from "react-redux";
import React from "react";
import {defaultColumnWithFilter, filterTypesHelper, isOneItemSelected, makeCheckBox} from "./TableUtils";
import {useFilters, usePagination, useRowSelect, useTable} from "react-table";
import {useTranslation} from "react-i18next";
import {useHistory} from "react-router";
import PopUp from "../misc/Snackbars";
import GenericAlert from "../misc/GenericAlert";
import {PaginationRow, TableBody} from "./TableRender";
import Col from "react-bootstrap/Col";
import {GenericDetailsButton, GenericEditButton, GenericRefreshButton} from "./TableButtons";
import Row from "react-bootstrap/Row";
import {cancelApplication, fetchOwnApplicationsList} from "../redux/actions/Application/ListOwnApplications";
import CheckIcon from "@material-ui/icons/Check";
import {ButtonWithConfirmDialog} from "../misc/BootstrapButtons";
import {RESET_OWN_APPLICATION_LIST_ERROR} from "../redux/actions/types";


function ApplicationOwnTable({columns, data}) {

    const dispatch = useDispatch();

    const filterTypes = React.useMemo(
        filterTypesHelper,
        []
    );
    const defaultColumn = React.useMemo(
        defaultColumnWithFilter,
        []
    );
    const {
        getTableProps, getTableBodyProps, headerGroups, rows, prepareRow, selectedFlatRows, page,
        canPreviousPage, canNextPage, pageOptions, pageCount, gotoPage, nextPage, previousPage, setPageSize,
        state: {pageIndex, pageSize, selectedRowIds}
    } = useTable({data, columns, defaultColumn, filterTypes, initialState: {pageIndex: 0, pageSize: 10},},
        useFilters, useRowSelect, usePagination,
        hooks => {
            hooks.visibleColumns.push(makeCheckBox)
        }
    );
    const {t} = useTranslation(["list_own_applications", "common"]);

    const error = useSelector(state => state.applicationsOwnList.error);
    const [success, setSuccess] = React.useState(false);

    const history = useHistory();

    const refresh = () => {
        dispatch({type: RESET_OWN_APPLICATION_LIST_ERROR});
        dispatch(fetchOwnApplicationsList());
    }

    const getDetailsURI = () => {
        let data = selectedFlatRows.map(d => d.original['col0']);
        return `/getMyApplication/${data[0]}`
    };

    const getEditURI = () => {
        let data = selectedFlatRows.map(d => d.original['col0']);
        return `/applications/edit/${data[0]}`
    };

    const getCancelButton = (selectedRowIds, selectedFlatRows) => {
        let data = selectedFlatRows.map(d => d.original['col0']);
        let isOneSelected = isOneItemSelected(selectedRowIds);

        return (
            <ButtonWithConfirmDialog disabled={!isOneSelected}
                                     onSubmit={() => dispatch(cancelApplication(data[0]))}>
                <CheckIcon/>{' '}{t('cancel')}
            </ButtonWithConfirmDialog>
        )
    };

    return (
        <div className="full-height-container bg-main small-table" style={{marginBottom: '3rem'}}>
            {success && <PopUp text={t('success')}/>}
            {error && <GenericAlert message={t(error.data.message)}/>}
            <Row className="align-items-center justify-content-start"
                 style={{justifySelf: "start", paddingTop: '0.5rem', paddingBottom: '0.5rem'}}>
                <Col className="align-center-col col-md-auto">
                    <GenericRefreshButton onClick={refresh}/>
                </Col>
                <Col className="align-center-col col-md-auto">
                    <GenericDetailsButton
                        disabled={!isOneItemSelected(selectedRowIds)}
                        onClick={() => history.push(getDetailsURI())}
                    />
                </Col>
                <Col className="align-center-col col-md-auto">
                    <GenericEditButton
                        disabled={!isOneItemSelected(selectedRowIds)}
                        onClick={() => history.push(getEditURI())}/>
                </Col>
                <Col className="align-center-col col-md-auto">
                    {getCancelButton(selectedRowIds, selectedFlatRows)}
                </Col>
            </Row>
            <TableBody classname={'small'} getTableBodyProps={getTableBodyProps} getTableProps={getTableProps}
                       headerGroups={headerGroups} page={page} prepareRow={prepareRow}/>
            <PaginationRow gotoPage={gotoPage} canNextPage={canNextPage} canPreviousPage={canPreviousPage}
                           nextPage={nextPage} pageCount={pageCount} pageIndex={pageIndex}
                           pageOptions={pageOptions} pageSize={pageSize} previousPage={previousPage}
                           setPageSize={setPageSize}/>
        </div>
    )

}

export default ApplicationOwnTable