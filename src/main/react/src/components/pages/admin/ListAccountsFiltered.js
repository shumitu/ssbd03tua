import React, {useEffect} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {useTranslation} from 'react-i18next';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import {Formik} from 'formik';
import {buildBreadcrumb, changeTitle} from '../../redux/actions/actions';
import AccountsTable from '../../List/AccountsTable';
import {getData, getHeader} from '../../List/TableUtils';
import {fetchAccountListFiltered} from '../../redux/actions/Account/ListAccounts';
import ProgressBar from '../../misc/ProgressBar';
import {AccountListBreadcrumb, HomeBreadcrumb} from '../../../resources/AppConstants';
import {RESET_ACCOUNT_LIST_ERROR} from '../../redux/actions/types';
import FormControl from '../../misc/FormControls/FormControl';
import {lettersOnly, mottoRegex} from '../../utils/Validators';

const ListAccountsFiltered = () => {
  const [filterPhrase, setFilterPhrase] = React.useState('');
  const [submitted, setSubmitted] = React.useState(false);
  const handleSubmit = ({ filterPhrase }) => {
    setSubmitted(true);
    setFilterPhrase(filterPhrase);
    dispatch(fetchAccountListFiltered(filterPhrase));
  };

  const { t } = useTranslation(['list_accounts', 'common', 'error']);
  const dispatch = useDispatch();
  const accountList = useSelector((state) => state.accountList);

  useEffect(() => {
    dispatch(buildBreadcrumb([HomeBreadcrumb, AccountListBreadcrumb]));
    dispatch(changeTitle(t('page_title')));
    dispatch({ type: RESET_ACCOUNT_LIST_ERROR });
  }, [dispatch, t]);

  const yup = require('yup');
  const validationSchema = yup.object().shape({
    filterPhrase: yup.string().max(32, t('error:filterPhrase.too_long'))
      .matches(mottoRegex, t('error:filterPhrase.incorrect'))
      .matches(lettersOnly, t('error:filterPhrase.incorrect')),
  });

  if (!submitted) {
    return (
      <div className="basic-form">
        <Formik
          onSubmit={handleSubmit}
          initialValues={{ filterPhrase: '' }}
          validationSchema={validationSchema}
        >
          {({
            values, errors, handleChange, handleSubmit,
          }) => (
            <Form onSubmit={handleSubmit}>
              <Form.Group controlId="filterPhrase">
                <FormControl
                  label={t('phrase_input')}
                  value={values.filterPhrase}
                  isInvalid={errors.filterPhrase}
                  required={false}
                  onChange={handleChange}
                  error={errors.filterPhrase}
                />
              </Form.Group>
              <Button type="submit" block>{t('phrase_get')}</Button>
            </Form>
          )}
        </Formik>
      </div>
    );
  } if (accountList.isLoading) {
    return <ProgressBar />;
  }
  return (
    <div style={{ minHeight: '100%', marginTop: '1rem' }}>
      <AccountsTable
        data={getData(accountList.data)}
        columns={getHeader([t('email'), t('active'), t('confirmed'), t('password_change'), t('access levels')], accountList.data)}
        filterPhrase={filterPhrase}
      />
    </div>
  );
};

export default ListAccountsFiltered;
