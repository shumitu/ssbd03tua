import React, { Suspense } from 'react';
import { Route, Switch } from 'react-router';
import EmployeeMenu from './EmployeeMenu';
import ShowAccountDetails from '../../Details/ShowAccountDetails';
import EditAccountDetails from '../../Details/EditAccountDetails';
import { MenuFallback } from '../../misc/FallBacks';
import ChangePassword from '../ChangePassword';
import AfterLogin from '../all/AfterLogin';
import Page404 from '../all/404';
import HomePage from '../HomePage';
import { ROUTES } from '../../../resources/AppConstants';
import PageUnauthorized from '../all/Unauthorized';
import PageUnexpected from '../all/UnexpectedError';
import AddStarship from './AddStarship';
import ListAllStarships from '../all/ListAllStarships';
import ListAllOffers from './ListAllOffers';
import OfferApplicationsList from './OfferApplicationsList';
import AddOffer from './AddOffer';
import ShowApplicationDetails from '../../Details/ShowApplicationDetails';
import ShowReviewDetails from '../../Details/ShowReviewDetails';
import EditOfferDetails from '../../Details/EditOfferDetails';
import ShowStarshipDetails from '../../Details/ShowStarshipDetails';
import EditStarshipDetails from '../../Details/EditStarshipDetails';
import ShowOfferDetails from '../../Details/ShowOfferDetails';
import AssignStarshipToOffer from './AssignStarshipToOffer';

const EmployeeMainView = () => (
  <>
    <Suspense fallback={<MenuFallback />}>
      <EmployeeMenu />
    </Suspense>
    <Suspense fallback={null}>
      <Switch>
        <Route exact path={ROUTES.AFTER_LOGIN}><AfterLogin /></Route>
        <Route
          exact
          path={ROUTES.APPLICATIONS_LIST}
          render={({ match }) => <OfferApplicationsList match={match} />}
        />
        <Route
            exact
            path={ROUTES.APPLICATION_DETAILS}
            render={({match}) => <ShowApplicationDetails match={match} employeeMode={true}/>}
        />
        <Route
          exact
          path={ROUTES.REVIEW_DETAILS}
          render={({ match }) => <ShowReviewDetails match={match} />}
        />
        <Route exact path={ROUTES.MY_ACCOUNT}><ShowAccountDetails /></Route>
        <Route exact path={ROUTES.ALL_STARSHIPS}><ListAllStarships /></Route>
        <Route
          exact
          path={ROUTES.EDIT_MY_ACCOUNT}
          render={({ match }) => <EditAccountDetails match={match} />}
        />
        <Route path={ROUTES.CHANGE_PASSWORD}><ChangePassword /></Route>
        <Route exact path={ROUTES.ALL_OFFERS}><ListAllOffers /></Route>
        <Route exact path={ROUTES.ADD_OFFER}><AddOffer /></Route>
        <Route
          exact
          path={ROUTES.OFFER_DETAILS}
          render={({ match }) => <ShowOfferDetails match={match} employeeMode />}
        />
        <Route
          exact
          path={ROUTES.EDIT_OFFER}
          render={({ match }) => <EditOfferDetails match={match} />}
        />
        <Route
          exact
          path={ROUTES.ASSIGN_STARSHIP}
          render={({ match }) => <AssignStarshipToOffer match={match} />}
        />
        <Route
          exact
          path={ROUTES.STARSHIP_DETAILS}
          render={({ match }) => <ShowStarshipDetails match={match} employeeMode />}
        />
        <Route
          exact
          path={ROUTES.EDIT_STARSHIP}
          render={({ match }) => <EditStarshipDetails match={match} />}
        />
        <Route exact path={ROUTES.UNAUTHORIZED}><PageUnauthorized /></Route>
        <Route exact path={ROUTES.UNEXPECTED_ERROR}><PageUnexpected /></Route>
        <Route exact path={ROUTES.ADD_STARSHIP}><AddStarship /></Route>
        <Route exact path="/"><HomePage /></Route>
        <Route path="*"><Page404 /></Route>
      </Switch>
    </Suspense>
  </>
);

export default EmployeeMainView;
