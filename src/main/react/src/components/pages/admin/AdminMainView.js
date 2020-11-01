import React, {Suspense} from 'react';
import {Route, Switch} from 'react-router';
import AdminMenu from './AdminMenu';
import ShowAccountDetails from '../../Details/ShowAccountDetails';
import EditAccountDetails from '../../Details/EditAccountDetails';
import ListAccountsReports from './ListAccountsReports';
import {MenuFallback} from '../../misc/FallBacks';
import ChangePassword from '../ChangePassword';
import AfterLogin from '../all/AfterLogin';
import ListAccountsFiltered from './ListAccountsFiltered';
import Page404 from '../all/404';
import HomePage from '../HomePage';
import {ROUTES} from '../../../resources/AppConstants';
import PageUnauthorized from '../all/Unauthorized';
import ListAllOffers from '../employee/ListAllOffers';
import ListAllStarships from '../all/ListAllStarships';
import PageUnexpected from '../all/UnexpectedError';
import ShowOfferDetails from '../../Details/ShowOfferDetails';
import ShowStarshipDetails from '../../Details/ShowStarshipDetails';

const AdminMainView = () => (
  <>
    <Suspense fallback={<MenuFallback />}>
      <AdminMenu />
    </Suspense>
    <Suspense fallback={null}>
      <Switch>
        <Route exact path={ROUTES.AFTER_LOGIN}><AfterLogin /></Route>
        <Route exact path={ROUTES.ACCOUNTS}><ListAccountsFiltered /></Route>
        <Route exact path={ROUTES.ALL_STARSHIPS}><ListAllStarships /></Route>
        <Route exact path={ROUTES.ACCOUNTS_REPORTS}><ListAccountsReports /></Route>
        <Route exact path={ROUTES.MY_ACCOUNT}><ShowAccountDetails /></Route>
        <Route
          exact
          path={ROUTES.ACCOUNT_DETAILS}
          render={({ match }) => <ShowAccountDetails match={match} />}
        />
        <Route
          exact
          path={ROUTES.EDIT_ACCOUNT}
          render={({ match }) => <EditAccountDetails match={match} adminMode />}
        />
        <Route
          exact
          path={ROUTES.EDIT_MY_ACCOUNT}
          render={({ match }) => <EditAccountDetails match={match} />}
        />
        <Route exact path={ROUTES.CHANGE_PASSWORD}><ChangePassword /></Route>
        <Route
          exact
          path={ROUTES.CHANGE_USERS_PASSWORD}
          render={({ match }) => <ChangePassword match={match} />}
        />
        <Route exact path={ROUTES.ALL_OFFERS}><ListAllOffers /></Route>
        <Route
            exact
            path={ROUTES.OFFER_DETAILS}
            render={({match}) => <ShowOfferDetails match={match} employeeMode={false}/>}
        />
        <Route
            exact
            path={ROUTES.STARSHIP_DETAILS}
            render={({match}) => <ShowStarshipDetails match={match} employeeMode={true}/>}
        />
        <Route exact path="/"><HomePage /></Route>
        <Route path={ROUTES.UNAUTHORIZED}><PageUnauthorized /></Route>
        <Route exact path={ROUTES.UNEXPECTED_ERROR}><PageUnexpected /></Route>
        <Route path="*"><Page404 /></Route>
      </Switch>
    </Suspense>
  </>
);

export default AdminMainView;
