package pl.lodz.p.it.ssbd2020.ssbd03.mol.endpoints;


import pl.lodz.p.it.ssbd2020.ssbd03.dto.*;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.offerDetails.OfferDetailsCandidateDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.offerDetails.OfferDetailsEmployeeDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.ConcurrencyException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.ERROR;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.TransactionException;
import pl.lodz.p.it.ssbd2020.ssbd03.mol.services.OfferServiceLocal;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.*;

/**
 * Klasa obsługująca zasób rest /offers
 */
@RequestScoped
@Path("/offers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@DeclareRoles({displayOfferDetailsCandidate, displayOfferDetailsEmployee, changeOfferVisibility, addOffer, editOffer, openOffer, removeOffer, closeOffer, getAllOffers, assignStarshipToOffer})
public class OfferEndpoint {

    @Inject
    OfferServiceLocal offerService;


    /**
     * Metoda zwraca szczegóły danej oferty dla kandydata i gościa
     * @param id identyfikator oferty
     * @return JSON zawierający szczegóły oferty, kod 500 jeśli wystapił błąd
     * @author Michał Tęgi
     */
    @GET
    @Path("/{id}")
    @PermitAll
    public Response displayOfferDetailsCandidate(@PathParam("id") Long id) {
        try {
            OfferDetailsCandidateDTO offerDetailsCandidateDTO;
            do {
                if (offerService.getCallCounter() >= offerService.getMaxTransactionCount()) {
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                offerDetailsCandidateDTO = offerService.getOfferDetailsCandidate(id);
            } while (offerService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).entity(offerDetailsCandidateDTO).build();
        } catch (TransactionException e) {
            return displayOfferDetailsCandidate(id);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda zwraca szczegóły danej oferty dla pracownika i administratora
     * @param id identyfikator oferty
     * @return JSON zawierający szczegóły oferty, kod 500 jeśli wystapił błąd
     * @author Michał Tęgi
     */
    @GET
    @Path("/employee/{id}")
    @RolesAllowed(displayOfferDetailsEmployee)
    public Response displayOfferDetailsEmployee(@PathParam("id") Long id) {
        try {
            OfferDetailsEmployeeDTO offerDetailsEmployeeDTO;
            do {
                if (offerService.getCallCounter() >= offerService.getMaxTransactionCount()) {
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                offerDetailsEmployeeDTO = offerService.getOfferDetailsEmployee(id);
            } while (offerService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).entity(offerDetailsEmployeeDTO).build();
        } catch (TransactionException e) {
            return displayOfferDetailsCandidate(id);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda pobiera dane o wszystkich ofertach aby wyświetlić je w liście.
     * @return 200 i JSON zawierający dane ofert lub kod 500 jeśli wystąpił błąd
     * @author Jakub Wróbel
     */
    @GET
    @Path("/listAll")
    @RolesAllowed(getAllOffers)
    public Response listAllOffers() {
        try {
            List<OfferForAllListDTO> offerForAllListDTOS;
            do {
                if(offerService.getCallCounter() >= offerService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                offerForAllListDTOS = offerService.getAllOffers();
            } while (offerService.isLastTransactionRollback());

            Collections.sort(offerForAllListDTOS);
            return Response.status(Response.Status.OK).entity(offerForAllListDTOS).build();
        }
        catch (TransactionException e) {
            return listAllOffers();
        }
        catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda pobiera dane o aktywnych ofertach aby wyświetlić je w liście.
     * @return 200 i JSON zawierający dane ofert lub kod 500 jeśli wystąpił błąd
     * @author Jakub Wróbel
     */
    @GET
    @Path("/listActive")
    @PermitAll
    public Response listActiveOffers() {
        try {
            List<OfferForActiveListDTO> offerForActiveListDTOS;
            do {
                if(offerService.getCallCounter() >= offerService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                offerForActiveListDTOS = offerService.getActiveOffers();
            } while (offerService.isLastTransactionRollback());

            Collections.sort(offerForActiveListDTOS);
            return Response.status(Response.Status.OK).entity(offerForActiveListDTOS).build();
        }
        catch (TransactionException e) {
            return listActiveOffers();
        }
        catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda dodająca nową ofertę
     * @param offerDTO obiekt zawierający dane potrzebne do stworzenia oferty
     * @author Sergiusz Parchatko
     * @return Kod 200 jeśli oferta została stworzona, 500 jeśli wystąpił błąd.
     */
    @POST
    @Path("/addOffer")
    @RolesAllowed(addOffer)
    public Response addOffer(OfferDTO offerDTO) {
        try {
            do {
                if(offerService.getCallCounter() >= offerService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                offerService.addOffer(offerDTO);
            } while (offerService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return addOffer(offerDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda usuwa istniejącą ofertę
     * @param removeOfferDTO obiekt zawierający informacje o tym jaką ofertę należy usunąć
     * @return Kod 200 jeśli oferta została usunięta, 500 jeśli wystąpił błąd.
     * @author Jakub Wróbel
     */
    @POST
    @Path("/remove")
    @RolesAllowed(removeOffer)
    public Response removeOffer(RemoveOfferDTO removeOfferDTO){
        try {
            do {
                if(offerService.getCallCounter() >= offerService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                offerService.removeOffer(removeOfferDTO);
            } while (offerService.isLastTransactionRollback());

            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return removeOffer(removeOfferDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }


    /**
     * Metoda edytująca dane oferty
     * @param updatedOfferDetailsDTO obiekt zawierające zaktualizowane dane oferty
     * @return kod 200 jeżeli oferta została zaktualizowana, 500 jeżeli wystąpił błąd
     * @author Sergiusz Parchatko
     */
    @PATCH
    @Path("/editOffer")
    @RolesAllowed(editOffer)
    public Response editOffer(UpdatedOfferDetailsDTO updatedOfferDetailsDTO) {
        try {
            do {
                if(offerService.getCallCounter() >= offerService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                offerService.editOffer(updatedOfferDetailsDTO);
            } while (offerService.isLastTransactionRollback());

            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return editOffer(updatedOfferDetailsDTO);
        } catch (ConcurrencyException e) {
            return Response.status(Response.Status.PRECONDITION_FAILED).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }
    
    /**
     * Metoda otwierająca zapisy do oferty lotu
     * 
     * @param idDTO obiekt DTO zawierający identyfikator oferty do otwarcia
     * @return kod 200 jeżeli oferta została zaktualizowana, 500 jeżeli wystąpił błąd
     * @author Paweł Wacławiak 216910
     */
    @PATCH
    @Path("/openOffer")
    @RolesAllowed(openOffer)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response openOffer(@Valid IdDTO idDTO) {
        try {
            do {
                if (offerService.getCallCounter() >= offerService.getMaxTransactionCount()) {
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                offerService.openOffer(idDTO.getId());
                
            } while (offerService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).build();
            
        } catch (TransactionException e) {
            return openOffer(idDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        }
    }

    /**
     * Metoda zamyka zapisy do oferty lotu
     *
     * @param offerDTO obiekt DTO zawierający informacje o ofercie do zamknięcia
     * @return kod 200 jeżeli oferta została zaktualizowana, 500 jeżeli wystąpił błąd
     * @author Jakub Wróbel
     */
    @PATCH
    @Path("/closeOffer")
    @RolesAllowed(closeOffer)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response closeOffer(IdDTO offerDTO) {
        try {
            do {
                if (offerService.getCallCounter() >= offerService.getMaxTransactionCount()) {
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                offerService.closeOffer(offerDTO.getId());

            } while (offerService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).build();

        } catch (TransactionException e) {
            return closeOffer(offerDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda pozwalająca zmienić status widoczności wybranej oferty
     * @param offerVisibilityDTO DTO zawierające informacje o wybranej ofercie i jej pożądanym stanie widoczności
     * @return Kod 200 jeśli operacja się powiodła, kod 500 jeśli doszło do błędu
     * @author Jakub Fornalski
     */
    @PATCH
    @Path("/visibility")
    @RolesAllowed(changeOfferVisibility)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeActiveStatus(OfferVisibilityDTO offerVisibilityDTO) {

        try {
            do {
                if (offerService.getCallCounter() >= offerService.getMaxTransactionCount()) {
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                if (!offerVisibilityDTO.isVisible()) {
                    offerService.hideOffer(offerVisibilityDTO.getId());
                } else {
                    offerService.showOffer(offerVisibilityDTO.getId());
                }
            } while (offerService.isLastTransactionRollback());


            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return changeActiveStatus(offerVisibilityDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda odpowiada za przypisanie statku do oferty
     * @param assignStarshipToOfferDTO Obiekt DTO zawierający identyfikator statku oraz identyfikator oferty
     * @return Kod 200 jeśli operacja się powiodła, kod 500 jeśli wystąpił błąd
     * @author Michał Tęgi
     */
    @PATCH
    @Path("/assign-starship")
    @RolesAllowed(assignStarshipToOffer)
    public Response assignStarshipToOffer(AssignStarshipToOfferDTO assignStarshipToOfferDTO) {
        try {
            do {
                if (offerService.getCallCounter() >= offerService.getMaxTransactionCount()) {
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                offerService.assignStarshipToOffer(assignStarshipToOfferDTO);
            } while (offerService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return assignStarshipToOffer(assignStarshipToOfferDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }
}
