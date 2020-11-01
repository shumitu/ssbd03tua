import {useDispatch, useSelector} from 'react-redux';
import React, {useEffect} from 'react';
import {useTranslation} from 'react-i18next';
import {buildBreadcrumb, changeTitle} from '../../redux/actions/actions';
import {
    AllOffersListBreadcrumb,
    ApplicationListBreadcrumb,
    HomeBreadcrumb,
    OfferDetailsBreadcrumb,
} from '../../../resources/AppConstants';
import {RESET_APPLICATION_LIST_ERROR} from '../../redux/actions/types';
import {fetchApplicationsList} from '../../redux/actions/Application/ListApplications';
import {getData, getHeader} from '../../List/TableUtils';
import ApplicationTable from '../../List/ApplicationsTable';
import ProgressBar from '../../misc/ProgressBar';

const OfferApplicationsList = ({ match }) => {
  const dispatch = useDispatch();
  const applicationList = useSelector((state) => state.applicationsList);
  const {t} = useTranslation(['list_applications', 'application_category', 'common', 'error']);

  useEffect(() => {
    const bc = ApplicationListBreadcrumb(match.params.offerId);
      dispatch(buildBreadcrumb([HomeBreadcrumb, AllOffersListBreadcrumb, OfferDetailsBreadcrumb(match.params.offerId), bc]));
    dispatch(changeTitle(t('page_title')));
    dispatch({ type: RESET_APPLICATION_LIST_ERROR });
    dispatch(fetchApplicationsList(match.params.offerId));
  }, [dispatch, t]);

  if (applicationList.isLoading) {
    return <ProgressBar />;
  }
  return (
    <div style={{ minHeight: '100%', marginTop: '1rem' }}>
      <ApplicationTable
        data={getData(applicationList.data)}
        columns={getHeader([t('id'), t('firstName'), t('lastName'), t('weight'), t('creationDate'), t('categoryName')], applicationList.data)}
        offerId={match.params.offerId}
      />
    </div>
  );
};

export default OfferApplicationsList;
