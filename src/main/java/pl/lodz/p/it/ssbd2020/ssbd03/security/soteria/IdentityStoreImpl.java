package pl.lodz.p.it.ssbd2020.ssbd03.security.soteria;

import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd03.security.services.IAuthService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;


/**
 * Klasa odpowiada za uwierzytelnienie użytkownika w oparciu źródło danych
 * takie jak m.in. baza danych, serwer LDAP lub plik.
 * Wykorzystywana przez {@link AuthMechanism} do ustalenia tożsamości użytkownika.
 *
 * @see IdentityStore
 */
@ApplicationScoped
@Interceptors(LoggingInterceptor.class)
public class IdentityStoreImpl implements IdentityStore {

    @Inject
    IAuthService authService;

    /**
     * Metoda odpowiada uwierzytelnienie użytkownika w oparciu o login i hasło.
     * @param usernamePasswordCredential obiekt reprezentujący login i hasło użytkownika.
     * @return wynik uwierzytelnienia dla podanych danych.
     */
    public CredentialValidationResult validate(UsernamePasswordCredential usernamePasswordCredential) {
        return authService.validateCredentials(usernamePasswordCredential);
    }
}


