package pl.lodz.p.it.ssbd2020.ssbd03.dto;

/**
 * Klasa DTO służąca do przypisywania statku do oferty
 */
public class AssignStarshipToOfferDTO {
    private long starshipId;
    private long offerId;


    /**
     * Konstruktor bezparametrowy
     */
    public AssignStarshipToOfferDTO() {
    }

    /**
     *
     * @param starshipId identyfikator statku
     * @param offerId identyfikator oferty
     */
    public AssignStarshipToOfferDTO(long starshipId, long offerId) {
        this.starshipId = starshipId;
        this.offerId = offerId;
    }

    public long getStarshipId() {
        return starshipId;
    }

    public void setStarshipId(long starshipId) {
        this.starshipId = starshipId;
    }

    public long getOfferId() {
        return offerId;
    }

    public void setOfferId(long offerId) {
        this.offerId = offerId;
    }

}
