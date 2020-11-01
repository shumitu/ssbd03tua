import React, {useEffect} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {useTranslation} from 'react-i18next';
import {buildBreadcrumb, changeTitle} from '../../redux/actions/actions';
import ActiveOffersTable from '../../List/ActiveOffersTable';
import {getData, getHeader} from '../../List/TableUtils';
import {fetchActiveOffersList} from '../../redux/actions/Offer/ListActiveOffers';
import ProgressBar from '../../misc/ProgressBar';
import {ActiveOffersListBreadcrumb, HomeBreadcrumb} from '../../../resources/AppConstants';
import {RESET_ACTIVE_OFFERS_LIST_ERROR} from '../../redux/actions/types';

const ListActiveOffers = () => {
  const { t } = useTranslation(['list_active_offers', 'common', 'error']);
  const dispatch = useDispatch();
  const activeOffersList = useSelector((state) => state.activeOffersList);

  useEffect(() => {
    dispatch(buildBreadcrumb([HomeBreadcrumb, ActiveOffersListBreadcrumb]));
    dispatch(changeTitle(t('page_title')));
    dispatch(fetchActiveOffersList());
    dispatch({ type: RESET_ACTIVE_OFFERS_LIST_ERROR });
  }, [dispatch, t]);

  if (activeOffersList.isLoading) {
    return <ProgressBar />;
  }
  return (
    <div style={{ minHeight: '100%', marginTop: '1rem' }}>
      <ActiveOffersTable
        data={getData(activeOffersList.data)}
        columns={getHeader([t('id'), t('price'), t('open'),
          t('destination')], activeOffersList.data)}
      />
    </div>
  );
};

export default ListActiveOffers;
