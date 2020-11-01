package pl.lodz.p.it.ssbd2020.ssbd03.dto;


/**
 * Klasa DTO obiektu danych, przechowująca dane do rozpoczęcia procesu resetowania hasła
 */
public class InitResetPasswordDTO {

    private String email;
    
    /**
     * Lokalizacja ustawiona w przeglądarce użytkownika.
     */
    private String locale;

    /**
     * Konstruktor
     *
     * @param email login (email) użytkownika
     */
    public InitResetPasswordDTO(String email) {
        this.email = email;
    }

    /**
     * Konstruktor bezparametrowy
     */
    public InitResetPasswordDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
