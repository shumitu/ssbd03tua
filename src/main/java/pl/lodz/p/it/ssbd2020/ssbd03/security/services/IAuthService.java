package pl.lodz.p.it.ssbd2020.ssbd03.security.services;

import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;

/**
 * Interfejs klasy stosowanej do uwierzytelnienia użytkownika.
 */
public interface IAuthService {
    /**
     * Metoda odpowiada za uwierzytelnienie użytkownika w oparciu o login i hasło.
     *
     * @param credentials obiekt reprezentujący login i hasło użytkownika.
     * @return wynik uwierzytelnienia dla podanych danych.
     * @author Michał Tęgi
     */
    CredentialValidationResult validateCredentials(UsernamePasswordCredential credentials);
}
