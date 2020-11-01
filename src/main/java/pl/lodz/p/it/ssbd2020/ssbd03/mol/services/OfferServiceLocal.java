package pl.lodz.p.it.ssbd2020.ssbd03.mol.services;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.*;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.offerDetails.OfferDetailsCandidateDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.offerDetails.OfferDetailsEmployeeDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.TransactionException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface OfferServiceLocal {
    List<OfferForAllListDTO> getAllOffers() throws AppException;

    List<OfferForActiveListDTO> getActiveOffers() throws AppException;

    void showOffer(Long offerId) throws AppException;

    void hideOffer(Long offerId) throws AppException;
    
    void addOffer(OfferDTO offerDTO) throws AppException;

    void removeOffer(RemoveOfferDTO removeOfferDTO) throws AppException;

    void editOffer(UpdatedOfferDetailsDTO updatedOfferDetailsDTO) throws AppException;
    
    void openOffer(Long offerId) throws AppException;

    void closeOffer(Long offerId) throws AppException;

    int getCallCounter();

    int getMaxTransactionCount();

    boolean isLastTransactionRollback();

    OfferDetailsCandidateDTO getOfferDetailsCandidate(Long id) throws AppException;

    OfferDetailsEmployeeDTO getOfferDetailsEmployee(Long id) throws AppException;

    void assignStarshipToOffer(AssignStarshipToOfferDTO assignStarshipToOfferDTO) throws AppException;
}
