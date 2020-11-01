import { combineReducers } from 'redux';
import { authReducer, currentAccessLevelReducer } from './auth';
import { titleReducer } from './meta';
import { accountDetailsReducer } from './Account/AccountDetails';
import { accountListReducer } from './Account/AccountList';
import { accountReportsListReducer } from './Account/AccountReportsList';
import { breadcrumbReducer } from './breadcrumb';
import { allOffersListReducer } from './Offer/AllOfferList';
import { starshipListReducer } from './Starship/StarshipList';
import { applicationOwnListReducer } from './Application/ApplicationOwnList';
import { activeOffersListReducer } from './Offer/ActiveOfferList';
import { applicationDetailsReducer } from './Application/ApplicationDetails';
import { reviewDetailsReducer } from './Review/ReviewDetails';
import { offerDetailsReducer } from './Offer/OfferDetails';
import { starshipDetailsReducer } from './Starship/StarshipDetails';
import { editApplicationDetailsReducer } from './Application/EditApplicationDetails';
import { applicationListReducer } from './Application/ApplicationList';

const rootReducer = combineReducers({
  auth: authReducer,
  breadcrumb: breadcrumbReducer,
  siteTitle: titleReducer,
  currentAccessLevel: currentAccessLevelReducer,
  accountDetails: accountDetailsReducer,
  accountList: accountListReducer,
  starshipList: starshipListReducer,
  accountReportsList: accountReportsListReducer,
  applicationsOwnList: applicationOwnListReducer,
  applicationsList: applicationListReducer,
  allOffersList: allOffersListReducer,
  activeOffersList: activeOffersListReducer,
  applicationDetails: applicationDetailsReducer,
  reviewDetails: reviewDetailsReducer,
  offerDetails: offerDetailsReducer,
  starshipDetails: starshipDetailsReducer,
  editApplicationDetails: editApplicationDetailsReducer,

});

export default rootReducer;
