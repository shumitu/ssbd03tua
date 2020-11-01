import React, { Suspense } from 'react';
import { Route, Switch } from 'react-router';
import ClientMenu from './CandidateMenu';
import ShowAccountDetails from '../../Details/ShowAccountDetails';
import EditAccountDetails from '../../Details/EditAccountDetails';
import ShowApplicationDetails from '../../Details/ShowApplicationDetails';
import ShowReviewDetails from '../../Details/ShowReviewDetails';
import ShowStarshipDetails from '../../Details/ShowStarshipDetails';
import ChangePassword from '../ChangePassword';
import { MenuFallback } from '../../misc/FallBacks';
import AfterLogin from '../all/AfterLogin';
import Page404 from '../all/404';
import HomePage from '../HomePage';
import { ROUTES } from '../../../resources/AppConstants';
import PageUnauthorized from '../all/Unauthorized';
import PageUnexpected from '../all/UnexpectedError';
import ListActiveOffers from './ListActiveOffers';
import ListOperationalStarships from '../all/ListOperationalStarships';
import OwnApplicationsList from './OwnApplicationsList';
import ShowOfferDetails from '../../Details/ShowOfferDetails';
import EditApplicationDetails from '../../Details/EditApplicationDetails';
import ApplyToOffer from './ApplyToOffer';

const CandidateMainView = () => (
  <>
    <Suspense fallback={<MenuFallback />}>
      <ClientMenu />
    </Suspense>
    <Suspense fallback={null}>
      <Switch>
        <Route exact path={ROUTES.AFTER_LOGIN}><AfterLogin /></Route>
        <Route exact path={ROUTES.APPLICATIONS_OWN_LIST}><OwnApplicationsList /></Route>
        <Route exact path={ROUTES.OPERATIONAL_STARSHIPS}><ListOperationalStarships /></Route>
        <Route exact path={ROUTES.STARSHIP_DETAILS} render={({ match }) => <ShowStarshipDetails match={match} />} />
        <Route exact path={ROUTES.EDIT_MY_ACCOUNT} render={({ match }) => <EditAccountDetails match={match} />} />
        <Route exact path={ROUTES.MY_ACCOUNT}><ShowAccountDetails /></Route>
        <Route exact path={ROUTES.CHANGE_PASSWORD}><ChangePassword /></Route>
        <Route exact path={ROUTES.ACTIVE_OFFERS}><ListActiveOffers /></Route>
        <Route exact path={ROUTES.OFFER_DETAILS} render={({ match }) => <ShowOfferDetails match={match} />} />
        <Route exact path={ROUTES.APPLICATION_MY_DETAILS} render={({ match }) => <ShowApplicationDetails match={match} />} />
        <Route exact path={ROUTES.EDIT_APPLICATION_DETAILS} render={({ match }) => <EditApplicationDetails match={match} />} />
        <Route exact path={ROUTES.REVIEW_MY_DETAILS} render={({ match }) => <ShowReviewDetails match={match} />} />
        <Route exact path={ROUTES.APPLY_TO_OFFER} render={({ match }) => <ApplyToOffer match={match} />} />
        <Route exact path={ROUTES.UNAUTHORIZED}><PageUnauthorized /></Route>
        <Route exact path={ROUTES.UNEXPECTED_ERROR}><PageUnexpected /></Route>
        <Route exact path="/"><HomePage /></Route>
        <Route path="*"><Page404 /></Route>
      </Switch>
    </Suspense>
  </>
);

export default CandidateMainView;
