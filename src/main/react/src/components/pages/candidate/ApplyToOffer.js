import React, {useEffect, useState} from 'react';
import Col from 'react-bootstrap/Col';
import {ButtonWithConfirmDialog} from "../../misc/BootstrapButtons";
import {useTranslation} from "react-i18next";
import {buildBreadcrumb, changeTitle} from "../../redux/actions/actions";
import {useDispatch, useSelector} from "react-redux";
import ProgressBar from "../../misc/ProgressBar";
import FormControl from "../../misc/FormControls/FormControl";
import {equalTo, mottoRegex, notEqualTo} from "../../utils/Validators";
import PopUp from "../../misc/Snackbars";
import Row from "react-bootstrap/Row";
import GenericAlert from "../../misc/GenericAlert";
import Form from "react-bootstrap/Form";
import {Formik} from 'formik';
import {GenericRefreshButton} from "../../List/TableButtons";
import ApplicationAPI from "../../API/ApplicationAPI";
import {
    ActiveOffersListBreadcrumb,
    ApplyToOfferBreadcrumb,
    HomeBreadcrumb,
} from "../../../resources/AppConstants";
import {
    fetchOfferGuestDetails
} from "../../redux/actions/Offer/OfferDetails";

const EditApplicationDetails = ({match}) => {

    const {t} = useTranslation(['apply_to_offer', 'error']);
    const dispatch = useDispatch();
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(false);
    const [isSubmitting, setIsSubmitting] = useState(false);

    const isFetching = useSelector(state => state.offerDetails.isLoading);
    const fetchError = useSelector(state => state.offerDetails.error);
    const offerDetails = useSelector(state => state.offerDetails.data)

    useEffect(() => {
        setSuccess(false);
        dispatch(changeTitle(t('page_title')));

        const applyToOfferBreadcrumb = ApplyToOfferBreadcrumb(match.params.offerId);
        dispatch(buildBreadcrumb([HomeBreadcrumb, ActiveOffersListBreadcrumb, applyToOfferBreadcrumb]));
        dispatch(fetchOfferGuestDetails(match.params.offerId));
    }, [dispatch, match, t]);

    const refresh = () => {
        dispatch(fetchOfferGuestDetails(match.params.offerId));
        setSuccess(false);
        setError(null);
    };

    const handleSubmit = (values, {resetForm}) => {
        setIsSubmitting(true);
        ApplicationAPI.createApplication(
            values.weight,
            values.examination_code,
            values.motivational_letter,
            match.params.offerId,
            offerDetails.etag
        ).then(() => {
                resetForm({});
                setError(null);
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

    if (isFetching || isSubmitting) return <ProgressBar/>;
    else {
        return (
            <div className="details-form" style={{ maxWidth: '50vw' }}>
                <Row style={{ justifyContent: 'center', marginBottom: '2vh' }}>
                    <Col md="auto" style={{ fontSize: '5vh' }}>
                        {offerDetails.destination}
                    </Col>
                </Row>
                <div className="offer-detail-separator" />
                <Row style={{ marginBottom: '1vh', justifyContent: 'center' }}>
                    <Col md="auto">
                        {t('departure')}
                        {': '}
                        {offerDetails.flightStartTime}
                    </Col>
                    <Col md="auto">
                        {t('return')}
                        {': '}
                        {offerDetails.flightEndTime}
                    </Col>
                </Row>
                <div className="offer-detail-separator" />
                <Row style={{ marginBottom: '1vh', justifyContent: 'center' }}>
                    <Col md="auto">
                        {offerDetails.description}
                    </Col>
                </Row>
                <Row style={{ marginBottom: '1vh', justifyContent: 'center' }}>
                    <Col md="auto">
                        {t('price')}
                        {': '}
                        {offerDetails.price}
                        {' PLN'}
                    </Col>
                </Row>
                <div className="offer-detail-separator" />
                
                
                
                {success && <PopUp text={t('success')}/>}
                <Formik
                    validationSchema={validationSchema}
                    enableReinitialize={true}
                    onSubmit={handleSubmit}
                    initialValues={{
                        weight: 0,
                        examination_code: '',
                        motivational_letter: ''
                    }}
                >
                    {({values, errors, handleChange, handleSubmit}) => (
                        <Form onSubmit={handleSubmit}>
                            <Form.Row>
                                <Form.Group controlId="weight" as={Col} style={{ marginTop: '2vh' }}>
                                    <FormControl label={t('weight') + ' [kg]'}
                                                 value={values.weight}
                                                 isInvalid={errors.weight}
                                                 required={true}
                                                 onChange={handleChange}
                                                 error={errors.weight}/>
                                </Form.Group>
                            </Form.Row>
                            <Form.Row>
                                <Form.Group controlId="examination_code" as={Col}>
                                    <FormControl label={t('examination_code')}
                                                 value={values.examination_code}
                                                 isInvalid={errors.examination_code}
                                                 required={true}
                                                 onChange={handleChange}
                                                 error={errors.examination_code}/>
                                </Form.Group>
                            </Form.Row>
                            <Form.Row>
                                <Form.Group controlId="motivational_letter" as={Col}>
                                    <FormControl label={t('motivational_letter')}
                                                 as="textarea"
                                                 rows="5"
                                                 value={values.motivational_letter}
                                                 isInvalid={errors.motivational_letter}
                                                 required={true}
                                                 onChange={handleChange}
                                                 error={errors.motivational_letter}/>
                                </Form.Group>
                            </Form.Row>
                            <Col md="auto" style={{display:'contents'}}>
                                <ButtonWithConfirmDialog onSubmit={handleSubmit}
                                                         disabled={Object.keys(errors).length !== 0}>
                                    {t('send_application')}
                                </ButtonWithConfirmDialog>
                            </Col>
                        </Form>
                    )}</Formik>
                {
                    error &&
                    <Row style={{marginTop: '0.5rem', justifyContent:'center'}}>
                        <Col md="auto" style={{display:'contents'}}>
                            <GenericRefreshButton onClick={refresh}/>
                        </Col>
                        <Col md="auto" className="column-center" style={{width: "100%"}}>
                            <GenericAlert message={t(error.message)}/>
                        </Col>
                    </Row>
                }
            </div>
        )
    }
};

export default EditApplicationDetails
