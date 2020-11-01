package pl.lodz.p.it.ssbd2020.ssbd03.dto.changePassword;

/**
 * Klasa DTO obiektu danych do zmiany hasła przez użytkownika.
 */
public class ChangePasswordDTO {
    private String email;
    private String oldPassword;
    private String newPassword;

    private String captcha;


    /**
     * Konstruktor
     *
     * @param email login (email) użytkownika
     * @param oldPassword obecne hasło użytkownika
     * @param newPassword nowe hasło użytkownika
     */
    public ChangePasswordDTO(String email, String oldPassword, String newPassword) {
        this.email = email;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    /**
     * Konstruktor
     */
    public ChangePasswordDTO() {}

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
