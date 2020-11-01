package pl.lodz.p.it.ssbd2020.ssbd03.dto;

/**
 * Klasa DTO obiektu danych, zawierająca identyfikator encji
 */

public class IdDTO {
    private Long id;

    /**
     * Konstruktor bezparametrowy
     */
    public IdDTO() {
    }

    /**
     * Konstruktor
     * @param id identyfikator encji
     */
    public IdDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
