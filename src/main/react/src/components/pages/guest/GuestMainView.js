import React, {Suspense} from 'react';
import {Route} from 'react-router';
import {AnimatedSwitch} from 'react-router-transition';
import GuestMenu from './GuestMenu';
import Login from '../Login';
import Register from './Register';
import FinishResetPassword from './FinishResetPassword';
import InitResetPassword from './InitResetPassword';
import ActivateAccount from '../ActivateAccount';
import {bounceTransition, mapStyles} from '../../misc/Transistions';
import {MenuFallback} from '../../misc/FallBacks';
import Page404 from '../all/404';
import HomePage from '../HomePage';
import ContactCard from '../all/ContactCard';
import {ROUTES} from '../../../resources/AppConstants';
import PageUnauthorized from '../all/Unauthorized';
import PageUnexpected from '../all/UnexpectedError';
import ListActiveOffers from '../candidate/ListActiveOffers';
import ListOperationalStarships from '../all/ListOperationalStarships';
import ShowOfferDetails from '../../Details/ShowOfferDetails';
import ShowStarshipDetails from '../../Details/ShowStarshipDetails';

const GuestMainView = () => (
  <>
    <Suspense fallback={<MenuFallback />}>
      <GuestMenu />
    </Suspense>
    <Suspense fallback={null}>
      <AnimatedSwitch
        atEnter={bounceTransition.atEnter}
        atLeave={bounceTransition.atLeave}
        atActive={bounceTransition.atActive}
        mapStyles={mapStyles}
        className="switch-wrapper"
      >
        <Route exact path={ROUTES.LOGIN}><Login /></Route>
        <Route exact path={ROUTES.CONTACT}><ContactCard /></Route>
        <Route exact path={ROUTES.REGISTER}><Register /></Route>
        <Route exact path={ROUTES.OPERATIONAL_STARSHIPS}><ListOperationalStarships /></Route>
        <Route
          exact
          path={ROUTES.ACTIVATE_ACCOUNT}
          render={({ location }) => <ActivateAccount location={location} />}
        />
        <Route exact path={ROUTES.INIT_RESET_PASSWORD}><InitResetPassword /></Route>
        <Route
          exact
          path={ROUTES.FINISH_RESET_PASSWORD}
          render={({ location }) => <FinishResetPassword location={location} />}
        />
        <Route
            exact
            path={ROUTES.STARSHIP_DETAILS}
            render={({match}) => <ShowStarshipDetails match={match}/>}
        />
        <Route exact path={ROUTES.ACTIVE_OFFERS}><ListActiveOffers /></Route>
        <Route exact path={ROUTES.OFFER_DETAILS} render={({ match }) => <ShowOfferDetails match={match} />} />
        <Route exact path={ROUTES.UNAUTHORIZED}><PageUnauthorized /></Route>
        <Route exact path={ROUTES.UNEXPECTED_ERROR}><PageUnexpected /></Route>
        <Route exact path="/"><HomePage /></Route>
        <Route path="*"><Page404 /></Route>
      </AnimatedSwitch>
    </Suspense>
  </>
);

export default GuestMainView;
