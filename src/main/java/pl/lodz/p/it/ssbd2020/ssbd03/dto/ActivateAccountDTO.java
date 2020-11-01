package pl.lodz.p.it.ssbd2020.ssbd03.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Klasa DTO używana do potwierdzenia konta
 */
public class ActivateAccountDTO {
    private String email;
    private String token;
    
    /**
     * Lokalizacja ustawiona w przeglądarce użytkownika.
     */
    private String locale;

    /**
     * Konstruktor bezparametrowy
     */
    public ActivateAccountDTO(){}

    /**
     * Konstruktor
     * @param email - email użytkownika
     * @param token - wygenerowany token aktywacyjny
     */
    public ActivateAccountDTO(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
