import React, {useEffect} from 'react';
import {useDispatch} from 'react-redux';
import {useTranslation} from 'react-i18next';
import {buildBreadcrumb, changeTitle} from '../redux/actions/actions';
import {HomeBreadcrumb} from '../../resources/AppConstants';

const HomePage = () => {
  const dispatch = useDispatch();
  const { t } = useTranslation('breadcrumbs');

  useEffect(() => {
    dispatch(buildBreadcrumb([HomeBreadcrumb]));
    dispatch(changeTitle(t('home')));
  }, [dispatch, t]);

  return (
    <>
      <p />
    </>
  );
};

export default HomePage;
