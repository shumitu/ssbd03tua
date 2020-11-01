package pl.lodz.p.it.ssbd2020.ssbd03.security.utils;

import javax.security.enterprise.credential.Credential;
import java.util.Set;

/**
 * Klasa przechowująca role użytkownika oraz identyfikator
 */
public class JWTCredential implements Credential {
    private final String principal;
    private final Set<String> authorities;

    /**
     * Konstruktor
     * @param principal   identyfikator użytkownika
     * @param authorities zbiór ról użytkownika
     */
    public JWTCredential(String principal, Set<String> authorities) {
        this.principal = principal;
        this.authorities = authorities;
    }

    /**
     * Konstruktor
     * @param principal   identyfikator użytkownika
     */
    public JWTCredential(String principal) {
        this.principal = principal;
        this.authorities = null;
    }


    public String getPrincipal() {
        return principal;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }
}
