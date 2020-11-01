package pl.lodz.p.it.ssbd2020.ssbd03.mok.services;

import pl.lodz.p.it.ssbd2020.ssbd03.core.AbstractService;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.*;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.accountDetails.AccountDetailsDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.accountDetails.CandidateDetailsDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.accountDetails.EmployeeDetailsDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.changePassword.ChangePasswordAdminDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.changePassword.ChangePasswordDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.converters.AccountConverter;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.converters.AccountDetailsConverter;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.converters.AccountReportsConverter;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.updatedAccountDetails.UpdatedAccountDetailsDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.*;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.*;
import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd03.mok.facades.*;
import pl.lodz.p.it.ssbd2020.ssbd03.security.services.HashService;
import pl.lodz.p.it.ssbd2020.ssbd03.security.services.HmacSigner;
import pl.lodz.p.it.ssbd2020.ssbd03.security.services.ITokenService;
import pl.lodz.p.it.ssbd2020.ssbd03.utils.AccountValidator;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.mail.MessagingException;
import javax.persistence.PersistenceException;
import javax.security.enterprise.SecurityContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.*;

/**
 * Stanowy komponent EJB, serwis dla kont
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
public class AccountService extends AbstractService implements AccountServiceLocal, SessionSynchronization {

    @Inject
    PasswordResetTokenFacadeLocal passwordResetTokenFacade;
    @Inject
    AccountFacadeLocal accountFacade;
    @Inject
    LoginInfoFacadeLocal loginInfoFacade;
    @Inject
    AccessLevelFacadeLocal accessLevelFacade;
    @Inject
    EmployeeFacadeLocal employeeFacade;
    @Inject
    CandidateFacadeLocal candidateFacade;
    @Inject
    AdminFacadeLocal adminFacade;
    @Inject
    MailService mailService;
    @Inject
    ITokenService tokenService;
    @Inject
    HashService hashService;
    @Inject
    SecurityContext securityContext;
    @Inject
    HttpServletRequest request;

    @Resource(name = "TOKEN_VALIDITY_MINUTES")
    private int TOKEN_VALIDITY_MINUTES;

    @Resource(name = "UNSUCCESSFUL_LOGIN_COUNT")
    private int UNSUCCESSFUL_LOGIN_COUNT;


    /**
     * Metoda pobiera dane o kontach zawierających w danych podaną frazę, aby wyświetlić je w liście.
     * @param filterAccountsPhraseDTO DTO zawierające frazę do filtrowania kont
     * @return JSON zawierający dane kont.
     * @throws AppException wyjątek aplikacyjny
     * @author Jakub Wróbel
     */
    @RolesAllowed(listAccounts)
    @Override
    public List<AccountDTO> getAccountsFiltered(FilterAccountsPhraseDTO filterAccountsPhraseDTO) throws AppException{
        try {
            List<AccountDTO> accountDTOs = new ArrayList<>();
            AccountConverter converter = new AccountConverter();
            List<Account> accountList;
            if(filterAccountsPhraseDTO.getFilterPhrase() == null ||
                    filterAccountsPhraseDTO.getFilterPhrase().isBlank()){
                accountList = accountFacade.findAll();
            }
            else {
                if(!AccountValidator.isFilterPhaseValid(filterAccountsPhraseDTO.getFilterPhrase())) {
                    throw AccountException.searchExceptionIncorrectPhrase();
                }
                accountList = accountFacade.findPhraseInName(filterAccountsPhraseDTO.getFilterPhrase());
            }
            for (Account account : accountList) {
                accountDTOs.add(converter.convert(account));
            }
            return accountDTOs;
        }
        catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda pobiera dane do raportu logowania dla wszystkich kont
     * @return lista pojedynczych raportów
     * @throws AppException wyjątek aplikacyjny
     * @author Sergiusz Parchatko
     */
    @RolesAllowed(listAccountsReports)
    @Override
    public List<AccountReportDTO> getAccountsReports() throws AppException {
        try {
            List<AccountReportDTO> accountReportDTOs = new ArrayList<>();
            AccountReportsConverter accountReportsConverter = new AccountReportsConverter();
            for (Account account : accountFacade.findAll()) {
                accountReportDTOs.add(accountReportsConverter.convert(account));
            }
            return accountReportDTOs;
        } catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda pobiera dane własnego konta w celu wyświetlenia ich
     * w odpowiednim komponencie, należącym do widoku.
     * @throws AppException wyjątek aplikacyjny związany z niepowodzeniem pobrania
     * informacji o koncie z bazy danych
     * @return obiekt DTO zawierający szczegóły konta
     * @author Jakub Fornalski
     */
    @RolesAllowed(displayOwnAccountDetails)
    @Override
    public AccountDetailsDTO getOwnAccount() throws AppException {
        try {
            Account account = accountFacade.findByEmail(securityContext.getCallerPrincipal().getName());
            AccountDetailsConverter accountDetailsConverter = new AccountDetailsConverter();
            List<AccessLevel> accessLevels = accessLevelFacade.findByAccount(account);
            AccountDetailsDTO accountDetailsDTO = accountDetailsConverter.convert(account);

            List<AccessLevel> filtered = accessLevels.stream()
                    .filter(AccessLevel::getActive)
                    .collect(Collectors.toList());

            for (AccessLevel accessLevel : filtered) {
                if (accessLevel.getLevel().equals("EMPLOYEE")) {
                    Employee employee = employeeFacade.findById(accessLevel.getId());
                    accountDetailsDTO.setEmployee(new EmployeeDetailsDTO(employee));
                }
                if (accessLevel.getLevel().equals("CANDIDATE")) {
                    Candidate candidate = candidateFacade.findById(accessLevel.getId());
                    accountDetailsDTO.setCandidate(new CandidateDetailsDTO(candidate));
                }
            }

            return accountDetailsDTO;
        } catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda pobiera wymagane dane pojedynczego konta w celu wyświetlenia ich
     * w odpowiednim komponencie, należącym do widoku.
     * @param emailDTO  DTO zawierający email użytkownika, dla którego chcemy pobrać dane.
     * @throws AppException wyjątek aplikacyjny związany z niepowodzeniem pobrania
     * informacji o koncie z bazy danych
     * @return obiekt DTO zawierający szczegóły konta
     * @author Jakub Fornalski
     */
    @RolesAllowed(displaySomeonesAccountDetails)
    @Override
    public AccountDetailsDTO getSomeoneAccount(EmailDTO emailDTO) throws AppException {
        try {
            Account account = accountFacade.findByEmail(emailDTO.getEmail());
            AccountDetailsConverter accountDetailsConverter = new AccountDetailsConverter();
            List<AccessLevel> accessLevels = accessLevelFacade.findByAccount(account);
            AccountDetailsDTO accountDetailsDTO = accountDetailsConverter.convert(account);

            List<AccessLevel> filtered = accessLevels.stream()
                    .filter(AccessLevel::getActive)
                    .collect(Collectors.toList());

            for (AccessLevel accessLevel : filtered) {
                if (accessLevel.getLevel().equals("EMPLOYEE")) {
                    Employee employee = employeeFacade.findById(accessLevel.getId());
                    accountDetailsDTO.setEmployee(new EmployeeDetailsDTO(employee));
                }
                if (accessLevel.getLevel().equals("CANDIDATE")) {
                    Candidate candidate = candidateFacade.findById(accessLevel.getId());
                    accountDetailsDTO.setCandidate(new CandidateDetailsDTO(candidate));
                }
            }

            return accountDetailsDTO;
        } catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }


    /**
     * Metoda służąca do zmiany własnego hasła przez użytkownika
     * @param changePasswordDTO jest to obiekt DTO zawierający email użytkownika,
     * a także podane przez niego obecne i nowe hasło
     * @throws AppException wyjątek aplikacyjny rzucany gdy podane hasło nie jest
     * zgodne z otrzymanym z encji lub gdy nowe hasło jest identyczne z obecnym
     * @author Paweł Wacławiak 216910
     */
    @RolesAllowed(changeOwnPassword)
    @Override
    public void changeOwnPassword(ChangePasswordDTO changePasswordDTO) throws AppException {
        try {
            Account account = accountFacade.findByEmail(securityContext.getCallerPrincipal().getName());

            String enteredOldPassword = changePasswordDTO.getOldPassword();
            if(!AccountValidator.isPasswordLengthValid(enteredOldPassword)) {
                throw ChangePasswordException.createExceptionOldPasswordTooShort();
            }

            try {
                String enteredPasswordHash = hashService.SHA256(enteredOldPassword);
                if (!enteredPasswordHash.equals(account.getPassword())) {
                    throw ChangePasswordException.createWrongPasswordException();
                }
            } catch (Exception e) {
                throw ChangePasswordException.createWrongPasswordException();
            }

            changePassword(changePasswordDTO.getNewPassword(), account);
            account.setPasswordChangeRequired(false);
            accountFacade.edit(account);
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }


    /**
     * Metoda służąca do zmiany przez administratora, hasła innego użytkownika
     * @param changePasswordDTO jest to obiekt DTO zawierający email użytkownika,
     * a także nowe hasło.
     * @throws AppException wyjątek aplikacyjny rzucany gdy administrator próbuje
     * zmienić z pomocą tej metody własne konto bez autoryzacji
     * lub gdy nowe hasło jest identyczne z obecnym
     * @throws MessagingException wyjątek związany z wysyłaniem wiadomości email
     * @author 216727 Ernest Błaż
     */
    @RolesAllowed(changeSomeonesPassword)
    @Override
    public void changeUsersPassword(ChangePasswordAdminDTO changePasswordDTO) throws AppException, MessagingException {
        if( changePasswordDTO.getEmail().equals(securityContext.getCallerPrincipal().getName()) ) {
            throw ChangePasswordException.createExceptionCantChangeOwn();
        }
        try {
            Account account = accountFacade.findByEmail(changePasswordDTO.getEmail());
            changePassword(changePasswordDTO.getNewPassword(), account);
            account.setPasswordChangeRequired(true);
            accountFacade.edit(account);
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
        mailService.sendPasswordChangedMessage(changePasswordDTO.getEmail(), changePasswordDTO.getNewPassword());
    }


    /**
     * Metoda pomocnicza wykorzystywana zarówno przy zmianie własnego hasła,
     * jak i hasła innego użytkownika przez administratora
     * @param newPassword nowe hasło dla konta
     * @param account objekt encji konta którego hasło ma zostać zmienione
     * @throws AppException wyjątek aplikacyjny rzucany gdy nowe hasło jest
     * zbyt krótkie, bądż gdy jest identyczne z obecnym
     * @author Paweł Wacławiak 216910
     */
    private void changePassword(String newPassword, Account account) throws AppException {
        if(!AccountValidator.isPasswordLengthValid(newPassword)) {
            throw ChangePasswordException.createExceptionNewPasswordTooShort();
        }

        String newPasswordHash = hashService.SHA256(newPassword);

        if( account.getPassword().equals(newPasswordHash) ) {
            throw ChangePasswordException.createExceptionPasswordIsTheSame();
        }

        account.setPassword(newPasswordHash);
        accountFacade.edit(account);
    }


    /**
     * Metoda odpowiedzialna jest za aktualizacje danych własnego konta na podstawie
     * zawartości przekazanego obiektu DTO
     * @param updatedAccountDetailsDTO obiekt zawierający nowe dane konta
     * @throws AppException wyjątek aplikacyjny
     * @author Sergiusz Parchatko
     */
    @RolesAllowed(editOwnAccount)
    @Override
    public void updateOwnAccount(UpdatedAccountDetailsDTO updatedAccountDetailsDTO) throws AppException {
        try {
            Account account = accountFacade.findByEmail(securityContext.getCallerPrincipal().getName());
            if (HmacSigner.verifyEtag(updatedAccountDetailsDTO.getEtag(), account.getVersion())) {
                updateAccountParams(updatedAccountDetailsDTO, account);
            } else {
                throw ConcurrencyException.createResourceModifiedException();
            }
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda aktualizuje dane konta użytkownika na podstawie nowych wartości
     * przekazanych w obiekcie DTO
     * @param updatedAccountDetailsDTO obiekt zawierający nowe dane konta
     * @throws AppException wyjątek aplikacyjny
     * @author Paweł Wacławiak 216910
     */
    @RolesAllowed(editSomeonesAccount)
    @Override
    public void updateSomeoneAccount(UpdatedAccountDetailsDTO updatedAccountDetailsDTO) throws AppException {
        try {
            Account account = accountFacade.findByEmail(updatedAccountDetailsDTO.getEmail());
            if (HmacSigner.verifyEtag(updatedAccountDetailsDTO.getEtag(), account.getVersion())) {
                updateAccountParams(updatedAccountDetailsDTO, account);
            } else {
                throw ConcurrencyException.createResourceModifiedException();
            }
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda pobiera czasy ostatnich logowań.
     * @return Kod 200 jeśli czasy zostały wysłane, 500 jeśli wystąpił błąd.
     */
    @RolesAllowed(logIn)
    @Override
    public LoginTimestampsDTO getLoginTimestampsForOwnAccount() throws AppException {
        try {
            Account account = accountFacade.findByEmail(securityContext.getCallerPrincipal().getName());
            return new LoginTimestampsDTO(account.getLoginInfo().getLastSuccessfulLogin(), account.getLoginInfo().getLastUnsuccessfulLogin());
        } catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda ustawia znacznik ostatniego poprawnego logowania
     * @param email email konta dla którego ustawiamy znacznik
     * @throws AppException wyjątek aplikacyjny
     */
    @PermitAll
    @Override
    public void handleSuccessfulLogin(String email) throws AppException {
        try {
            Account account = accountFacade.findByEmail(email);
            LoginInfo loginInfo = loginInfoFacade.findByAccount(account);
            loginInfo.setLastSuccessfulLogin(new Date());
            loginInfo.setLoginAttemptCounter(0);
            String userIpAddress = request.getRemoteAddr();
            if (!AccountValidator.isIpAddressValid(userIpAddress)) {
                throw LoginInfoException.createExceptionWrongIpAddress();
            }
            loginInfo.setIpAddress(userIpAddress);
            loginInfoFacade.edit(loginInfo);

            Logger.getGlobal().log(Level.INFO, "Successful login, Idenity: " + email + " IP Address: " + loginInfo.getIpAddress());
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda ustawia znacznik ostatniego niepoprawnego logowania
     * @param email email konta dla którego ustawiamy znacznik
     * @throws AppException wyjątek aplikacyjny
     */
    @PermitAll
    @Override
    public void handleUnsuccessfulLogin(String email) throws AppException {
        try {
            if (!AccountValidator.isEmailValid(email)) {
                throw AccountException.createExceptionIncorrectEmail();
            }
            Account account = accountFacade.findByEmail(email);
            LoginInfo loginInfo = loginInfoFacade.findByAccount(account);
            loginInfo.setLastUnsuccessfulLogin(new Date());
            loginInfo.incrementLoginAttemptCounter();
            loginInfoFacade.edit(loginInfo);

            if (loginInfo.getLoginAttemptCounter() == UNSUCCESSFUL_LOGIN_COUNT) {
                account.setActive(false);
                accountFacade.edit(account);
                try {
                    mailService.sendAutomaticBlockMessage(account.getEmail());
                } catch (MessagingException e) {
                    Logger.getGlobal().log(Level.SEVERE, e.getMessage());
                }
            }
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda tworzy encję tokenu zmiany hasła w bazie oraz wysyła link
     * umożliwiający zmianę hasła na otrzymany w obiekcie DTO adres email.
     * @param initResetPasswordDTO obiekt zawierający adres email użytkownika.
     * @throws AppException wyjątek aplikacyjny
     * @throws MessagingException wyjątek związany z wysyłaniem wiadomości email
     * @author Paweł Wacławiak 216910
     */
    @PermitAll
    @Override
    public void createPasswordResetToken(InitResetPasswordDTO initResetPasswordDTO) throws AppException, MessagingException {
        String email = initResetPasswordDTO.getEmail();
        if (!AccountValidator.isEmailValid(email)) {
            throw AccountException.createExceptionIncorrectEmail();
        }
        String token = tokenService.generatePasswordResetToken();
        String hashedToken = hashService.SHA256(token);

        try {
            Account account = accountFacade.findByEmail(email);
            if (!account.getConfirmed()) {
                throw AccountException.createExceptionAccountNotConfirmed();
            }
            if (passwordResetTokenFacade.checkIfTokenExistForAccount(account)) {
                int deletedRecords = passwordResetTokenFacade.deleteExistingTokensForAccount(account);
                LOGGER.log(Level.INFO, "Deleted {0} records from {1}.", new Object[]{deletedRecords, "password_reset_token"});
            }
            long currentTime = new Date().getTime();
            Date expirationTime = new Date(currentTime + TimeUnit.MINUTES.toMillis(TOKEN_VALIDITY_MINUTES));
            PasswordResetToken passwordResetToken = new PasswordResetToken(hashedToken, expirationTime, account);
            passwordResetTokenFacade.create(passwordResetToken);
        } catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
        mailService.sendPasswordResetLink(email, hashedToken, initResetPasswordDTO.getLocale());
    }


    /**
     * Metoda zmienia hasło użytkownika na nowe bez konieczności potwierdzania
     * za pomocą dotychczasowego hasła, wymaga jednak tokenu wysyłanego mailem.
     * @param passwordResetDTO obiekt zawierający token zmiany hasła oraz nowe hasło.
     * @throws AppException wyjątek aplikacyjny
     * @author Paweł Wacławiak 216910
     */
    @PermitAll
    @Override
    public void resetPassword(PasswordResetDTO passwordResetDTO) throws AppException {
        try {
            String token = passwordResetDTO.getToken();
            PasswordResetToken resetToken = passwordResetTokenFacade.findByToken(token);
            Date now = new Date();
            if (resetToken.getExpirationTime().after(now)) {
                Account account = resetToken.getAccount();
                String password = passwordResetDTO.getPassword();
                if (!AccountValidator.isPasswordLengthValid(password)) {
                    throw ChangePasswordException.createExceptionNewPasswordTooShort();
                }
                account.setPassword(hashService.SHA256(password));
                account.setPasswordChangeRequired(false);
                accountFacade.edit(account);
                passwordResetTokenFacade.deleteExistingTokensForAccount(account);
            } else {
                throw PasswordResetTokenException.createTokenExpiredException();
            }
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }

    }

    /**
     * Metoda tworzy nowe konto
     * @param registerDTO obiekt zawierający dane potrzebne do rejestracji.
     * @throws AppException wyjątek aplikacyjny rzucany gdy tworzenie konta się nie powiedzie
     * @throws MessagingException wyjątek aplikacyjny rzucany gdy wysyłanie maila aktywacyjnego nie powiedzie się
     * @author Jakub Wróbel
     */
    @PermitAll
    @Override
    public void createNewAccount(RegisterDTO registerDTO) throws AppException, MessagingException {
        String password = registerDTO.getPassword();

        if (!AccountValidator.isPasswordLengthValid(password)) {
            throw AccountException.createExceptionPasswordTooShort();
        }

        if (!AccountValidator.isEmailValid(registerDTO.getEmail())) {
            throw AccountException.createExceptionIncorrectEmail();
        }

        if(!AccountValidator.isFirstNameValid(registerDTO.getFirstName())) {
                throw AccountException.createExceptionIncorrectFirstName();
        }

        if(!AccountValidator.isLastNameValid(registerDTO.getLastName())) {
            throw AccountException.createExceptionIncorrectLastName();
        }

        if(!AccountValidator.isMottoValid(registerDTO.getMotto())) {
            throw AccountException.createExceptionIncorrectMotto();
        }

        password = hashService.SHA256(password);

        Account account = new Account(password, registerDTO.getEmail(), registerDTO.getMotto(),
                false, true, true);
        LoginInfo loginInfo = new LoginInfo(account, 0);
        account.setLoginInfo(loginInfo);
        Candidate candidate = new Candidate(true, account,
                registerDTO.getFirstName(), registerDTO.getLastName());

        try {
            accountFacade.create(account);
            candidateFacade.create(candidate);
        }
        catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }

        String accountToken = hashService.SHA256(account.getEmail() + account.getId()
                + account.getLoginInfo().getLastSuccessfulLogin());
        mailService.sendActivationLink(account.getEmail(), accountToken, registerDTO.getLocale());
    }

    /**
     * Metoda aktywuje konto
     * @param activateAccountDTO obiekt zawierający email i token potrzebne do aktywacji konta.
     * @throws AppException wyjątek aplikacyjny rzucany gdy aktywacja konta się nie powiedzie
     * @throws MessagingException wyjątek związany w wysyłaniem wiadomości email
     */
    @PermitAll
    @Override
    public void activateAccount(ActivateAccountDTO activateAccountDTO) throws AppException, MessagingException {
        try {
            if (!AccountValidator.isEmailValid(activateAccountDTO.getEmail())) {
                throw AccountException.createExceptionIncorrectEmail();
            }
            Account account = accountFacade.findByEmail(activateAccountDTO.getEmail());

            String accountToken = hashService.SHA256(account.getEmail() + account.getId()
                    + account.getLoginInfo().getLastSuccessfulLogin());

            if (activateAccountDTO.getToken().compareTo(accountToken) != 0) {
                throw AccountException.createExceptionActivationFailed();
            }

            if (account.getConfirmed()) {
                throw AccountException.confirmExceptionAlreadyConfirmed();
            }
            account.setConfirmed(true);
            accountFacade.edit(account);
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
        mailService.sendActivatedMessage(activateAccountDTO.getEmail(), activateAccountDTO.getLocale());
    }


    /**
     * Metoda blokuje konto użytkownika poprzez zmianę pola active na false.
     * @param email email użytkownika.
     * @throws AppException wyjatek aplikacji rzucany gdy zablokowanie konta sie nie powiedzie
     * @throws MessagingException wyjątek rzucany przy błędzie z wysłaniem wiadomości email
     * @author 216727 Ernest Błaż
     */
    @RolesAllowed(changeActiveStatus)
    @Override
    public void blockAccount(String email) throws AppException, MessagingException {
        try {
            if (!AccountValidator.isEmailValid(email)) {
                throw AccountException.createExceptionIncorrectEmail();
            }
            Account account = accountFacade.findByEmail(email);

            if (!account.getActive()) {
                throw AccountException.blockExceptionAccountAlreadyBlocked();
            }

            account.setActive(false);
            accountFacade.edit(account);
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
        mailService.sendBlockMessage(email);
    }

    /**
     * Metoda wysyła link potwierdzający dane konto użytkownika
     * @param emailDTO obiekt danych przechowywujący email użytkownika
     * @throws AppException wyjątek aplikacji rzucany gdy konto jest już potwierdzone
     * @throws MessagingException wyjątek rzucany przy błędzie z wysłaniem wiadomości email
     * @author Sergiusz Parchatko
     */
    @RolesAllowed(sendConfirmationLink)
    @Override
    public void sendConfirmationLink(EmailDTO emailDTO) throws AppException, MessagingException {
        try {
            if (!AccountValidator.isEmailValid(emailDTO.getEmail())) {
                throw AccountException.createExceptionIncorrectEmail();
            }
            Account account = accountFacade.findByEmail(emailDTO.getEmail());

            if (account.getConfirmed()) {
                throw AccountException.confirmExceptionAlreadyConfirmed();
            }

            String accountToken = hashService.SHA256(account.getEmail() + account.getId()
                    + account.getLoginInfo().getLastSuccessfulLogin());
            mailService.sendActivationLink(account.getEmail(), accountToken, emailDTO.getLocale());

        } catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }

    }




    /**
     * Metoda odblokowuje konto użytkownika poprzez zmianę pola active na true.
     * @param email email użytkownika.
     * @throws AppException wyjatek aplikacji rzucany gdy odblokowanie konta sie nie powiedzie
     * @author 216727 Ernest Błaż
     */
    @RolesAllowed(changeActiveStatus)
    @Override
    public void unblockAccount(String email) throws AppException, MessagingException {
        try {
            if (!AccountValidator.isEmailValid(email)) {
                throw AccountException.createExceptionIncorrectEmail();
            }
            Account account = accountFacade.findByEmail(email);

            if (account.getActive()) {
                throw AccountException.blockExceptionAccountAlreadyUnblocked();
            }

            account.setActive(true);
            account.getLoginInfo().setLoginAttemptCounter(0);
            accountFacade.edit(account);
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
        mailService.sendUnblockMessage(email);
    }


    /**
     * Metoda nadaje użytkownikowi nowy poziom dostępu, pod warunkiem że jeszcze go nie posiada
     *
     * @param accessLevelDTO obiekt zawierający dane potrzebne do nadania poziomu dostępu,
     *                       czyli email wybranego konta oraz nazwa pożądanego poziomu dostępu
     * @throws AppException wyjątek aplikacyjny związany z niepowodzeniem nadania poziomu dostępu
     * np. z powodu posiadania go przez użytkownika
     * @author Jakub Fornalski
     */
    @RolesAllowed(addAccessLevel)
    @Override
    public void addAccessLevel(AccessLevelDTO accessLevelDTO) throws AppException {
        try {
            if (!AccountValidator.isEmailValid(accessLevelDTO.getEmail())) {
                throw AccountException.createExceptionIncorrectEmail();
            }
            Account account = accountFacade.findByEmail(accessLevelDTO.getEmail());
            Optional<AccessLevel> accessLevelOpt;
            switch (accessLevelDTO.getLevel()) {
                case AccessLevel.ADMIN:
                    accessLevelOpt = account.findAccessLevel(AccessLevel.ADMIN);
                    if (accessLevelOpt.isEmpty())
                        adminFacade.create(new Admin(true, account));
                    else
                        setAccessLevelActive(accessLevelOpt.get());
                    break;
                case AccessLevel.EMPLOYEE:
                    accessLevelOpt = account.findAccessLevel(AccessLevel.EMPLOYEE);
                    if (accessLevelOpt.isEmpty())
                        employeeFacade.create(new Employee(true, account));
                    else
                        setAccessLevelActive(accessLevelOpt.get());
                    break;
                case AccessLevel.CANDIDATE:
                    accessLevelOpt = account.findAccessLevel(AccessLevel.CANDIDATE);
                    if (accessLevelOpt.isEmpty())
                        candidateFacade.create(new Candidate(true, account));
                    else
                        setAccessLevelActive(accessLevelOpt.get());
                    break;
                default:
                    throw AccessLevelException.createExceptionAccessLevelNotRecognized();
            }
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    
    /**
     * Metoda pomocnicza, uaktywniająca istniejący poziom dostępu pod warunkiem ,że nie jest już aktywny
     * @param accessLevel obiekt encji poziomu dostępu, który ma zostać uaktulniony.
     * @throws AppException wyjątek aplikacyjny związany z niepowodzeniem operacji
     * @author Jakub Fornalski
     */
    private void setAccessLevelActive(AccessLevel accessLevel) throws AppException {
        if (accessLevel.getActive())
            throw AccessLevelException.createExceptionAccessLevelAlreadyGranted();
        else {
            accessLevel.setActive(true);
            accessLevelFacade.edit(accessLevel);
        }
    }
    

    /**
     * Metoda pomocnicza, dezaktywująca istniejący poziom dostępu pod warunkiem, że jest on aktywny
     * @param accessLevel obiekt encji poziomu dostępu, który ma zostać uaktulniony.
     * @throws AppException wyjątek aplikacyjny związany z niepowodzeniem operacji
     * @author Jakub Fornalski
     */
    private void setAccessLevelInactive(AccessLevel accessLevel) throws AppException {
        if (!accessLevel.getActive())
            throw AccessLevelException.createExceptionAccessLevelNotGranted();
        else {
            accessLevel.setActive(false);
            accessLevelFacade.edit(accessLevel);
        }
    }

    
    /**
     * Metoda odbiera użytkownikowi poziom dostępu, pod warunkiem że go posiada
     * @param accessLevelDTO obiekt zawierający dane potrzebne do odebrania poziomu dostępu,
     *                       czyli email wybranego konta oraz nazwa poziomu dostępu
     * @throws AppException wyjątek aplikacyjny związany z niepowodzeniem nadania poziomu dostępu
     * np. z powodu nie posiadania go przez użytkownika
     * @author Jakub Fornalski
     */
    @RolesAllowed(revokeAccessLevel)
    @Override
    public void revokeAccessLevel(AccessLevelDTO accessLevelDTO) throws AppException {
        try {
            Account account = accountFacade.findByEmail(accessLevelDTO.getEmail());
            Optional<AccessLevel> accessLevelOpt;
            
            switch (accessLevelDTO.getLevel()) {
                case AccessLevel.ADMIN:
                    accessLevelOpt = account.findAccessLevel(AccessLevel.ADMIN);
                    break;
                case AccessLevel.EMPLOYEE:
                    accessLevelOpt = account.findAccessLevel(AccessLevel.EMPLOYEE);
                    break;
                case AccessLevel.CANDIDATE:
                    accessLevelOpt = account.findAccessLevel(AccessLevel.CANDIDATE);
                    break;
                default:
                    throw AccessLevelException.createExceptionAccessLevelNotRecognized();
            }

            if (!accessLevelOpt.isEmpty())
                setAccessLevelInactive(accessLevelOpt.get());
            else
                throw AccessLevelException.createExceptionAccessLevelNotGranted();

        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }
    

    /**
     * Prywatna metoda pomocnicza, wykonująca aktualizację danych konta, wspólną
     * dla obu stworzonych metod, edytujących wskazane konto.
     * @param updatedAccountDetailsDTO obiekt zawierający nowe dane konta
     * @param account obiekt encji, przekazywany do funkcji
     * @throws AppException wyjątek aplikacyjny
     */
    private void updateAccountParams(UpdatedAccountDetailsDTO updatedAccountDetailsDTO, Account account) throws AppException {
        List<AccessLevel> accessLevels = accessLevelFacade.findByAccount(account);

        if(!AccountValidator.isMottoValid(updatedAccountDetailsDTO.getMotto())) {
            throw AccountException.updateExceptionIncorrectMotto();
        }

        account.setMotto(updatedAccountDetailsDTO.getMotto());
        accountFacade.edit(account);

        List<AccessLevel> filtered = accessLevels.stream()
                .filter(AccessLevel::getActive)
                .collect(Collectors.toList());

        for(AccessLevel accessLevel: filtered) {
            if(accessLevel.getLevel().equals("CANDIDATE")) {


                if(!AccountValidator.isFirstNameValid(updatedAccountDetailsDTO.getCandidate().getFirstName())) {
                    throw AccountException.updateExceptionIncorrectFirstName();
                }
                if(!AccountValidator.isLastNameValid(updatedAccountDetailsDTO.getCandidate().getLastName())) {
                    throw AccountException.updateExceptionIncorrectLastName();
                }

                Candidate candidate = candidateFacade.findById(accessLevel.getId());
                if(HmacSigner.verifyEtag(updatedAccountDetailsDTO.getCandidate().getEtag(), candidate.getVersion())){
                    candidate.setFirstName(updatedAccountDetailsDTO.getCandidate().getFirstName());
                    candidate.setLastName(updatedAccountDetailsDTO.getCandidate().getLastName());
                    candidateFacade.edit(candidate);
                } else throw ConcurrencyException.createResourceModifiedException();
            }
        }
    }

    /**
     * Metoda rejestruje zdarzenie zmiany poziomu dostępu
     *
     * @param changeAccessLevelDTO obiekt zawierający nazwę pożądanego poziomu dostępu
     * @author Michał Tęgi
     */
    @RolesAllowed(changeAccessLevel)
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public void changeAccessLevel(ChangeAccessLevelDTO changeAccessLevelDTO) {
        Logger.getGlobal().log(Level.INFO, "Changed access level, Idenity: " + securityContext.getCallerPrincipal().getName()
                                              + " Access level: " + changeAccessLevelDTO.getLevel());
    }

}
