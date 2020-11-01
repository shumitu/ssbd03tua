package pl.lodz.p.it.ssbd2020.ssbd03.security.services;

import pl.lodz.p.it.ssbd2020.ssbd03.entities.AuthView;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.TransactionException;
import pl.lodz.p.it.ssbd2020.ssbd03.mok.services.AccountService;
import pl.lodz.p.it.ssbd2020.ssbd03.mok.services.AccountServiceLocal;
import pl.lodz.p.it.ssbd2020.ssbd03.security.facades.AuthViewFacadeLocal;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Klasa komunikująca się z authViewFacade w celu uwierzytelnienia użytkownika
 */
@RequestScoped
public class AuthService implements IAuthService {

    @Inject
    private AuthViewFacadeLocal authViewFacade;

    @Inject
    private HashService hashService;

    @Inject
    AccountServiceLocal accountService;

    /**
     * Metoda odpowiada za sprawdzenie czy dana para (login, hasło) znajduje się w bazie danych.
     * @param credentials obiekt reprezentujący login i hasło użytkownika.
     * @return wynik uwierzytelnienia dla podanych danych.
     * @author Michał Tęgi
     */
    @Override
    @PermitAll
    public CredentialValidationResult validateCredentials(UsernamePasswordCredential credentials) {
        List<AuthView> authView = authViewFacade.findByEmail(credentials.getCaller());
        if (authView.isEmpty()) {
            return CredentialValidationResult.INVALID_RESULT;
        }
        try {
            String sha256hex = hashService.SHA256(credentials.getPasswordAsString());
            if(sha256hex.equals(authView.get(0).getPassword())){
                try {
                    do{
                        if(accountService.getCallCounter() >= accountService.getMaxTransactionCount()){
                            throw AppException.createExceptionForRepeatedTransactionRollback();
                        }
                        accountService.handleSuccessfulLogin(credentials.getCaller());
                    }
                    while (accountService.isLastTransactionRollback());
                }
                catch (TransactionException e){
                    validateCredentials(credentials);
                }
                Set<String> accessLevelSet = authView.stream().map(AuthView::getAccessLevel).collect(Collectors.toSet());
                return new CredentialValidationResult(authView.get(0).getEmail(), accessLevelSet);
            } else {
                try {
                    do{
                        if(accountService.getCallCounter() >= accountService.getMaxTransactionCount()){
                            throw AppException.createExceptionForRepeatedTransactionRollback();
                        }
                        accountService.handleUnsuccessfulLogin(credentials.getCaller());
                    }
                    while (accountService.isLastTransactionRollback());
                }
                catch (TransactionException e){
                    validateCredentials(credentials);
                }
            }
        } catch (AppException e) {
            return CredentialValidationResult.NOT_VALIDATED_RESULT;
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }

    /**
     * Metoda pobranie poziomów dostępu użytkownika
     * @param email email (login) użytkownika
     * @return poziomy dostępu użytkownika
     * @author Michał Tęgi
     */
    @PermitAll
    Set<String> getAccessLevels(String email) {
        return authViewFacade.findByEmail(email).stream().map(AuthView::getAccessLevel).collect(Collectors.toSet());
    }
}
