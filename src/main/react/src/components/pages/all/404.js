import React, {useEffect} from 'react';
import {useDispatch} from 'react-redux';
import SentimentVeryDissatisfiedIcon from '@material-ui/icons/SentimentVeryDissatisfied';
import Row from 'react-bootstrap/Row';
import {useTranslation} from 'react-i18next';
import {buildBreadcrumb, changeTitle} from '../../redux/actions/actions';
import {HomeBreadcrumb, Page404Breadcrumb} from '../../../resources/AppConstants';

const Page404 = () => {
  const dispatch = useDispatch();
  const { t } = useTranslation('404');

  useEffect(() => {
    dispatch(buildBreadcrumb([HomeBreadcrumb, Page404Breadcrumb]));
    dispatch(changeTitle(t('page_title')));
  }, [dispatch, t]);

  return (
    <div className="basic-form">
      <Row style={{ justifyContent: 'center' }}>
        <h1>{t('content')}</h1>
      </Row>
      <Row style={{ justifyContent: 'center' }}>
        <SentimentVeryDissatisfiedIcon style={{ fontSize: '5rem' }} />
      </Row>
    </div>
  );
};

export default Page404;
