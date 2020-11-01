package pl.lodz.p.it.ssbd2020.ssbd03.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.*;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.accountDetails.AccountDetailsDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.changePassword.ChangePasswordAdminDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.changePassword.ChangePasswordDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.updatedAccountDetails.UpdatedAccountDetailsDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.*;
import pl.lodz.p.it.ssbd2020.ssbd03.mok.services.AccountServiceLocal;
import pl.lodz.p.it.ssbd2020.ssbd03.mok.services.CaptchaService;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.*;

/**
 * Klasa obsługująca zasób rest /accounts
 */
@RequestScoped
@Path("/accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@DeclareRoles({listAccounts, listAccountsReports, displaySomeonesAccountDetails, displayOwnAccountDetails, changeOwnPassword, changeSomeonesPassword,
        editOwnAccount, editSomeonesAccount, changeActiveStatus, addAccessLevel, changeAccessLevel, revokeAccessLevel, sendConfirmationLink})
public class AccountEndpoint {

    @Inject
    AccountServiceLocal accountService;

    @Inject
    CaptchaService captchaService;

    /**
     * Metoda pobiera dane o kontach aby wyświetlić je w liście.
     * W przypadku gdy zostanie podana fraza, przesyła tylko konta zawierające je w imieniu lub nazwisku.
     * @return 200 i JSON zawierający dane kont lub kod 500 jeśli wystąpił błąd
     * @author Jakub Wróbel
     */
    @POST
    @Path("/list")
    @RolesAllowed(listAccounts)
    public Response getAccounts(FilterAccountsPhraseDTO filterAccountsPhraseDTO) {
        try {
            List<AccountDTO> accountDTOS;
            do {
                if(accountService.getCallCounter() >= accountService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                accountDTOS = accountService.getAccountsFiltered(filterAccountsPhraseDTO);
            } while (accountService.isLastTransactionRollback());

            Collections.sort(accountDTOS);
            return Response.status(Response.Status.OK).entity(accountDTOS).build();
        } catch (TransactionException e) {
            return getAccounts(filterAccountsPhraseDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda pobiera dane do raportu logowania dla wszystkich kont
     * @return JSON zawierający raport dla wszystkich kont
     * @author Sergiusz Parchatko
     */
    @GET
    @Path("/listReports")
    @RolesAllowed(listAccountsReports)
    public Response getAccountsReports() {
        try {
            List<AccountReportDTO> accountReportDTOS;
            do {
                if(accountService.getCallCounter() >= accountService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                accountReportDTOS = accountService.getAccountsReports();
            } while (accountService.isLastTransactionRollback());

            Collections.sort(accountReportDTOS);
            return Response.status(Response.Status.OK).entity(accountReportDTOS).build();
        } catch (TransactionException e) {
            return getAccountsReports();
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda pobierająca dane konkretnego konta w celu prezentacji w warstwie widoku
     * @param emailDTO DTO zawierające adres użytkownika, dla którego wyświetlone zostaną dane konta
     * @return JSON zawierający dane konta, 404 jeżeli nie znaleziono danego konta
     * @author Jakub Fornalski
     */
    @POST
    @RolesAllowed(displaySomeonesAccountDetails)
    @Path("/account")
    public Response getSomeoneAccount(EmailDTO emailDTO) {
        try {
            AccountDetailsDTO accountDetailsDTO;
            do {
                if(accountService.getCallCounter() >= accountService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                accountDetailsDTO = accountService.getSomeoneAccount(emailDTO);
            } while (accountService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).entity(accountDetailsDTO).build();
        } catch (TransactionException e) {
            return getSomeoneAccount(emailDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda pobierająca dane własnego konta w celu prezentacji w warstwie widoku
     * @return JSON zawierający dane konta, 404 jeżeli nie znaleziono danego konta
     * @author Jakub Fornalski
     */
    @GET
    @RolesAllowed(displayOwnAccountDetails)
    @Path("/my-account")
    public Response getOwnAccount() {
        try {
            AccountDetailsDTO accountDetailsDTO;
            do {
                if(accountService.getCallCounter() >= accountService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                accountDetailsDTO = accountService.getOwnAccount();
            } while (accountService.isLastTransactionRollback());

            return Response.status(Response.Status.OK).entity(accountDetailsDTO).build();
        } catch (TransactionException e) {
            return getOwnAccount();
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }


    /**
     * Metoda służąca do zmiany własnego hasła przez użytkownika.
     * @param changePasswordDTO obiekt zawierający email, stare hasło oraz nowe hasło użytkownika.
     * @return Kod 200 jeśli link został wysłany, 500 jeśli wystąpił błąd.
     * @author Paweł Wacławiak 216910
     */
    @PATCH
    @Path("/changeOwnPassword")
    @RolesAllowed(changeOwnPassword)
    public Response changeOwnPassword(ChangePasswordDTO changePasswordDTO) {
        if(!accountService.isLastTransactionRollback()){
            try {
                captchaService.validateRequest(changePasswordDTO.getCaptcha());
            } catch (CaptchaException e) {
                Logger.getGlobal().log(Level.SEVERE, e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
            }
        }

        try {
            do {
                if(accountService.getCallCounter() >= accountService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                accountService.changeOwnPassword(changePasswordDTO);
            } while (accountService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return changeOwnPassword(changePasswordDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }


    /**
     * Metoda służąca do zmiany przez administratora, hasła innego użytkownika.
     * @param changePasswordDTO obiekt zawierający email oraz nowe hasło użytkownika.
     * @return Kod 200 jeśli link został wysłany, 500 jeśli wystąpił błąd.
     * @author 216727 Ernest Błaż
     */
    @PATCH
    @Path("/changeSomeonesPassword")
    @RolesAllowed(changeSomeonesPassword)
    public Response changeSomeonesPassword(ChangePasswordAdminDTO changePasswordDTO) {
        try {
            do {
                if(accountService.getCallCounter() >= accountService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                accountService.changeUsersPassword(changePasswordDTO);
            } while (accountService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return changeSomeonesPassword(changePasswordDTO);
        } catch (AppException | MessagingException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }


    /**
     * Metoda odpowiedzialna jest za aktualizacje danych własnego konta na podstawie
     * zawartości przekazanego obiektu DTO
     * @param updatedAccountDetailsDTO obiekt zawierający nowe dane
     * @return kod 200 jeżeli konto zostało zaktualizowane, 500 jeżeli wystąpił błąd
     * @author Sergiusz Parchatko
     */
    @PATCH
    @Path("/editOwnAccount")
    @RolesAllowed(editOwnAccount)
    public Response editOwnAccount(UpdatedAccountDetailsDTO updatedAccountDetailsDTO) {
        if(!accountService.isLastTransactionRollback()) {
            try {
                captchaService.validateRequest(updatedAccountDetailsDTO.getCaptcha());
            } catch (CaptchaException e) {
                Logger.getGlobal().log(Level.SEVERE, e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
            }
        }

        try {
            do {
                if(accountService.getCallCounter() >= accountService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                accountService.updateOwnAccount(updatedAccountDetailsDTO);
            } while (accountService.isLastTransactionRollback());

            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return editOwnAccount(updatedAccountDetailsDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda aktualizuje dane konta użytkownika na podstawie nowych wartości
     * przekazanych w obiekcie DTO
     * @param updatedAccountDetailsDTO obiekt zawierający nowe dane dla konta
     * @return kod 200 jeżeli konto zostało zaktualizowane, 500 jeżeli wystąpił błąd
     * @author Paweł Wacławiak 216910
     */
    @PATCH
    @Path("/editOtherAccount")
    @RolesAllowed(editSomeonesAccount)
    public Response editSomeoneAccount(UpdatedAccountDetailsDTO updatedAccountDetailsDTO) {
        try {
            do {
                if(accountService.getCallCounter() >= accountService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                accountService.updateSomeoneAccount(updatedAccountDetailsDTO);
            } while (accountService.isLastTransactionRollback());

            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return editSomeoneAccount(updatedAccountDetailsDTO);
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
     * Metoda udostępnia endpoint do wyświetlenia czasów ostatniego logowania.
     * @return Kod 200 jeśli czasy zostały wysłane, 500 jeśli wystąpił błąd.
     */
    @GET
    @Path("/loginTimestamps")
    @RolesAllowed(logIn)
    public Response getLoginTimestampsForOwnAccount() {
        try {
            LoginTimestampsDTO loginTimestampsDTO;
            do {
                if(accountService.getCallCounter() >= accountService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                loginTimestampsDTO = accountService.getLoginTimestampsForOwnAccount();
            } while (accountService.isLastTransactionRollback());

            return Response.status(Response.Status.OK).entity(loginTimestampsDTO).build();
        } catch (TransactionException e) {
            return getLoginTimestampsForOwnAccount();
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }


    /**
     * Metoda wysyła email z linkiem umożliwiającym zresetowanie hasła.
     * @param initResetPasswordDTO obiekt zawierający email użytkownika.
     * @return Kod 200 jeśli link został wysłany, 500 jeśli wystąpił błąd.
     * @author Paweł Wacławiak
     */
    @POST
    @Path("/resetPassword")
    @PermitAll
    public Response sendPasswordResetLink(InitResetPasswordDTO initResetPasswordDTO) {
        try {
            do {
                if(accountService.getCallCounter() >= accountService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                accountService.createPasswordResetToken(initResetPasswordDTO);
            } while (accountService.isLastTransactionRollback());

            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return sendPasswordResetLink(initResetPasswordDTO);
        } catch (AppException | MessagingException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }


    /**
     * Metoda zmienia hasło użytkownika na nowe bez konieczności potwierdzania
     * za pomocą dotychczasowego hasła, wymaga jednak tokenu wysyłanego mailem.
     * @param passwordResetDTO obiekt zawierający token zmiany hasła oraz nowe hasło.
     * @return Kod 200 jeśli hasło zostało zmienione, 500 jeśli wystąpił błąd.
     * @author Paweł Wacławiak 216910
     */
    @PATCH
    @Path("/confirmResetPassword")
    @PermitAll
    public Response resetPassword(PasswordResetDTO passwordResetDTO) {
        try {
            do {
                if(accountService.getCallCounter() >= accountService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                accountService.resetPassword(passwordResetDTO);
            } while (accountService.isLastTransactionRollback());

            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return resetPassword(passwordResetDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda tworzy nowe konto użytkownika.
     * @param registerDTO obiekt zawierający dane potrzebne do stworzenia konta czyli
     *                    email, hasło, imię, nazwisko i motto.
     * @return Kod 200 jeśli konto zostało stworzone, 500 jeśli wystąpił błąd.
     * @author Jakub Wróbel
     */
    @POST
    @Path("/createAccount")
    @PermitAll
    public Response createNewAccount(RegisterDTO registerDTO) {
        if(!accountService.isLastTransactionRollback()){
            try {
                captchaService.validateRequest(registerDTO.getCaptcha());
            } catch (CaptchaException e) {
                Logger.getGlobal().log(Level.SEVERE, e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
            }
        }

        try {
            do {
                if(accountService.getCallCounter() >= accountService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                accountService.createNewAccount(registerDTO);
            } while (accountService.isLastTransactionRollback());
            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return createNewAccount(registerDTO);
        } catch (AppException | MessagingException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda potwierdza konto użytkownika.
     * @param activateAccountDTO obiekt zawierający dane potrzebne do potwierdzenia konta
     * @return Kod 200 jeśli konto zostało stworzone, 500 jeśli wystąpił błąd.
     * @author Jakub Wróbel
     */
    @PATCH
    @Path("/activateAccount")
    @PermitAll
    public Response activateAccount(ActivateAccountDTO activateAccountDTO) {        
        try {
            do {
                if(accountService.getCallCounter() >= accountService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                accountService.activateAccount(activateAccountDTO);
            } while (accountService.isLastTransactionRollback());

            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return activateAccount(activateAccountDTO);
        } catch (AppException | MessagingException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda blokuje i odblokowuje konto użytkownika, zmieniając pole active na false lub true.
     * @param blockAccountDTO obiekt zawierający email oraz active.
     * @return Kod 200 jeśli konto zostało pomyślnie odblokowane lub zablokowane, 500 jeśli wystąpił błąd.
     * @author 216727 Ernest Błaż
     */
    @PATCH
    @Path("/block")
    @RolesAllowed(changeActiveStatus)
    public Response changeActiveStatus(BlockAccountDTO blockAccountDTO) {
        try {
            do {
                if(accountService.getCallCounter() >= accountService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                if (!blockAccountDTO.getActive()) {
                    accountService.blockAccount(blockAccountDTO.getEmail());
                } else {
                    accountService.unblockAccount(blockAccountDTO.getEmail());
                }
            } while (accountService.isLastTransactionRollback());


            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return changeActiveStatus(blockAccountDTO);
        } catch (AppException | MessagingException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda wysyła link potwierdzający dane konto użytkownika
     * @param emailDTO obiekt zawierający email użytkownika
     * @return Kod 200 jeśli email został wysłany, 500 jeśli wystąpił błąd.
     * @author Sergiusz Parchatko
     */
    @POST
    @Path("/sendConfirmationLink")
    @RolesAllowed(sendConfirmationLink)
    public Response sendConfirmationLink(EmailDTO emailDTO) {
        try {
            do {
                if(accountService.getCallCounter() >= accountService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                accountService.sendConfirmationLink(emailDTO);

            } while (accountService.isLastTransactionRollback());


            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return sendConfirmationLink(emailDTO);
        } catch (AppException | MessagingException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }


    /**
     * Metoda nadaje użytkownikowi dany poziom dostępu
     * @param accessLevelDTO obiekt zawierający dane potrzebne do nadania poziomu dostępu,
     *                       czyli wybrane konto oraz nazwa pożądanego poziomu dostępu
     * @return Kod 200 jeśli poziom został poprawnie nadany, 500 jeśli wystąpił błąd.
     * @author Jakub Fornalski
     */
    @PATCH
    @Path("/addAccessLevel")
    @RolesAllowed(addAccessLevel)
    public Response addAccessLevel(AccessLevelDTO accessLevelDTO) {
        try {
            do {
                if(accountService.getCallCounter() >= accountService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                accountService.addAccessLevel(accessLevelDTO);
            } while (accountService.isLastTransactionRollback());

            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return addAccessLevel(accessLevelDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }

    /**
     * Metoda rejestruje zdarzenie zmiany poziomu dostępu użytkownika
     * @param changeAccessLevelDTO obiekt zawierający nazwę pożądanego poziomu dostępu
     * @return Kod 200 jeśli zdarzenie zostało zarejestrowane, 500 jeśli wystąpił błąd.
     * @author Michał Tęgi
     */
    @POST
    @Path("/changeAccessLevel")
    @RolesAllowed(changeAccessLevel)
    public Response changeAccessLevel(ChangeAccessLevelDTO changeAccessLevelDTO) {
        try {
            accountService.changeAccessLevel(changeAccessLevelDTO);
            return Response.status(Response.Status.OK).build();
        } catch (MessagingException | AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }


    /**
     * Metoda odbierająca użytkownikowi dany poziom dostępu
     * @param accessLevelDTO obiekt zawierający dane potrzebne do odebrania poziomu dostępu,
     *                       czyli wybrane konto oraz nazwa poziomu dostępu
     * @return Kod 200 jeśli poziom został poprawnie odebrany, 500 jeśli wystąpił błąd.
     * @author Jakub Fornalski
     */
    @PATCH
    @Path("/revokeAccessLevel")
    @RolesAllowed(revokeAccessLevel)
    public Response revokeAccessLevel(AccessLevelDTO accessLevelDTO) {
        try {
            do {
                if(accountService.getCallCounter() >= accountService.getMaxTransactionCount()){
                    throw AppException.createExceptionForRepeatedTransactionRollback();
                }
                accountService.revokeAccessLevel(accessLevelDTO);
            } while (accountService.isLastTransactionRollback());

            return Response.status(Response.Status.OK).build();
        } catch (TransactionException e) {
            return revokeAccessLevel(accessLevelDTO);
        } catch (AppException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(e.getMessage())).build();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }
}
