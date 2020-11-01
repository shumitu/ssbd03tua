import {
  API_END,
  API_ERROR,
  API_START,
  FETCH_APPLICATION_DETAILS,
  FETCH_MY_APPLICATION_DETAILS,
  SET_APPLICATION_DETAILS,
} from '../../actions/types';


const init_state = {
    data: {
        weight: "",
        examination_code: "",
        motivational_letter: "",
        offer_id: "",
        offer_destination: "",
        candidate_first_name: "",
        candidate_last_name: "",
        category_name: ""
    },
    isLoading: true,
    error: null
};


export const applicationDetailsReducer = (state = init_state, action) => {
    switch (action.type) {
        case SET_APPLICATION_DETAILS:
            return {
                ...state,
                data: action.payload
            };
        case API_START:
            if (action.payload === FETCH_MY_APPLICATION_DETAILS || action.payload === FETCH_APPLICATION_DETAILS) {
                return {
                    ...state,
                    isLoading: true
                };
            } else return state;
        case API_END:
            if (action.payload === FETCH_MY_APPLICATION_DETAILS || action.payload === FETCH_APPLICATION_DETAILS) {
                return {
                    ...state,
                    isLoading: false
                };
            } else return state;
        case API_ERROR:
            if (action.payload.label === FETCH_MY_APPLICATION_DETAILS || action.payload.label === FETCH_APPLICATION_DETAILS) {
                return {
                    ...state,
                    error: action.payload
                };
            } else return state;
        default:
            return state;
    }
};
