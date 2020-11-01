package pl.lodz.p.it.ssbd2020.ssbd03.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Klasa DTO obiektu danych dla procesu rejestracji
 */
public class RegisterDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    private String motto;
    
    /**
     * Lokalizacja ustawiona w przeglądarce użytkownika.
     */
    private String locale;
    private String captcha;

    /**
     * Konstruktor
     * @param email login (email) użytkownika
     * @param password hasło użytkownika
     * @param firstName imię użytkownika
     * @param lastName nazwisko użytkownika
     * @param motto motto użytkownika
     */
    public RegisterDTO(String email, String password, String firstName, String lastName, String motto) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.motto = motto;
    }

    /**
     * Konstruktor bezparametrowy
     */
    public RegisterDTO() {
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMotto() {
        return motto;
    }
}
