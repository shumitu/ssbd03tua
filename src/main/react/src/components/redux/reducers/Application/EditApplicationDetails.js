import {
    API_END,
    API_ERROR,
    API_START,
    FETCH_GET_APPLICATION_DETAILS,
    SET_EDIT_APPLICATION_DETAILS,
} from "../../actions/types";


const init_state = {
    data: {
        etag: "",
        application_id: "",
        weight: "",
        examination_code: "",
        motivational_letter: ""
    },
    isLoading: true,
    error: null
};


export const editApplicationDetailsReducer = (state = init_state, action) => {
    switch (action.type) {
        case SET_EDIT_APPLICATION_DETAILS:
            return {
                ...state,
                data: action.payload
            };
        case API_START:
            if (action.payload === FETCH_GET_APPLICATION_DETAILS) {
                return {
                    ...state,
                    isLoading: true
                };
            } else return state;
        case API_END:
            if (action.payload === FETCH_GET_APPLICATION_DETAILS) {
                return {
                    ...state,
                    isLoading: false
                };
            } else return state;
        case API_ERROR:
            if (action.payload.label === FETCH_GET_APPLICATION_DETAILS) {
                return {
                    ...state,
                    error: action.payload
                };
            } else return state;
        default:
            return state;
    }
};
