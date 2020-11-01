package pl.lodz.p.it.ssbd2020.ssbd03.dto.offerDetails;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.ConvertableDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.VersionedDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

import java.util.Date;

/**
 * Klasa DTO obiektu szczegółów oferty dla kandydata i gościa
 */
public class OfferDetailsCandidateDTO extends VersionedDTO implements ConvertableDTO {

    protected Date flightStartTime;
    protected Date flightEndTime;
    protected String destination;
    protected int price;
    protected String description;
    protected StarshipOfferDetailsDTO starship;

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
     */
    public OfferDetailsCandidateDTO(Long version, Date flightStartTime, Date flightEndTime, String destination, int price, String description, StarshipOfferDetailsDTO starship) throws AppException {
        super(version);
        this.flightStartTime = flightStartTime;
        this.flightEndTime = flightEndTime;
        this.destination = destination;
        this.price = price;
        this.description = description;
        this.starship = starship;
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
     */
    public OfferDetailsCandidateDTO(Long version, Date flightStartTime, Date flightEndTime, String destination, int price, String description) throws AppException {
        super(version);
        this.flightStartTime = flightStartTime;
        this.flightEndTime = flightEndTime;
        this.destination = destination;
        this.price = price;
        this.description = description;
    }

    /**
     *Konstruktor bezparametrowy
     */
    public OfferDetailsCandidateDTO() {
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

    public StarshipOfferDetailsDTO getStarship() {
        return starship;
    }

    public void setStarship(StarshipOfferDetailsDTO starship) {
        this.starship = starship;
    }
}
