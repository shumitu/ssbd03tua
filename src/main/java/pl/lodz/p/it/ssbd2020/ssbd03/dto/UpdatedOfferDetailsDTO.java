package pl.lodz.p.it.ssbd2020.ssbd03.dto;

import java.util.Date;

/**
 * Klasa obiektu DTO dla zaktualizowanych danych oferty
 */
public class UpdatedOfferDetailsDTO extends VersionedDTO{


    private Long offerId;
    private Date flightStartTime;
    private Date flightEndTime;
    private String destination;
    private int price;
    private String description;
    private long totalCost;

    /**
     * Konstruktor bezparametrowy
     */
    public UpdatedOfferDetailsDTO() {
    }


    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Date getFlightStartTime() {
        return flightStartTime;
    }

    public void setFlightStartTime(Date flightStartTime) {
        this.flightStartTime = flightStartTime;
    }

    public Date getFlightEndTime() {
        return flightEndTime;
    }

    public void setFlightEndTime(Date flightEndTime) {
        this.flightEndTime = flightEndTime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(long totalCost) {
        this.totalCost = totalCost;
    }
}
