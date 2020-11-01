package pl.lodz.p.it.ssbd2020.ssbd03.dto.offerDetails;

import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

import java.util.Date;

/**
 * Klasa DTO obiektu szczegółów oferty dla pracownika i administratora
 */
public class OfferDetailsEmployeeDTO extends OfferDetailsCandidateDTO {
    private boolean hidden;
    private boolean open;
    private double totalWeight;
    private long totalCost;

    /**
     * Konstruktor
     *
     * @param description     opis oferty
     * @param destination     cel podróży
     * @param flightEndTime   data wylotu
     * @param flightStartTime data powrotu na Ziemię
     * @param price           cena wycieczki
     * @param starship        obiekt DTO statku dla szczegółów oferty
     * @param version         wersja
     * @param hidden          widoczność oferty dla kandydatów i gości
     * @param open            czy zapisy są otawarte
     * @param totalCost       całkowity koszt poniesiony przez przewoźnika
     * @param totalWeight     aktualna całkowita waga pasażerow
     */
    public OfferDetailsEmployeeDTO(Long version, Date flightStartTime, Date flightEndTime, String destination, int price,
                                   String description, StarshipOfferDetailsDTO starship, boolean hidden,
                                   boolean open, double totalWeight, long totalCost)
            throws AppException {
        super(version, flightStartTime, flightEndTime, destination, price, description, starship);
        this.hidden = hidden;
        this.open = open;
        this.totalWeight = totalWeight;
        this.totalCost = totalCost;
    }

    /**
     * Konstruktor
     *
     * @param description     opis oferty
     * @param destination     cel podróży
     * @param flightEndTime   data wylotu
     * @param flightStartTime data powrotu na Ziemię
     * @param price           cena wycieczki
     * @param version         wersja
     * @param hidden          widoczność oferty dla kandydatów i gości
     * @param open            czy zapisy są otawarte
     * @param totalCost       całkowity koszt poniesiony przez przewoźnika
     * @param totalWeight     aktualna całkowita waga pasażerow
     */
    public OfferDetailsEmployeeDTO(Long version, Date flightStartTime, Date flightEndTime, String destination, int price,
                                   String description, boolean hidden,
                                   boolean open, double totalWeight, long totalCost)
            throws AppException {
        super(version, flightStartTime, flightEndTime, destination, price, description);
        this.hidden = hidden;
        this.open = open;
        this.totalWeight = totalWeight;
        this.totalCost = totalCost;
    }

    /**
     *Konstruktor bezparametrowy
     */
    public OfferDetailsEmployeeDTO() {
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(long totalCost) {
        this.totalCost = totalCost;
    }

}
