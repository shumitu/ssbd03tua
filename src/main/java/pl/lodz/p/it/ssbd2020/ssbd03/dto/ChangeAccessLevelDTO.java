package pl.lodz.p.it.ssbd2020.ssbd03.dto;



/**
 * Klasa DTO obiektu danych dla rejestrowania zdarzenia zmiany poziomu dostępu,
 * przechowująca nazwę pożądanego poziomu dostępu
 */

public class ChangeAccessLevelDTO {

    private String level;

    /**
     * Konstruktor bezparametrowy
     */
    public ChangeAccessLevelDTO() {
        super();
    }

    /**
     * Konstruktor
     * @param level String opisujący pożądany poziom dostępu. Dozwolone wartości:
     *              "ADMIN", "EMPLOYEE","CANDIDATE".
     */
    public ChangeAccessLevelDTO(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}