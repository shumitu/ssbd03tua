package pl.lodz.p.it.ssbd2020.ssbd03.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.*;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.ERROR;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.TransactionException;
import pl.lodz.p.it.ssbd2020.ssbd03.mol.services.ApplicationServiceLocal;

import javax.annotation.security.DeclareRoles;
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


@RequestScoped
@Path("/applications")
@DeclareRoles({listApplications, listOwnApplications, addApplicationToCategory, editApplication,
        cancelApplication, checkReview, checkMyReview, displayApplicationDetails, displayMyApplicationDetails, applyToOffer})
public class ApplicationEndpoint {

    @Inject
    ApplicationServiceLocal applicationService;

    /**
     * Metoda pobiera pełną listę zgłoszeń do danej oferty
     *
     * @param idDTO DTO z id oferty, dla której ma zostać pobrana lista zgłoszeń
     * @return JSON zawierający listę zgłoszeń do danej oferty lub kod 500 jeśli pobranie listy się nie powiedzie
     * @author Jakub Fornalski
     */
    @POST
    @Path("/listApplications")
    @RolesAllowed({listApplications})
    public Response listApplications(IdDTO idDTO) {
        try {
            List<ApplicationListedDTO> applicationDTOList;
            do {
                if (applicationService.getCallCounter() >= applicationService.getMaxTransactionCount()) {
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                applicationDTOList = applicationService.getOfferApplicationsList(idDTO.getId());
            } while (applicationService.isLastTransactionRollback());

            Collections.sort(applicationDTOList);
            return Response.status(Response.Status.OK).entity(applicationDTOList).build();
        } catch (TransactionException e) {
            return listApplications(idDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda pobiera pełną listę zgłoszeń aktualnie zalogowanego użytkownika
     *
     * @return JSON zawierający listę zgłoszeń aktualnie zalogowanego użytkownika
     * lub kod 500 jeśli pobranie listy się nie powiedzie
     * @author Jakub Fornalski
     */
    @GET
    @Path("/listOwnApplications")
    @RolesAllowed({listOwnApplications})
    public Response listOwnApplications() {
        try {
            List<ApplicationCandidateListedDTO> applicationDTOList;
            do {
                if (applicationService.getCallCounter() >= applicationService.getMaxTransactionCount()) {
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                applicationDTOList = applicationService.getOwnApplicationsList();
            } while (applicationService.isLastTransactionRollback());

            Collections.sort(applicationDTOList);
            return Response.status(Response.Status.OK).entity(applicationDTOList).build();
        } catch (TransactionException e) {
            return listOwnApplications();
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda dodaje zgłoszenie kandydata na ofertę lotu.
     *
     * @param applicationDTO obiekt DTO zwierający informacje o zgłoszeniu
     * @return Kod 200 jeśli operacja się powiodła, kod 500 jeśli doszło do błędu
     * @author Paweł Wacławiak 216910
     */
    @POST
    @Path("/applyToOffer")
    @RolesAllowed({applyToOffer})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response applyToOffer(@Valid NewApplicationDTO applicationDTO) {
        try {
            do {
                if (applicationService.getCallCounter() >= applicationService.getMaxTransactionCount()) {
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                applicationService.createNewApplication(applicationDTO);
            } while (applicationService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).build();

        } catch (TransactionException e) {
            return applyToOffer(applicationDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda powoduje przypisania wybranego zgłoszenia do wskazanej kategorii
     *
     * @param applicationCategoryDTO DTO zawierające id zgłoszenia i nazwę kategori,
     *                               do której ma ono zostać przypisane
     * @return Kod 200 jeśli operacja się powiodła, kod 500 jeśli doszło do błędu
     * @author Jakub Fornalski
     */
    @POST
    @Path("/addToCategory")
    @RolesAllowed(addApplicationToCategory)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addToCategory(ApplicationCategoryDTO applicationCategoryDTO) {
        try {
            do {
                if (applicationService.getCallCounter() >= applicationService.getMaxTransactionCount()) {
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                applicationService.addApplicationToCategory(applicationCategoryDTO);
            } while (applicationService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return addToCategory(applicationCategoryDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }



    /**
     * Metoda odpowiedzialna jest za edycję własnego zgłoszenia
     *
     * @param updateApplicationDTO jest to obiekt DTO zawierający wagę kandydata,
     *                              numer badania lekarskiego,
     *                              a także odnośnik do listu motywacyjnego kandydata.
     * @return Kod 200 jeśli operacja się powiodła, kod 500 jeśli doszło do błędu
     *
     * @author 216727 Ernest Błaż
     */

    @PATCH
    @Path("/edit")
    @RolesAllowed(editApplication)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editApplication(UpdateApplicationDTO updateApplicationDTO) {
        try {
            do {
                if (applicationService.getCallCounter() >= applicationService.getMaxTransactionCount()) {
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                applicationService.updateApplication(updateApplicationDTO);
            } while (applicationService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return editApplication(updateApplicationDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda pobiera status zgłoszenia w celu wyświetlenia go w odpowiednim komponencie, należącym do widoku.
     *
     * @param idDTO jest to obiekt DTO zawierający identyfikator zgłoszenia,
     *                      którego stan chcemy wyświetlić
     * @return JSON zawierający stan zgłoszenia, 404 jeżeli nie znaleziono stanu zgłoszenia
     *
     * @author 216727 Ernest Błaż
     */

    @POST
    @Path("/getReview")
    @RolesAllowed(checkReview)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getReview(IdDTO idDTO) {
        try {
            ReviewDTO reviewDTO;
            do {
                if(applicationService.getCallCounter() >= applicationService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                reviewDTO = applicationService.getReview(idDTO);
            } while (applicationService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).entity(reviewDTO).build();
        } catch (TransactionException e) {
            return getReview(idDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda pobiera status zgłoszenia w celu wyświetlenia go w odpowiednim komponencie, należącym do widoku.
     *
     * @param idDTO jest to obiekt DTO zawierający identyfikator zgłoszenia,
     *                      którego stan chcemy wyświetlić
     * @return JSON zawierający stan zgłoszenia, 404 jeżeli nie znaleziono stanu zgłoszenia
     *
     * @author 216727 Ernest Błaż
     */

    @POST
    @Path("/getMyReview")
    @RolesAllowed(checkMyReview)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getMyReview(IdDTO idDTO) {
        try {
            ReviewDTO reviewDTO;
            do {
                if(applicationService.getCallCounter() >= applicationService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                reviewDTO = applicationService.getMyReview(idDTO);
            } while (applicationService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).entity(reviewDTO).build();
        } catch (TransactionException e) {
            return getMyReview(idDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda służąca do anulowania własnego zgłoszenia.
     * @return Kod 200 jeśli operacja się powiodła, kod 500 jeśli doszło do błędu
     *
     * @author 216727 Ernest Błaż
     */

    @POST
    @Path("/cancel")
    @RolesAllowed(cancelApplication)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cancelApplication(IdDTO idDTO) {
        try {
            do {
                if (applicationService.getCallCounter() >= applicationService.getMaxTransactionCount()) {
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                applicationService.cancelApplication(idDTO);
            } while (applicationService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return cancelApplication(idDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda służąca do wyświetlenia szczegółów zgłoszenia
     *
     * @param idDTO jest to obiekt DTO zawierający identyfikator zgłoszenia,
     *                      którego stan chcemy wyświetlić
     * @return JSON zawierający szczegóły zgłoszenia, kod 500 jeśli doszło do błędu
     *
     * @author 216727 Ernest Błaż
     */

    @POST
    @Path("/getApplication")
    @RolesAllowed(displayApplicationDetails)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response displayApplicationDetails(IdDTO idDTO) {
        try {
            ApplicationDTO applicationDTO;
            do {
                if(applicationService.getCallCounter() >= applicationService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                applicationDTO = applicationService.displayApplicationDetails(idDTO);
            } while (applicationService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).entity(applicationDTO).build();
        } catch (TransactionException e) {
            return displayApplicationDetails(idDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda służąca do wyświetlenia szczegółów zgłoszenia
     *
     * @param idDTO jest to obiekt DTO zawierający identyfikator zgłoszenia,
     *                      którego stan chcemy wyświetlić
     * @return JSON zawierający szczegóły zgłoszenia, kod 500 jeśli doszło do błędu
     *
     * @author 216727 Ernest Błaż
     */

    @POST
    @Path("/getMyApplication")
    @RolesAllowed(displayMyApplicationDetails)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getMyApplication(IdDTO idDTO) {
        try {
            ApplicationDTO applicationDTO;
            do {
                if(applicationService.getCallCounter() >= applicationService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                applicationDTO = applicationService.getMyApplication(idDTO);
            } while (applicationService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).entity(applicationDTO).build();
        } catch (TransactionException e) {
            return getMyApplication(idDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }
}
