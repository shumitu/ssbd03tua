import {useDispatch, useSelector} from 'react-redux';
import React, {useEffect} from 'react';
import {useTranslation} from 'react-i18next';
import {buildBreadcrumb, changeTitle} from '../../redux/actions/actions';
import {ApplicationsOwnListBreadcrumb, HomeBreadcrumb,} from '../../../resources/AppConstants';
import {RESET_OWN_APPLICATION_LIST_ERROR,} from '../../redux/actions/types';
import {getData, getHeader} from '../../List/TableUtils';
import ProgressBar from '../../misc/ProgressBar';
import {fetchOwnApplicationsList} from '../../redux/actions/Application/ListOwnApplications';
import ApplicationOwnTable from '../../List/ApplicationsOwnTable';

const OwnApplicationsList = () => {
  const dispatch = useDispatch();
  const applicationList = useSelector((state) => state.applicationsOwnList);
  const {t} = useTranslation(['list_own_applications', 'application_category', 'common', 'error']);

  useEffect(() => {
    dispatch(buildBreadcrumb([HomeBreadcrumb, ApplicationsOwnListBreadcrumb]));
    dispatch(changeTitle(t('page_title')));
    dispatch(fetchOwnApplicationsList());
    dispatch({ type: RESET_OWN_APPLICATION_LIST_ERROR });
  }, [dispatch, t]);

  if (applicationList.isLoading) {
    return <ProgressBar />;
  }
  return (
    <div style={{ minHeight: '100%', marginTop: '1rem' }}>
      <ApplicationOwnTable
        data={getData(applicationList.data)}
        columns={getHeader([t('id'), t('creationDate'), t('destination'), t('startTime'), t('endTime'), t('categoryName')], applicationList.data)}
      />
    </div>
  );
};

export default OwnApplicationsList;
