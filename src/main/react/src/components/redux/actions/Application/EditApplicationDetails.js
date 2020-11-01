import {
    SET_EDIT_APPLICATION_DETAILS,
    FETCH_GET_APPLICATION_DETAILS
} from "../types";
import {apiAction} from "../api";

export const fetchGetApplicationDetails = (id) => apiAction({
    uri: '/applications/getMyApplication',
    method: "POST",
    data: {id},
    onSuccess: setEditApplicationDetails,
    label: FETCH_GET_APPLICATION_DETAILS
});

export const setEditApplicationDetails = (data) => {
    console.log(data);

    return {
        type: SET_EDIT_APPLICATION_DETAILS,
        payload: {
            etag: data.etag,
            application_id: data.id,
            weight: data.weight,
            examination_code: data.examinationCode,
            motivational_letter: data.motivationalLetter
        }
    };
};
