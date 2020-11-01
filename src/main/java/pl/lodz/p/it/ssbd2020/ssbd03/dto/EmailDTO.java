package pl.lodz.p.it.ssbd2020.ssbd03.dto;

/**
 * Klasa DTO obiektu danych, przechowująca email użytkownika
 */
public class EmailDTO {
    private String email;
    private String locale;

    /**
     * Konstruktor
     *
     * @param email login (email) użytkownika
     */
    public EmailDTO(String email) {
        this.email = email;
    }

    /**
     * Konstruktor bezparametrowy
     */
    public EmailDTO() {
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
