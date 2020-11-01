package pl.lodz.p.it.ssbd2020.ssbd03.dto;

/**
 * Klasa DTO służąca do aktywowania / dezaktywowania statku
 */
public class StarshipStatusDTO {
    private long id;
    private boolean active;

    /**
     * Konstruktor bezparametrowy
     */
    public StarshipStatusDTO() {
    }

    /**
     * Konstruktor
     * @param id identyfikator statku
     * @param active stan aktywności statku
     */
    public StarshipStatusDTO(long id, boolean active) {
        this.id = id;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
