package pl.lodz.p.it.ssbd2020.ssbd03.dto.changePassword;


/**
 * Klasa DTO obiektu danych do zmiany hasła przez administratora.
 */
public class ChangePasswordAdminDTO {
    private String email;
    private String newPassword;

    /**
     * Konstruktor
     *
     * @param email       login (email) użytkownika
     * @param newPassword nowe hasło użytkownika
     */
    public ChangePasswordAdminDTO(String email, String newPassword) {
        this.email = email;
        this.newPassword = newPassword;
    }

    /**
     * Konstruktor
     */
    public ChangePasswordAdminDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
