import React, {useEffect} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {useTranslation} from 'react-i18next';
import {buildBreadcrumb, changeTitle} from '../../redux/actions/actions';
import AllOffersTable from '../../List/AllOffersTable';
import {getData, getHeader} from '../../List/TableUtils';
import {fetchAllOffersList} from '../../redux/actions/Offer/ListAllOffers';
import ProgressBar from '../../misc/ProgressBar';
import {AllOffersListBreadcrumb, HomeBreadcrumb} from '../../../resources/AppConstants';
import {RESET_ALL_OFFERS_LIST_ERROR} from '../../redux/actions/types';

const ListAllOffers = () => {
  const { t } = useTranslation(['list_all_offers', 'common', 'error']);
  const dispatch = useDispatch();
  const allOffersList = useSelector((state) => state.allOffersList);

  useEffect(() => {
    dispatch(buildBreadcrumb([HomeBreadcrumb, AllOffersListBreadcrumb]));
    dispatch(changeTitle(t('page_title')));
    dispatch(fetchAllOffersList());
    dispatch({ type: RESET_ALL_OFFERS_LIST_ERROR });
  }, [dispatch, t]);

  if (allOffersList.isLoading) {
    return <ProgressBar />;
  }
  return (
    <div style={{ minHeight: '100%', marginTop: '1rem' }}>
      <AllOffersTable
        data={getData(allOffersList.data)}
        columns={getHeader([t('id'), t('price'), t('hidden'), t('open'),
          t('totalWeight'), t('destination')], allOffersList.data)}
      />
    </div>
  );
};

export default ListAllOffers;
