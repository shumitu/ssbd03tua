import {FETCH_STARSHIP_EMPLOYEE_DETAILS, FETCH_STARSHIP_GUEST_DETAILS, SET_STARSHIP_DETAILS} from "../types";
import {apiAction} from "../api";

export const fetchStarshipGuestDetails = (id) => apiAction({
    uri: `/starships/details`,
    method: "POST",
    data: {id},
    onSuccess: setStarshipDetails,
    label: FETCH_STARSHIP_GUEST_DETAILS
});

export const fetchStarshipEmployeeDetails = (id) => apiAction({
    uri: `/starships/employee/details`,
    method: "POST",
    data: {id},
    onSuccess: setStarshipDetails,
    label: FETCH_STARSHIP_EMPLOYEE_DETAILS
});

export const setStarshipDetails = (data) => {
    return {
        type: SET_STARSHIP_DETAILS,
        payload: {
            etag: data.etag,
            name: data.name,
            maximumWeight: data.maximumWeight,
            crewCapacity: data.crewCapacity,
            fuelCapacity: data.fuelCapacity,
            maximumSpeed: data.maximumSpeed,
            yearOfManufacture: data.yearOfManufacture,
            operational: "operational" in data ? data.operational : null
        }
    };
};
