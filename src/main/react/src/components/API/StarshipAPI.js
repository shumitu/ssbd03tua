import RestFacade from './RestFacade';
import handleError from './errorHandler';

const StarshipAPI = {
  addStarship: async (name, crewCapacity, maximumWeight, fuelCapacity, maximumSpeed, yearOfManufacture, operational) => {
    try {
      const response = await RestFacade.post('/starships/add-starship',
        {
          name,
          crewCapacity,
          maximumWeight,
          fuelCapacity,
          maximumSpeed,
          yearOfManufacture,
          operational,
        });
      return response.data;
    } catch (e) {
      handleError(e);
    }
  },

  changeStatus: async (id, active) => {
    try {
      return await RestFacade.post('/starships/change-status',
        { id, active });
    } catch (e) {
      handleError(e);
    }
  },

  assignToOffer: async (starshipId, offerId) => {
    try {
      return await RestFacade.patch('/offers/assign-starship',
        { starshipId, offerId });
    } catch (e) {
      handleError(e);
    }
  },
  editStarship: async (etag, id, name, crewCapacity, maximumWeight, fuelCapacity, maximumSpeed, yearOfManufacture) => {
    try {
      return await RestFacade.patch('/starships/edit-starship',
        {
          etag,
          id,
          name,
          crewCapacity,
          maximumWeight,
          fuelCapacity,
          maximumSpeed,
          yearOfManufacture,
        });
    } catch (e) {
      handleError(e);
    }
  },

};

export default StarshipAPI;
