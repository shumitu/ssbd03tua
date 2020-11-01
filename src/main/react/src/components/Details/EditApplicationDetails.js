import React, {useEffect, useState} from 'react';
import Col from 'react-bootstrap/Col';
import {ButtonWithConfirmDialog} from "../misc/BootstrapButtons";
import {useTranslation} from "react-i18next";
import {buildBreadcrumb, changeTitle} from "../redux/actions/actions";
import {useDispatch, useSelector} from "react-redux";
import ProgressBar from "../misc/ProgressBar";
import FormControl from "../misc/FormControls/FormControl";
import {equalTo, mottoRegex, notEqualTo} from "../utils/Validators";
import PopUp from "../misc/Snackbars";
import Row from "react-bootstrap/Row";
import GenericAlert from "../misc/GenericAlert";
import Form from "react-bootstrap/Form";
import {Formik} from 'formik';
import {GenericRefreshButton} from "../List/TableButtons";
import ApplicationAPI from "../API/ApplicationAPI";
import {
    ApplicationsOwnListBreadcrumb,
    EditApplicationDetailsBreadcrumb,
    HomeBreadcrumb,
} from "../../resources/AppConstants";
import {
    fetchGetApplicationDetails
} from "../redux/actions/Application/EditApplicationDetails";

const EditApplicationDetails = ({match}) => {

    const {t} = useTranslation(['edit_application', 'error']);
    const dispatch = useDispatch();
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(false);
    const [isSubmitting, setIsSubmitting] = useState(false);

    const data = useSelector(state => state.editApplicationDetails.data);
    const isFetching = useSelector(state => state.editApplicationDetails.isLoading);
    const fetchError = useSelector(state => state.editApplicationDetails.error);

    useEffect(() => {
        setSuccess(false);
        dispatch(changeTitle(t('page_title')));

        const editApplicationDetailsBreadcrumb = EditApplicationDetailsBreadcrumb(match.params.applicationId);
        dispatch(buildBreadcrumb([HomeBreadcrumb, ApplicationsOwnListBreadcrumb, editApplicationDetailsBreadcrumb]));
        dispatch(fetchGetApplicationDetails(match.params.applicationId));
    }, [dispatch, match, t]);

    const refresh = () => {
        dispatch(fetchGetApplicationDetails(match.params.applicationId));
        setSuccess(false);
        setError(null);
    };

    const handleSubmit = (values, {resetForm}) => {
        setIsSubmitting(true);
        ApplicationAPI.updateApplicationDetails(
            data.etag, data.application_id, values.weight,
            values.examination_code, values.motivational_letter
        ).then(() => {
                resetForm({});
                setError(null);
                dispatch(fetchGetApplicationDetails(match.params.applicationId));
                setSuccess(true);
            }).catch(e => {
                setSuccess(false);
                setError(e);
            }).finally(() => setIsSubmitting(false));
    };

    let yup = require('yup');
    yup.addMethod(yup.string, 'equalTo', equalTo);
    yup.addMethod(yup.string, 'notEqualTo', notEqualTo);

    const validationSchema = yup.object().shape({
        weight: yup.number().typeError(t('error:application.incorrect_weight')).required(t('error:validation.required')).moreThan(0, t('error:validation.number.not_positive_value')),
        examination_code: yup.string().trim().required(t('error:validation.required')).matches(mottoRegex, t('error:application.incorrect_examination_code')).max(1024, t('error:application.examination_code_too_long')),
        motivational_letter: yup.string().trim().required(t('error:validation.required')).matches(mottoRegex, t('error:application.incorrect_motivational_letter'))

    });

    if (isSubmitting || isFetching) return <ProgressBar/>;
    else {
        return (
            <div className="details-form">
                {success && <PopUp text={t('success')}/>}
                <Formik
                    validationSchema={validationSchema}
                    enableReinitialize={true}
                    onSubmit={handleSubmit}
                    initialValues={{
                        application_id: match.params.applicationId,
                        weight: data.weight,
                        examination_code: data.examination_code,
                        motivational_letter: data.motivational_letter
                    }}
                >
                    {({values, errors, handleChange, handleSubmit}) => (
                        <Form onSubmit={handleSubmit}>
                            <Form.Row>
                                <Form.Group controlId="weight" as={Col}>
                                    <FormControl label={t('weight')} value={values.weight}
                                                 isInvalid={errors.weight} required={true}
                                                 onChange={handleChange} error={errors.weight}/>
                                </Form.Group>
                            </Form.Row>
                            <Form.Row>
                                <Form.Group controlId="examination_code" as={Col}>
                                    <FormControl label={t('examination_code')} value={values.examination_code}
                                                 isInvalid={errors.examination_code} required={true}
                                                 onChange={handleChange} error={errors.examination_code}/>
                                </Form.Group>
                            </Form.Row>
                            <Form.Row>
                                <Form.Group controlId="motivational_letter" as={Col}>
                                    <FormControl label={t('motivational_letter')} value={values.motivational_letter}
                                                 isInvalid={errors.motivational_letter} required={true}
                                                 onChange={handleChange} error={errors.motivational_letter}/>
                                </Form.Group>
                            </Form.Row>
                            <ButtonWithConfirmDialog onSubmit={handleSubmit}
                                                     disabled={Object.keys(errors).length !== 0}>
                                {t('save')}
                            </ButtonWithConfirmDialog>
                        </Form>
                    )}</Formik>
                {
                    error &&
                    <Row style={{marginTop: '0.5rem', justifyContent:'center'}}>
                        <Col md="auto" className="column-center" style={{width: "100%"}}>
                            <GenericAlert message={t(error.message)}/>
                        </Col>
                        <Col md="auto" style={{display:'contents'}}>
                            <GenericRefreshButton onClick={refresh}/>
                        </Col>
                    </Row>
                }
            </div>
        )
    }
};

export default EditApplicationDetails
