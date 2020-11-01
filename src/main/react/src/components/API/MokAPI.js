import RestFacade from './RestFacade';
import handleError from './errorHandler';

const MokAPI = {
  login: async (email, password) => {
    let response;
    try {
      response = await RestFacade.post('/login', null, null, { email, password });
    } catch (e) {
      if (e.response !== undefined && e.response.status === 401) throw new Error('error');
      else {
        handleError(e);
      }
    }
    const bearer = response.headers.authorization;
    try {
      response = await RestFacade.get('/login', { Authorization: bearer });
    } catch (e) {
      handleError(e);
    }
    return { bearer, claims: response.data };
  },

  getAccountsReports: async () => {
    try {
      const response = await RestFacade.get('/accounts/listReports');
      return response.data;
    } catch (e) {
      handleError(e);
    }
  },

  getSingleAccount: async (email) => {
    try {
      const response = await RestFacade.get(`/accounts/${email}`);
      return response.data;
    } catch (e) {
      handleError(e);
    }
  },

  getOwnAccount: async () => {
    try {
      const response = await RestFacade.get('/accounts/my-account');
      return response.data;
    } catch (e) {
      handleError(e);
    }
  },

  updateOwnAccount: async (etag, email, candidate, motto, captcha) => {
    try {
      const response = await RestFacade.patch('/accounts/editOwnAccount/',
        {
          etag, email, candidate, motto, captcha,
        });
      return response.data;
    } catch (e) {
      handleError(e);
    }
  },

  updateOtherAccount: async (etag, email, candidate, motto) => {
    try {
      const data = {
        etag, email, candidate, motto,
      };
      const response = await RestFacade.patch('/accounts/editOtherAccount/', data);
      return response.data;
    } catch (e) {
      if (e.response.status === 412) {
        handleError(e);
      } else {
        handleError(e);
      }
    }
  },

  changeOwnPassword: async (email, oldPassword, newPassword, captcha) => {
    try {
      await RestFacade.patch('/accounts/changeOwnPassword', {
        email, oldPassword, newPassword, captcha,
      });
    } catch (e) {
      handleError(e);
    }
  },

  changeSomeonesPassword: async (email, newPassword) => {
    try {
      await RestFacade.patch('/accounts/changeSomeonesPassword', { email, newPassword });
    } catch (e) {
      handleError(e);
    }
  },

  initResetPassword: async (email, locale) => {
    try {
      await RestFacade.post('/accounts/resetPassword', { email, locale });
    } catch (e) {
      handleError(e);
    }
  },

  confirmResetPassword: async (token, password) => {
    try {
      const response = await RestFacade.patch('/accounts/confirmResetPassword', { token, password });
      return response.data;
    } catch (e) {
      handleError(e);
    }
  },

  register: async (email, password, firstName, lastName, motto, locale, captcha) => {
    try {
      const response = await RestFacade.post('/accounts/createAccount',
        {
          email, password, firstName, lastName, motto, locale, captcha,
        });
      return response.data;
    } catch (e) {
      handleError(e);
    }
  },

  sendConfirmationLink: async (email, locale) => {
    try {
      const response = await RestFacade.post('/accounts/sendConfirmationLink',
        { email, locale });
      return response.data;
    } catch (e) {
      handleError(e);
    }
  },

  activateAccount: async (email, token, locale) => {
    try {
      const response = await RestFacade.patch('/accounts/activateAccount',
        { email, token, locale });
      return response.data;
    } catch (e) {
      handleError(e);
    }
  },

  changeActiveStatus: async (email, active) => {
    try {
      const response = await RestFacade.patch('/accounts/block', { email, active });
      return response.data;
    } catch (e) {
      handleError(e);
    }
  },

  getLoginTimestamps: async () => {
    try {
      const response = await RestFacade.get('/accounts/loginTimestamps');
      return response.data;
    } catch (e) {
      handleError(e);
    }
  },

  grantAccessLevel: async (email, level) => {
    try {
      const response = await RestFacade.patch('/accounts/addAccessLevel', { email, level });
      return response.data;
    } catch (e) {
      handleError(e);
    }
  },

  revokeAccessLevel: async (email, level) => {
    try {
      const response = await RestFacade.patch('/accounts/revokeAccessLevel', { email, level });
      return response.data;
    } catch (e) {
      handleError(e);
    }
  },

  changeAccessLevel: async (level) => {
    try {
      const response = await RestFacade.post('/accounts/changeAccessLevel', { level });
      return response.data;
    } catch (e) {
      handleError(e);
    }
  },

};

export default MokAPI;
