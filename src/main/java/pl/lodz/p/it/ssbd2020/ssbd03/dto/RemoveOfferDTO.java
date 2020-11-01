package pl.lodz.p.it.ssbd2020.ssbd03.dto;

/**
 * Klasa DTO obiektu danych do usuwania oferty
 */
public class RemoveOfferDTO {
    private Long offerID;

    /**
     * Konstrukor
     * @param offerID numer identyfikacyjny oferty
     */
    public RemoveOfferDTO(Long offerID) {
        this.offerID = offerID;
    }

    /**
     * Konstruktor bezparametrowy
     */
    public RemoveOfferDTO() {

    }

    public Long getOfferID() {
        return offerID;
    }

    public void setOfferID(Long offerID) {
        this.offerID = offerID;
    }
}
