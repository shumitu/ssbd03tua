package pl.lodz.p.it.ssbd2020.ssbd03.dto;


/**
 * Klasa DTO obiektu resetu hasła
 */
public class PasswordResetDTO {
    private String token;
    private String password;

    /**
     * Konstruktor
     *
     * @param token    token użytkownika
     * @param password wprowadzone hasło
     */
    public PasswordResetDTO(String token, String password) {
        this.token = token;
        this.password = password;
    }

    /**
     * Konstruktor bezparametrowy
     */
    public PasswordResetDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
