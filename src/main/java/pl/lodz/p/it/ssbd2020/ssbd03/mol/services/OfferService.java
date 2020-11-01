package pl.lodz.p.it.ssbd2020.ssbd03.mol.services;

import pl.lodz.p.it.ssbd2020.ssbd03.core.AbstractService;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.*;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.converters.OfferForActiveListConverter;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.converters.OfferForAllListConverter;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.converters.offerDetails.OfferDetailsCandidateConverter;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.converters.offerDetails.OfferDetailsEmployeeConverter;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.offerDetails.OfferDetailsCandidateDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.offerDetails.OfferDetailsEmployeeDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Offer;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Starship;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.*;
import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd03.mol.facades.OfferFacadeLocal;
import pl.lodz.p.it.ssbd2020.ssbd03.mol.facades.StarshipFacadeLocal;
import pl.lodz.p.it.ssbd2020.ssbd03.security.services.HmacSigner;
import pl.lodz.p.it.ssbd2020.ssbd03.utils.OfferValidator;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.*;

@Stateful
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
public class OfferService extends AbstractService implements SessionSynchronization, OfferServiceLocal {

    @Inject
    OfferFacadeLocal offerFacade;
    @Inject
    StarshipFacadeLocal starshipFacade;

    /**
     * Metoda zwracająca wszystkie oferty
     *
     * @return lista obiektów DTO z danymi ofert
     * @throws AppException wyjątek aplikacyjny
     * @author Jakub Wróbel
     */
    @RolesAllowed(getAllOffers)
    @Override
    public List<OfferForAllListDTO> getAllOffers() throws AppException {
        try {
            List<Offer> offers = offerFacade.findAll();
            OfferForAllListConverter converter = new OfferForAllListConverter();
            List<OfferForAllListDTO> offerDTOs = new ArrayList<>();
            for (Offer offer : offers) {
                offerDTOs.add(converter.convert(offer));
            }
            return offerDTOs;
        } catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda zwracająca aktywne oferty
     *
     * @return lista obiektów DTO z danymi aktywnych ofert
     * @throws AppException wyjątek aplikacyjny
     * @author Jakub Wróbel
     */
    @PermitAll
    @Override
    public List<OfferForActiveListDTO> getActiveOffers() throws AppException {
        try {
            List<Offer> offers = offerFacade.findActive();
            OfferForActiveListConverter converter = new OfferForActiveListConverter();
            List<OfferForActiveListDTO> offerDTOs = new ArrayList<>();
            for (Offer offer : offers) {
                offerDTOs.add(converter.convert(offer));
            }
            return offerDTOs;
        } catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda uwidacznia wybraną ofertę dla kandydatów w aplikacji
     *
     * @param offerId Identyfikator oferty do upublicznienia
     * @throws AppException wyjątek aplikacyjny związany z niepowodzeniem operacji,
     * spowodowanym np. faktem że oferta jest już widoczna
     * @author Jakub Fornalski
     */

    @Override
    @RolesAllowed(changeOfferVisibility)
    public void showOffer(Long offerId) throws AppException {
        try {
            Offer offer = offerFacade.findById(offerId);
            if (!offer.getHidden())
                throw OfferException.createExceptionAlreadyVisible();
            else {
                offer.setHidden(false);
                offerFacade.edit(offer);
            }
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda ukrywa wybraną ofertę przed kandydatami w aplikacji
     *
     * @param offerId Identyfikator oferty do ukrycia
     * @throws AppException wyjątek aplikacyjny związany z niepowodzeniem operacji,
     * spowodowanym np. faktem że oferta jest już ukryta
     * @author Jakub Fornalski
     */

    @Override
    @RolesAllowed(changeOfferVisibility)
    public void hideOffer(Long offerId) throws AppException {
        try {
            Offer offer = offerFacade.findById(offerId);
            if (offer.getHidden())
                throw OfferException.createExceptionAlreadyHidden();
            else {
                offer.setHidden(true);
                offerFacade.edit(offer);
            }
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda dodająca nową ofertę
     *
     * @param offerDTO obiekt zawierający dane oferty, lotu, statku
     * @author Sergiusz Parchatko
     */
    @RolesAllowed(addOffer)
    @Override
    public void addOffer(OfferDTO offerDTO) throws AppException {
        if (!OfferValidator.isDescriptionValid(offerDTO.getDescription())) {
            throw OfferException.createExceptionIncorrectDescription();
        }
        if (!OfferValidator.isDestinationValid(offerDTO.getDestination())) {
            throw OfferException.createExceptionIncorrectDestination();
        }
        if (!OfferValidator.isStartDateValid(offerDTO.getFlightStartTime())) {
            throw OfferException.createExceptionIncorrectStartDate();
        }
        if (!OfferValidator.isEndDateValid(offerDTO.getFlightEndTime())) {
            throw OfferException.createExceptionIncorrectEndDate();
        }
        if (!OfferValidator.isEndDateAfterStartDate(offerDTO.getFlightStartTime(), offerDTO.getFlightEndTime())) {
            throw OfferException.createExceptionIncorrectDatesOrder();
        }
        if (!OfferValidator.isPriceValid(offerDTO.getPrice())) {
            throw OfferException.createExceptionIncorrectPrice();
        }
        if (!OfferValidator.isTotalCostValid(offerDTO.getTotalCost())) {
            throw OfferException.createExceptionIncorrectTotalCost();
        }

        if (!OfferValidator.isStarshipIdValid(offerDTO.getStarshipId())) {
            throw OfferException.createExceptionIncorrectStarshipId();
        }


        try {
            Starship starship = starshipFacade.findById(offerDTO.getStarshipId());
            starshipFacade.lockPessimisticRead(starship);
            if(offerFacade.checkIfOverlappingForAdding(starship, offerDTO.getFlightStartTime(), offerDTO.getFlightEndTime())) {
                throw OfferException.createExceptionOverlappingOfferFound();
            }

            if(!starship.getOperational()) {
                throw OfferException.createExceptionStarshipNotOperational();
            }

            Offer offer = new Offer(offerDTO.getFlightStartTime(), offerDTO.getFlightEndTime(),
                    offerDTO.getDestination(), offerDTO.getPrice(), offerDTO.getDescription(),
                    offerDTO.isHidden(), offerDTO.isOpen(), offerDTO.getTotalCost(), starship);

            offerFacade.create(offer);
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda usuwająca ofertę
     *
     * @param removeOfferDTO obiekt zawierający dane o tym którą ofertę usunąć
     * @throws AppException wyjątek aplikacyjny
     * @author Jakub Wróbel
     */
    @RolesAllowed(removeOffer)
    @Override
    public void removeOffer(RemoveOfferDTO removeOfferDTO) throws AppException {
        try {
            Offer offer = offerFacade.findById(removeOfferDTO.getOfferID());
            offerFacade.lockPessimisticRead(offer);
            if (offer.getApplicationCollection().size() != 0) {
                throw OfferException.createExceptionOfferHasApplications();
            }
            if (!offer.getHidden()){
                throw OfferException.createExceptionCannotDeleteIsNotHidden();
            }
            if (offer.getOpen()){
                throw OfferException.createExceptionCannotDeleteIsOpen();
            }
            offerFacade.remove(offer);
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda edytująca dane oferty
     * @param updatedOfferDetailsDTO obiekt zawierający dane zaktualizowanej oferty
     * @author Sergiusz Parchatko
     */
    @RolesAllowed(editOffer)
    @Override
    public void editOffer(UpdatedOfferDetailsDTO updatedOfferDetailsDTO) throws AppException {
        try {

            Offer offer = offerFacade.findById(updatedOfferDetailsDTO.getOfferId());
            offerFacade.lockPessimisticRead(offer);

            if(offer.getApplicationCollection().size() > 0) {
                throw OfferException.editExceptionHasApplications();
            }

            if (HmacSigner.verifyEtag(updatedOfferDetailsDTO.getEtag(), offer.getVersion())) {

                if(!OfferValidator.isDescriptionValid(updatedOfferDetailsDTO.getDescription())){
                    throw OfferException.createExceptionIncorrectDescription();
                }
                offer.setDescription(updatedOfferDetailsDTO.getDescription());

                if(!OfferValidator.isDestinationValid(updatedOfferDetailsDTO.getDestination())){
                    throw OfferException.createExceptionIncorrectDestination();
                }
                offer.setDestination(updatedOfferDetailsDTO.getDestination());

                if(!OfferValidator.isStartDateValid(updatedOfferDetailsDTO.getFlightStartTime())) {
                    throw OfferException.createExceptionIncorrectStartDate();
                }
                if(!OfferValidator.isEndDateValid(updatedOfferDetailsDTO.getFlightEndTime())) {
                    throw OfferException.createExceptionIncorrectEndDate();
                }
                if(!OfferValidator.isEndDateAfterStartDate(updatedOfferDetailsDTO.getFlightStartTime(), updatedOfferDetailsDTO.getFlightEndTime())){
                    throw OfferException.createExceptionIncorrectDatesOrder();
                }
                if (!OfferValidator.isPriceValid(updatedOfferDetailsDTO.getPrice())) {
                    throw OfferException.createExceptionIncorrectPrice();
                }
                offer.setPrice(updatedOfferDetailsDTO.getPrice());

                if (!OfferValidator.isTotalCostValid(updatedOfferDetailsDTO.getTotalCost())) {
                    throw OfferException.createExceptionIncorrectTotalCost();
                }
                offer.setTotalCost(updatedOfferDetailsDTO.getTotalCost());

                starshipFacade.lockPessimisticRead(offer.getStarship());

                if (!(offer.getFlightStartTime().equals(updatedOfferDetailsDTO.getFlightStartTime()))
                        || !(offer.getFlightEndTime().equals(updatedOfferDetailsDTO.getFlightEndTime()))) {
                    if (offerFacade.checkIfOverlappingForEditing(offer.getStarship(), updatedOfferDetailsDTO.getFlightStartTime(),
                            updatedOfferDetailsDTO.getFlightEndTime(), updatedOfferDetailsDTO.getOfferId())) {

                        throw OfferException.createExceptionOverlappingOfferFound();
                    }
                    offer.setFlightStartTime(updatedOfferDetailsDTO.getFlightStartTime());
                    offer.setFlightEndTime(updatedOfferDetailsDTO.getFlightEndTime());
                }


                offerFacade.edit(offer);

            } else {
                throw ConcurrencyException.createResourceModifiedException();
            }
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda otwierająca zapisy do oferty lotu
     *
     * @param offerId Identyfikator oferty do otwarcia zapisów
     * @throws AppException - wyjątek aplikacyjny w przypadku gdy oferta jest
     * już otwarta, bądź napotkania błędu transakcji
     * @author Paweł Wacławiak 216910
     */
    @Override
    @RolesAllowed(openOffer)
    public void openOffer(Long offerId) throws AppException {
        try {
            Offer offer = offerFacade.findById(offerId);
            if(offer.getOpen()) {
                throw OfferException.createExceptionOfferAlreadyOpen();
            }
            offer.setOpen(true);
            offerFacade.edit(offer);
        } catch(TransactionRequiredLocalException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda otwiera zapisy do danej oferty
     *
     * @param offerId Identyfikator oferty do zamknięcia zapisów
     * @throws AppException wyjątek aplikacyjny
     * @author Jakub Wróbel
     */
    @RolesAllowed(closeOffer)
    @Override
    public void closeOffer(Long offerId) throws AppException {
        try {
            Offer offer = offerFacade.findById(offerId);
            if (!offer.getOpen()) {
                throw OfferException.createExceptionOfferAlreadyClosed();
            }
            offer.setOpen(false);
            offerFacade.edit(offer);
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda zwraca szczegóły danej oferty dla kandydata i gościa
     * @param id identyfikator oferty
     * @return Obiekt DTO zawierający szczegóły oferty dla kandydata i gościa
     * @throws AppException wyjątek aplikacyjny, gdy wystąpi błąd
     * @author Michał Tęgi
     */
    @Override
    @PermitAll
    public OfferDetailsCandidateDTO getOfferDetailsCandidate(Long id) throws AppException {
        try {
            OfferDetailsCandidateConverter converter = new OfferDetailsCandidateConverter();
            return converter.convert(offerFacade.findById(id));
        } catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda zwraca szczegóły danej oferty dla pracownika i administratora
     * @param id identyfikator oferty
     * @return Obiekt DTO zawierający szczegóły oferty dla pracownika i administratora
     * @throws AppException wyjątek aplikacyjny, gdy wystąpi błąd
     * @author Michał Tęgi
     */
    @Override
    @RolesAllowed(displayOfferDetailsEmployee)
    public OfferDetailsEmployeeDTO getOfferDetailsEmployee(Long id) throws AppException {
        try {
            OfferDetailsEmployeeConverter converter = new OfferDetailsEmployeeConverter();
            return converter.convert(offerFacade.findById(id));
        } catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda odpowiada za przypisanie statku do oferty
     * @param assignStarshipToOfferDTO Obiekt DTO zawierający identyfikator statku oraz identyfikator oferty
     * @throws AppException wyjątek aplikacyjny, gdy wystąpi błąd
     * @author Michał Tęgi
     */
    @Override
    @RolesAllowed(assignStarshipToOffer)
    public void assignStarshipToOffer(AssignStarshipToOfferDTO assignStarshipToOfferDTO) throws AppException {
        try {
            Starship starship = starshipFacade.findById(assignStarshipToOfferDTO.getStarshipId());
            starshipFacade.lockPessimisticRead(starship);
            Offer offer = offerFacade.findById(assignStarshipToOfferDTO.getOfferId());
            if(!starship.getOperational()){
                throw StarshipException.createStarshipNotOperationalException();
            }
            if(!offer.getApplicationCollection().isEmpty()){
                throw OfferException.assignStarshipExceptionHasApplications();
            }
            if(offerFacade.checkIfOverlappingForEditing(starship, offer.getFlightStartTime(), offer.getFlightEndTime(), offer.getId())){
                throw StarshipException.createStarshipAlreadyAssignedException();
            }
            if(starship.getId().equals(offer.getStarship().getId())) {
                throw StarshipException.createStarshipSelfAssignedException();
            }
            offer.setStarship(starship);
            offerFacade.edit(offer);
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }
}
