import RestFacade from './RestFacade';
import handleError from './errorHandler';

const OfferAPI = {
  addOffer: async (flightStartTime, flightEndTime, destination, price, description, hidden, open, totalCost, starshipId) => {
    try {
      const response = await RestFacade.post('/offers/addOffer',
        {
          flightStartTime, flightEndTime, destination, price, description, hidden, open, totalCost, starshipId,
        });
      return response.data;
    } catch (e) {
      handleError(e);
    }
  },

  getOffer: async (id) => {
    try {
      const response = await RestFacade.get(`offers/employee/${id}`);
      console.log(response.data);
      return response.data;
    } catch (e) {
      handleError(e);
    }
  },

  updateOffer: async (etag, offerId, flightStartTime, flightEndTime, destination, description, price, totalCost) => {
    try {
      const response = await RestFacade.patch('/offers/editOffer',
        {
          etag, offerId, flightStartTime, flightEndTime, destination, description, price, totalCost,
        });
      return response.data;
    } catch (e) {
      handleError(e);
    }
  },
};

export default OfferAPI;
