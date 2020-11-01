import React, {useEffect} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {useTranslation} from 'react-i18next';
import {buildBreadcrumb, changeTitle} from '../../redux/actions/actions';
import StarshipsTable from '../../List/StarshipsTable';
import {getData, getHeader} from '../../List/TableUtils';
import {fetchOperationalStarshipList} from '../../redux/actions/Starship/ListStarships';
import ProgressBar from '../../misc/ProgressBar';
import {HomeBreadcrumb, OperationalStarshipListBreadcrumb} from '../../../resources/AppConstants';
import {RESET_STARSHIP_LIST_ERROR} from '../../redux/actions/types';

const ListOperationalStarships = () => {
  const { t } = useTranslation(['list_starships', 'common', 'error']);
  const dispatch = useDispatch();
  const starshipList = useSelector((state) => state.starshipList);

  useEffect(() => {
    dispatch(buildBreadcrumb([HomeBreadcrumb, OperationalStarshipListBreadcrumb]));
    dispatch(changeTitle(t('page_title')));
    dispatch(fetchOperationalStarshipList());
    dispatch({ type: RESET_STARSHIP_LIST_ERROR });
  }, [dispatch, t]);

  if (starshipList.isLoading) {
    return <ProgressBar />;
  }
  return (
    <div style={{ minHeight: '100%', marginTop: '1rem' }}>
      <StarshipsTable
        data={getData(starshipList.data)}
        columns={getHeader([t('id'), t('name'), t('crew'), t('production_year'), t('operational')], starshipList.data)}
      />
    </div>
  );
};

export default ListOperationalStarships;
