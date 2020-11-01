import React, {useEffect} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {useTranslation} from 'react-i18next';
import Form from 'react-bootstrap/Form';
import {MDBDataTable} from 'mdbreact';
import {buildBreadcrumb, changeTitle} from '../../redux/actions/actions';
import GenericAlert from '../../misc/GenericAlert';
import ProgressBar from '../../misc/ProgressBar';
import {fetchAccountReportsList} from '../../redux/actions/Account/ListReports';
import {AccountReportsBreadcrumb, HomeBreadcrumb} from '../../../resources/AppConstants';

const ListAccountsReports = () => {
  const { t } = useTranslation(['list_accounts_reports', 'common']);
  const dispatch = useDispatch();
  const accountListReports = useSelector((state) => state.accountReportsList);
  const columns = [
    {
      label: t('account_email'),
      field: 'email',
      width: 300,
    },
    {
      label: t('last_successful'),
      field: 'lastSuccessfulLogin',
      width: 300,
    },
    {
      label: t('last_unsuccessful'),
      field: 'lastUnsuccessfulLogin',
      width: 300,
    },
    {
      label: t('account_ip'),
      field: 'ipAddress',
      width: 300,
    },
  ];

  useEffect(() => {
    dispatch(fetchAccountReportsList());
    dispatch(buildBreadcrumb([HomeBreadcrumb, AccountReportsBreadcrumb]));
    dispatch(changeTitle(t('page_title')));
  }, [dispatch, t]);

  if (accountListReports.isLoading) {
    return <ProgressBar />;
  } if (accountListReports.error !== null) {
    return <GenericAlert message={accountListReports.error.message} />;
  }
  const output = { columns, rows: accountListReports.data };
  return (
    <div className="report-form">
      <Form.Label><h2>{t('table_label')}</h2></Form.Label>
      <MDBDataTable
        scrollY
        striped
        hover
        responsiveXl
        scrollX={false}
        paging={false}
        data={output}
        noBottomColumns
        searching={false}
        maxHeight="30rem"
      />
    </div>

  );
};

export default ListAccountsReports;
