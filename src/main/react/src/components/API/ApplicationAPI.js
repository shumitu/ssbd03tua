import RestFacade from './RestFacade';
import handleError from './errorHandler';

const ApplicationAPI = {
    updateApplicationDetails: async (etag, applicationId, weight, examinationCode, motivationalLetter) => {
      try {
        const response = await RestFacade.patch('/applications/edit',
          {
            etag, applicationId, weight, examinationCode, motivationalLetter,
          });
        return response.data;
      } catch (e) {
        handleError(e);
      }
    },
    addApplicationToCategory: async (applicationId, categoryName, etag) => {
        try {
            const response = await RestFacade.post('/applications/addToCategory',
                {applicationId, categoryName, etag});
            return response.data;
        } catch (e) {
            handleError(e);
        }
    },
    createApplication: async ( weight, examinationCode, motivationalLetter, offerId, offerEtag ) => {
        try {
            const response = await RestFacade.post('/applications/applyToOffer',
                { weight, examinationCode, motivationalLetter, offerId, offerEtag });
            return response.data;
        } catch (e) {
            handleError(e);
        }
    }

};

export default ApplicationAPI;
