package pl.lodz.p.it.ssbd2020.ssbd03.dto;


/**
 * Klasa DTO obiektu danych procesu nadawania poziomu dostepu
 */

public class AccessLevelDTO {

    private String email;
    private String level;

    /**
     * Konstruktor bezparametrowy
     */
    public AccessLevelDTO() {
        super();
    }

    /**
     * Konstruktor
     * @param email login (email) użytkownika
     * @param level String opisujący pożądany poziom dostępu. Dozwolone wartości:
     *              "ADMIN", "EMPLOYEE","CANDIDATE".
     */
    public AccessLevelDTO(String email, String level) {
        this.email = email;
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public String getLevel() {
        return level;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLevel(String level) {
        this.level = level;
    }

}
