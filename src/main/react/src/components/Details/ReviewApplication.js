import React, {useState} from "react";
import {useTranslation} from "react-i18next";
import {useDispatch, useSelector} from "react-redux";
import MokAPI from "../API/MokAPI";
import {fetchAccountListFiltered} from "../redux/actions/Account/ListAccounts";
import {GenericTableButton} from "../List/TableButtons";
import Dialog from "@material-ui/core/Dialog/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import FormControl from "@material-ui/core/FormControl";
import InputLabel from "@material-ui/core/InputLabel/InputLabel";
import Select from "@material-ui/core/Select/Select";
import {APPLICATION_CATEGORIES, AUTH_ROLE} from "../../resources/AppConstants";
import MenuItem from "@material-ui/core/MenuItem";
import GenericAlert from "../misc/GenericAlert";
import DialogActions from "@material-ui/core/DialogActions/DialogActions";
import Button from "react-bootstrap/Button";
import {makeStyles} from "@material-ui/core/styles";
import ApplicationAPI from "../API/ApplicationAPI";
import {fetchAllOffersList} from "../redux/actions/Offer/ListAllOffers";
import {fetchApplicationDetails} from "../redux/actions/Application/ApplicationDetails";
import {ButtonWithConfirmDialog} from "../misc/BootstrapButtons";
import ProgressBar from "../misc/ProgressBar";
import Spinner from "react-bootstrap/Spinner";

const useStyles = makeStyles((theme) => ({
    formControl: {
        margin: theme.spacing(1),
        minWidth: '16rem',
    },
    selectEmpty: {
        marginTop: theme.spacing(2),
    },
}));

const ReviewApplication = (props) => {
    const [open, setOpen] = React.useState(false);
    const {t} = useTranslation(['application_category', 'common', 'error']);
    const handleClickOpen = () => {
        setOpen(true);
    };

    const dispatch = useDispatch();
    const [loading, setLoading] = useState(false);
    const classes = useStyles();
    const handleClose = () => {
        setError(null);
        dispatch(fetchApplicationDetails(props.applicationId))
        setOpen(false);
    };

    const [error, setError] = useState(null);
    const [category, setCategory] = React.useState('');

    const handleChange = (event) => {
        setCategory(event.target.value);
    };

    const canSubmit = () => !(category === '');

    const handleSubmit = async () => {
        if (canSubmit()) {
            try {
                setLoading(true);
                await ApplicationAPI.addApplicationToCategory(props.applicationId, category, props.applicationEtag);
                dispatch(fetchApplicationDetails(props.applicationId))
                setOpen(false);

            } catch (e) {
                setError(e);
            } finally {
                setLoading(false);
            }
        }
    };

    const getSendButton = () => {
        if (loading)
            return (<ButtonWithConfirmDialog onSubmit={handleSubmit} variant="success" disabled={!canSubmit()}>
                <Spinner
                    as="span"
                    animation="border"
                    size="sm"
                    role="status"
                    aria-hidden="true"
                />
                {t('common:submit')}
            </ButtonWithConfirmDialog>)
        else
            return (
                <ButtonWithConfirmDialog onSubmit={handleSubmit} variant="success"
                                         disabled={!canSubmit()}>{t('common:submit')}</ButtonWithConfirmDialog>);
    }

    return (

        <>
            <GenericTableButton onClick={handleClickOpen}>
                {t('review')}
            </GenericTableButton>
            <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
                <DialogTitle>{t('add_to_category')}</DialogTitle>
                <DialogContent>
                    <Row>
                        <Col>
                            <FormControl className={classes.formControl}>
                                <InputLabel>{t('category_name')}</InputLabel>
                                <Select onChange={handleChange} value={category}>
                                    {Object.entries(APPLICATION_CATEGORIES).map(([key, value]) => (
                                        <MenuItem
                                            className="menu-item"
                                            key={key}
                                            value={value}>
                                            {t(`application_category:${key}`)}
                                        </MenuItem>
                                    ))}
                                </Select>
                            </FormControl>
                        </Col>
                    </Row>
                    {
                        error
                        && (
                            <Row>
                                <Col md="auto" style={{width: '100%'}}>
                                    <GenericAlert message={t(error.message)}/>
                                </Col>
                            </Row>
                        )
                    }
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} variant="warning">
                        {t('common:cancel')}
                    </Button>
                    {getSendButton()}
                </DialogActions>
            </Dialog>
        </>
    );
};

export default ReviewApplication;