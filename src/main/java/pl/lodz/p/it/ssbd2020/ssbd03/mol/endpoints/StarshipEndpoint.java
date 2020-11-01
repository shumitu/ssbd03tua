package pl.lodz.p.it.ssbd2020.ssbd03.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.ExceptionDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.StarshipDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.StarshipRequestDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.StarshipStatusDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.ERROR;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.TransactionException;
import pl.lodz.p.it.ssbd2020.ssbd03.mol.services.StarshipServiceLocal;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.*;

/**
 * Klasa obsługująca zasób rest /starships
 */
@RequestScoped
@Path("/starships")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@DeclareRoles({displayStarshipDetails, addStarship, editStarship, changeStarshipActiveStatus, getAllStarships})
public class StarshipEndpoint {

    @Inject
    StarshipServiceLocal starshipService;

    /**
     * Metoda pobiera listę aktywnych statków
     * @return 200 i JSON zawierający dane statków lub kod 500 jeśli wystąpił błąd
     * @author Sergiusz Parchatko
     */
    @GET
    @Path("/listActive")
    @PermitAll
    public Response getActiveStarships() {
        try {
            List<StarshipDTO> starshipDTOS;
            do {
                if (starshipService.getCallCounter() >= starshipService.getMaxTransactionCount()) {
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                starshipDTOS = starshipService.getActiveStarships();
            } while (starshipService.isLastTransactionRollback());
            Collections.sort(starshipDTOS);
            return Response.status(Response.Status.OK).entity(starshipDTOS).build();
        } catch (TransactionException e) {
            return getActiveStarships();
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda pobiera listę wszystkich statków
     * @return 200 i JSON zawierający dane statków lub kod 500 jeśli wystąpił błąd
     * @author Sergiusz Parchatko
     */
    @GET
    @Path("/listAll")
    @RolesAllowed(getAllStarships)
    public Response getAllStarships() {
        try {
            List<StarshipDTO> starshipDTOS;
            do {
                if (starshipService.getCallCounter() >= starshipService.getMaxTransactionCount()) {
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                starshipDTOS = starshipService.getAllStarships();
            } while (starshipService.isLastTransactionRollback());

            Collections.sort(starshipDTOS);
            return Response.status(Response.Status.OK).entity(starshipDTOS).build();
        } catch (TransactionException e) {
            return getAllStarships();
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda pozwalająca na pobranie szczegółów sprawnego statku
     *
     * @param starshipRequestDTO obiekt DTO opisujący statek, zawiera jego indentyfikator
     * @return JSON zawierający szczegóły statku
     * @author Paweł Wacławiak 216910
     */
    @POST
    @Path("/details")
    @PermitAll
    public Response displayOperationalStarshipDetails(StarshipRequestDTO starshipRequestDTO) {
        try {
            StarshipDTO starshipDetailsDTO;
            do {
                if (starshipService.getCallCounter() >= starshipService.getMaxTransactionCount()) {
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                starshipDetailsDTO = starshipService.getOperationalStarshipDetails(starshipRequestDTO);

            } while (starshipService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).entity(starshipDetailsDTO).build();

        } catch (TransactionException e) {
            return displayOperationalStarshipDetails(starshipRequestDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(new ExceptionDTO(e.getMessage())).build();
        }
    }

    /**
     * Metoda pozwalająca na pobranie szczegółów dowolnego statku
     *
     * @param starshipRequestDTO obiekt DTO opisujący statek, zawiera jego indentyfikator
     * @return JSON zawierający szczegóły statku
     * @author Paweł Wacławiak 216910
     */
    @POST
    @Path("/employee/details")
    @RolesAllowed(displayStarshipDetails)
    public Response displayStarshipDetails(StarshipRequestDTO starshipRequestDTO) {
        try {
            StarshipDTO starshipDetailsDTO;
            do {
                if (starshipService.getCallCounter() >= starshipService.getMaxTransactionCount()) {
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                starshipDetailsDTO = starshipService.getStarshipDetails(starshipRequestDTO);

            } while (starshipService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).entity(starshipDetailsDTO).build();

        } catch (TransactionException e) {
            return displayStarshipDetails(starshipRequestDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(new ExceptionDTO(e.getMessage())).build();
        }
    }

    /**
     * Metoda dodaje nowy statek
     * @param starshipDTO obiekt DTO zawierający dane nowego statku
     * @return kod 200 jeśli statek został dodany, kod 500 jeśli wystąpił błąd
     * @author Michał Tęgi
     */
    @POST
    @Path("/add-starship")
    @RolesAllowed(addStarship)
    public Response addStarship(StarshipDTO starshipDTO) {
        try {
            do {
                if (starshipService.getCallCounter() >= starshipService.getMaxTransactionCount()) {
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                starshipService.addStarship(starshipDTO);
            } while (starshipService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return addStarship(starshipDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda odpowiada za edycje istniejącego statku
     * @param starshipDTO obiekt DTO zawierający dane statku do edycji
     * @return kod 200 jeśli edycja się powiodła, kod 500 jeśli wystąpił błąd
     * @author Michał Tęgi
     */
    @PATCH
    @Path("/edit-starship")
    @RolesAllowed(editStarship)
    public Response editStarship(StarshipDTO starshipDTO) {
        try {
            do {
                if (starshipService.getCallCounter() >= starshipService.getMaxTransactionCount()) {
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                starshipService.editStarship(starshipDTO);
            } while (starshipService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return editStarship(starshipDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda odpowiada za aktywacje / dezaktywacje statku
     * @param statusDTO id statku i nowy status aktywności
     * @return kod 200 jeśli status uległ zmianie, kod 500 jeśli wystąpił błąd
     * @author Michał Tęgi
     */
    @PATCH
    @Path("/change-status")
    @RolesAllowed(changeStarshipActiveStatus)
    public Response changeStarshipActiveStatus(StarshipStatusDTO statusDTO) {
        try {
            do {
                if (starshipService.getCallCounter() >= starshipService.getMaxTransactionCount()) {
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                starshipService.changeStarshipActiveStatus(statusDTO);
            } while (starshipService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return changeStarshipActiveStatus(statusDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

}
