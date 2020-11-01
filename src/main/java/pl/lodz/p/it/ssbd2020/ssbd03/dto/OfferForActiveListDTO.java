package pl.lodz.p.it.ssbd2020.ssbd03.dto;

/**
 * Klasa DTO służąca do przesyłnia danych ofert w celu wyświetlenia ich w liście gościowi i kandydatowi
 */
public class OfferForActiveListDTO implements Comparable<OfferForActiveListDTO>, ConvertableDTO {
    private Long id;
    private int price;
    private boolean isOpen;
    private String destination;

    /**
     * Konstruktor używany podczas tworzenia listy ofert
     * @param id id oferty
     * @param price cena podróży w tej ofercie
     * @param isOpen infromacja o tym czy zapisy do oferty są otwarte
     * @param destination cel podróży
     */
    public OfferForActiveListDTO(Long id, int price, boolean isOpen, String destination) {
        this.id = id;
        this.price = price;
        this.isOpen = isOpen;
        this.destination = destination;
    }

    /**
     * Konstruktor bezparametrowy
     */
    public OfferForActiveListDTO() {
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

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int compareTo(OfferForActiveListDTO o) {
        return id.compareTo(o.id);
    }
}
