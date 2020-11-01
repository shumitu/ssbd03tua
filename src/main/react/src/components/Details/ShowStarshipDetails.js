import React, {useEffect} from "react";
import {buildBreadcrumb, changeTitle} from "../redux/actions/actions";
import {useDispatch, useSelector} from "react-redux";
import {useTranslation} from "react-i18next";
import {getData, getHeader} from "../List/TableUtils";
import {fetchAllStarshipList} from "../redux/actions/Starship/ListStarships";
import ProgressBar from "../misc/ProgressBar";
import {RESET_STARSHIP_LIST_ERROR, RESET_STARSHIP_DETAILS_ERROR} from "../redux/actions/types";
import { fetchStarshipEmployeeDetails, fetchStarshipGuestDetails } from "../redux/actions/Starship/StarshipDetails"
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row"
import Col from "react-bootstrap/Col"
import Badge from "react-bootstrap/Badge";
import GenericAlert from "../misc/GenericAlert";
import {
    StarshipDetailsBreadcrumb,
    AllStarshipListBreadcrumb,
    OperationalStarshipListBreadcrumb,
    HomeBreadcrumb
} from "../../resources/AppConstants";



const ShowStarshipDetails = ({match, employeeMode = false}) => {
    
    const {t} = useTranslation(['starship_details', 'error']);
    const dispatch = useDispatch();
    const error = useSelector(state => state.starshipDetails.error);
    const isLoading = useSelector(state => state.starshipDetails.isLoading);
    const data = useSelector(state => state.starshipDetails.data);

    useEffect(() => {
        dispatch(changeTitle(t('page_title')));
        dispatch({type: RESET_STARSHIP_DETAILS_ERROR});
        if (employeeMode) {
            dispatch(buildBreadcrumb([HomeBreadcrumb, AllStarshipListBreadcrumb, StarshipDetailsBreadcrumb(match.params.starshipId)]));
            if (match !== undefined) {
                dispatch(fetchStarshipEmployeeDetails(match.params.starshipId));
            }
        } else {
            dispatch(buildBreadcrumb([HomeBreadcrumb, OperationalStarshipListBreadcrumb, StarshipDetailsBreadcrumb(match.params.starshipId)]));
            if (match !== undefined) {
                dispatch(fetchStarshipGuestDetails(match.params.starshipId));
            }
        }
    }, [dispatch, match, t, employeeMode]);
    
    const getBadge = (val) => {
        if (val) return "✔";
        else return "✖"
    };
    
    const getBadgeVariant = (val) => {
        if (val) return "success";
        else return "danger"
    };
    
    const EmployeeDetails = () => {
        return <>
            <Form.Row>
                <Form.Group
                as={Col}
                style={{
                  display: 'flex',
                  flexDirection: 'row',
                  alignItems: 'center',
                  height: 'fit-content',
                }}
                >
                    <Form.Label>{t('operational')}</Form.Label>
                    <h2 style={{ marginLeft: '1rem' }}>
                      <Badge variant={getBadgeVariant(data.operational)}>
                        {getBadge(data.operational)}
                      </Badge>
                    </h2>
                </Form.Group>
            </Form.Row>
        </>
    };
    

    
    if (isLoading) {
        return <ProgressBar/>;
    } else if (error) {
        return  <div className="details-form" style={{maxWidth: '50vw'}}>
                    {error && <GenericAlert message={t(error.data.message)}/>}
                </div>;
    } else {
        return  <div className="details-form" style={{maxWidth: '50vw'}}>
                    <Row style={{justifyContent: 'center', marginBottom: '2vh'}}>
                        <Col md={"auto"} style={{fontSize: '3.5vh'}}>
                            {data.name}
                        </Col>
                    </Row>
                    <Form.Row>
                        <Form.Group as={Col}>
                            <Form.Label>{t('year_of_manufacture')}</Form.Label>
                            <Form.Control readOnly defaultValue={data.yearOfManufacture}/>
                        </Form.Group>
                        <Form.Group as={Col}>
                            <Form.Label>{t('crew_capacity')}</Form.Label>
                            <Form.Control readOnly defaultValue={data.crewCapacity}/>
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col}>
                            <Form.Label>{t('cargo_capacity') + ' [kg]'}</Form.Label>
                            <Form.Control readOnly defaultValue={data.maximumWeight}/>
                        </Form.Group>
                        <Form.Group as={Col}>
                            <Form.Label>{t('maximum_speed') + ' [km/h]'}</Form.Label>
                            <Form.Control readOnly defaultValue={data.maximumSpeed}/>
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col}>
                            <Form.Label>{t('fuel_capacity') + ' [l]'}</Form.Label>
                            <Form.Control readOnly defaultValue={data.fuelCapacity}/>
                        </Form.Group>
                        <Form.Group as={Col}>
                        </Form.Group>
                    </Form.Row>
                    {employeeMode && <EmployeeDetails/>}
                </div>
    }

};

export default ShowStarshipDetails
