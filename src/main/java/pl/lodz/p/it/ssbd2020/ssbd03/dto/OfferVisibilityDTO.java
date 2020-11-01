package pl.lodz.p.it.ssbd2020.ssbd03.dto;

/**
 * Klasa DTO służąca do przesyłania informacji niezbędnych do
 * zmiany statusu oferty na widoczną/niewidoczną dla kendydatów
 */

public class OfferVisibilityDTO {

    private Long id;
    private boolean visible;

    /**
     * Konstruktor bezparametrowy
     */

    public OfferVisibilityDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
