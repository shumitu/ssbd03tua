package pl.lodz.p.it.ssbd2020.ssbd03.dto;

/**
 * Klasa DTO służąca do przesyłnia danych ofert w celu wyświetlenia ich w liście pracownikowi i administratorowi
 */
public class OfferForAllListDTO implements Comparable<OfferForAllListDTO>, ConvertableDTO {
    private Long id;
    private int price;
    private boolean isHidden;
    private boolean isOpen;
    private double totalWeight;
    private String destination;

    /**
     * Konstruktor używany podczas tworzenia listy ofert
     * @param id id oferty
     * @param price cena podróży w tej ofercie
     * @param isHidden czy oferta jest ukryta
     * @param isOpen czy zapisy do oferty są otwarte
     * @param totalWeight całkowita waga pasażerów (agregat)
     * @param destination cel podróży
     */
    public OfferForAllListDTO(Long id, int price, boolean isHidden, boolean isOpen, double totalWeight, String destination) {
        this.id = id;
        this.price = price;
        this.isHidden = isHidden;
        this.isOpen = isOpen;
        this.totalWeight = totalWeight;
        this.destination = destination;
    }

    /**
     * Konstruktor bezparametrowy
     */
    public OfferForAllListDTO() {
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Long getId() {
        return id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public int compareTo(OfferForAllListDTO o) {
        return id.compareTo(o.id);
    }
}
