package pl.lodz.p.it.ssbd2020.ssbd03.dto.offerDetails;


/**
 * Klasa DTO obiektu szczegółów staku w szczegółach oferty
 *
 * @author Michał Tęgi
 */
public class StarshipOfferDetailsDTO {
    private Long id;
    private String name;

    /**
     * Konstruktor
     *
     * @param id   identyfikator statku
     * @param name nazwa statku
     */
    public StarshipOfferDetailsDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Konstruktor bezparametrowy
     */
    public StarshipOfferDetailsDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

