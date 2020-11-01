package pl.lodz.p.it.ssbd2020.ssbd03.dto;

import java.util.Date;

/**
 * Klasa dla obiektu DTO dla procesu tworzenia oferty
 */
public class OfferDTO {

    private Long offerId;
    private Date flightStartTime;
    private Date flightEndTime;
    private String destination;
    private int price;
    private String description;
    private boolean hidden;
    private boolean open;
    private long totalCost;
    private double totalWeight;
    private Long starshipId;

    /**
     * Konstruktor bezparametrowy
     */
    public OfferDTO() {
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

    public long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(long totalCost) {
        this.totalCost = totalCost;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Long getStarshipId() { return starshipId; }

    public void setStarshipId(Long starshipId) { this.starshipId = starshipId; }
}