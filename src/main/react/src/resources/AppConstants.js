export const SITE_KEY = '6LeIxAcTAAAAAJcZVRqyHh71UMIEGNQ_MXjiZKhI';
export const API_CONTEXT = '/api';
export const LOCAL_FRONT_URL = 'http://localhost:3000';
export const LOCAL_BACK_URL = 'https://localhost:8443';

export const AUTH_ROLE = {
  GUEST: '',
  CANDIDATE: 'CANDIDATE',
  EMPLOYEE: 'EMPLOYEE',
  ADMIN: 'ADMIN',
};
export const ROUTES = {
    LOGIN: '/login',
    AFTER_LOGIN: '/after_login',
    REGISTER: '/guest/register',
    ACTIVATE_ACCOUNT: '/activate_account',
    FINISH_RESET_PASSWORD: '/password_reset',
    INIT_RESET_PASSWORD: '/guest/init_reset_password',
    MY_ACCOUNT: '/my-account',
    ACCOUNT_DETAILS: '/accounts/:email',
    EDIT_ACCOUNT: '/accounts/edit/:email',
    CHANGE_PASSWORD: '/change_password',
    CHANGE_USERS_PASSWORD: '/change_users_password/:email',
    ACCOUNTS: '/accounts',
    ACCOUNTS_REPORTS: '/accounts_reports',
    EDIT_MY_ACCOUNT: '/my-account/edit',
    CONTACT: '/contact-card',
    UNAUTHORIZED: '/unauthorized',
    ADD_OFFER: '/offers/add',
    EDIT_OFFER: '/offers/edit/:offerId',
    ALL_OFFERS: '/offers/all',
    ACTIVE_OFFERS: '/offers/active',
    ASSIGN_STARSHIP: '/offers/assign/:offerId',
    OFFER_DETAILS: '/offers/:offerId',
    ADD_STARSHIP: '/starships/add',
    ALL_STARSHIPS: '/all-starships',
    OPERATIONAL_STARSHIPS: '/operational-starships',
    STARSHIP_DETAILS: '/starships/details/:starshipId',
    EDIT_STARSHIP: '/starships/edit/:starshipId',
    UNEXPECTED_ERROR: '/unexpected_error',
    APPLICATIONS_OWN_LIST: '/applications/ownList',
    APPLICATIONS_LIST: '/applications/list/:offerId',
    APPLICATION_DETAILS: '/getApplication/:offerId/:id',
    APPLICATION_MY_DETAILS: '/getMyApplication/:id',
    APPLY_TO_OFFER: '/applications/applyToOffer/:offerId',
    EDIT_APPLICATION_DETAILS: '/applications/edit/:applicationId',
    REVIEW_DETAILS: '/getReview/:offerId/:id',
    REVIEW_MY_DETAILS: '/getReview/:id',
};
export const HomeBreadcrumb = { to: '/', content: 'home' };
export const LoginBreadcrumb = { to: ROUTES.LOGIN, content: 'login' };
export const AfterLoginBreadcrumb = { to: ROUTES.AFTER_LOGIN, content: 'after_login' };
export const RegisterBreadcrumb = { to: ROUTES.REGISTER, content: 'register' };
export const ActivateAccountBreadcrumb = { to: ROUTES.ACTIVATE_ACCOUNT, content: 'activate_account' };
export const FinishResetPasswordBreadcrumb = { to: ROUTES.FINISH_RESET_PASSWORD, content: 'finish_reset_password' };
export const InitResetPasswordBreadcrumb = { to: ROUTES.INIT_RESET_PASSWORD, content: 'init_reset_password' };
export const MyAccountBreadcrumb = { to: ROUTES.MY_ACCOUNT, content: 'my_account' };
export const AccountDetailsBreadcrumb = (email) => ({ to: `/accounts/${email}`, content: 'account_details' });
export const EditAccountBreadcrumb = (email) => ({ to: `/accounts/edit/${email}`, content: 'edit' });
export const ChangePasswordBreadcrumb = { to: ROUTES.CHANGE_PASSWORD, content: 'change_password' };
export const ChangeUsersPasswordBreadcrumb = (email) => ({ to: `/change_users_password/${email}`, content: 'change_password' });
export const AccountListBreadcrumb = { to: ROUTES.ACCOUNTS, content: 'accounts' };
export const ApplicationListBreadcrumb = (offerId) => ({ to: `/applications/list/${offerId}`, content: 'application_list' });
export const AllStarshipListBreadcrumb = { to: ROUTES.ALL_STARSHIPS, content: 'all_starships' };
export const OperationalStarshipListBreadcrumb = { to: ROUTES.OPERATIONAL_STARSHIPS, content: 'operational_starships' };
export const AccountReportsBreadcrumb = { to: ROUTES.ACCOUNTS_REPORTS, content: 'account_reports' };
export const EditMyAccountBreadcrumb = { to: ROUTES.EDIT_MY_ACCOUNT, content: 'edit' };
export const Page404Breadcrumb = { to: '/404', content: '404' };
export const Page403Breadcrumb = { to: ROUTES.UNAUTHORIZED, content: 'unauthorized' };
export const Page500Breadcrumb = { to: ROUTES.UNEXPECTED_ERROR, content: 'unexpected_error' };
export const AddStarshipBreadcrumb = { to: ROUTES.ADD_STARSHIP, content: 'add_starship' };
export const AddOfferBreadcrumb = { to: ROUTES.ADD_OFFER, content: 'add_offer' };
export const EditOfferBreadcrumb = (offerId) => ({ to: `/offers/edit/${offerId}`, content: 'edit_offer' });

export const OfferDetailsBreadcrumb = (offerId) => ({ to: `/offers/${offerId}`, content: 'offer_details' });
export const ApplicationsOwnListBreadcrumb = { to: ROUTES.APPLICATIONS_OWN_LIST, content: 'application_own_list' };
export const AllOffersListBreadcrumb = { to: ROUTES.ALL_OFFERS, content: 'all_offers_list' };
export const ActiveOffersListBreadcrumb = { to: ROUTES.ACTIVE_OFFERS, content: 'active_offers_list' };
export const ApplicationDetailsBreadcrumb = (offerId, id) => ({ to: `/getApplication/${offerId}/${id}`, content: 'application_details' });
export const ApplicationMyDetailsBreadcrumb = (id) => ({ to: `/getMyApplication/${id}`, content: 'application_my_details' });

export const EditApplicationDetailsBreadcrumb = (applicationId) => ({ to: `/applications/edit/${applicationId}`, content: 'edit_application_details' });

export const ApplyToOfferBreadcrumb = (offerId) => {
    return {to: `/applications/applyToOffer/${offerId}`, content: 'apply_to_offer'};
};

export const ReviewDetailsBreadcrumb = (offerId, id) => {
    return {to: `/getReview/${offerId}/${id}`, content: 'review_details'};
};

export const ReviewMyDetailsBreadcrumb = (id) => ({ to: `/getReview/${id}`, content: 'review_my_details' });

export const APPLICATION_CATEGORIES = {
  Accepted: 'Accepted',
  Reserve: 'Reserve',
  Rejected: 'Rejected'
};

export const StarshipDetailsBreadcrumb = (starshipId) => ({ to: `/starships/details/${starshipId}`, content: 'starship_details' });
export const EditStarshipBreadcrumb = (starshipId) => ({ to: `/starships/edit/${starshipId}`, content: 'edit_starship' });

export const AssignStarshipBreadcrumb = (offerId) => ({ to: `/offers/assign/${offerId}`, content: 'assign_starship' });