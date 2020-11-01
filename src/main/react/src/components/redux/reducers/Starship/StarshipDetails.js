import {
    API_END,
    API_ERROR,
    API_START,
    FETCH_STARSHIP_EMPLOYEE_DETAILS,
    FETCH_STARSHIP_GUEST_DETAILS,
    RESET_STARSHIP_DETAILS_ERROR,
    SET_STARSHIP_DETAILS
} from "../../actions/types";

const init_state = {
    data:
        {
            etag: "",
            name: "",
            maximumWeight: 0,
            crewCapacity: 0,
            fuelCapacity: 0,
            maximumSpeed: 0,
            yearOfManufacture: 0,
            operational: false
        },
    isLoading: true,
    error: null
};

export const starshipDetailsReducer = (state = init_state, action) => {
    switch (action.type) {
        case RESET_STARSHIP_DETAILS_ERROR:
            return {
                ...state,
                error: null
            };
        case SET_STARSHIP_DETAILS:
            return {
                ...state,
                data: action.payload
            };
        case API_START:
            if (action.payload === FETCH_STARSHIP_GUEST_DETAILS || action.payload === FETCH_STARSHIP_EMPLOYEE_DETAILS) {
                return {
                    ...state,
                    isLoading: true
                };
            } else return state;
        case API_END:
            if (action.payload === FETCH_STARSHIP_GUEST_DETAILS || action.payload === FETCH_STARSHIP_EMPLOYEE_DETAILS) {
                return {
                    ...state,
                    isLoading: false
                };
            } else return state;
        case API_ERROR:
            if (action.payload === FETCH_STARSHIP_GUEST_DETAILS || action.payload === FETCH_STARSHIP_EMPLOYEE_DETAILS) {
                return {
                    ...state,
                    error: action.payload
                };
            } else return state;
        default:
            return state;
    }
};
