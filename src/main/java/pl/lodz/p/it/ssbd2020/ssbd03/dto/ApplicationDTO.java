package pl.lodz.p.it.ssbd2020.ssbd03.dto;


import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

/**
 * Klasa DTO obiektu danych dla encji zgłoszenia
 */
public class ApplicationDTO extends VersionedDTO implements ConvertableDTO {

    private Long id;
    private double weight;
    private String examinationCode;
    private String motivationalLetter;
    private Long offerId;
    private String offerDestination;
    private String candidateFirstName;
    private String candidateLastName;
    private String categoryName;

    /**
     *
     * @param id                        identyfikator zgłoszenia
     * @param weight                    masa ciała kandydata
     * @param examinationCode           numer identyfikacyjny badania lekarskiego
     * @param motivationalLetter        list motywacyjny kandydata
     * @param offerId                   identyfikator oferty do której odnosi się zgłoszenie
     * @param offerDestination          miejsce docelowe oferty do której odnosi się zgłoszenie
     * @param candidateFirstName        imie kandydata
     * @param candidateLastName         nazwisko kandydata
     * @param version                   wersja
     */
    public ApplicationDTO(Long version, Long id, double weight, String examinationCode, String motivationalLetter, Long offerId,
                          String offerDestination, String candidateFirstName, String candidateLastName) throws AppException {
        super(version);
        this.id = id;
        this.weight = weight;
        this.examinationCode = examinationCode;
        this.motivationalLetter = motivationalLetter;
        this.offerId = offerId;
        this.offerDestination = offerDestination;
        this.candidateFirstName = candidateFirstName;
        this.candidateLastName = candidateLastName;
    }

    /**
     * Konstruktor bezparametrowy
     */
    public ApplicationDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getExaminationCode() {
        return examinationCode;
    }

    public void setExaminationCode(String examinationCode) {
        this.examinationCode = examinationCode;
    }

    public String getMotivationalLetter() {
        return motivationalLetter;
    }

    public void setMotivationalLetter(String motivationalLetter) {
        this.motivationalLetter = motivationalLetter;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public String getOfferDestination() {
        return offerDestination;
    }

    public void setOfferDestination(String offerDestination) {
        this.offerDestination = offerDestination;
    }

    public String getCandidateFirstName() {
        return candidateFirstName;
    }

    public void setCandidateFirstName(String candidateFirstName) {
        this.candidateFirstName = candidateFirstName;
    }

    public String getCandidateLastName() {
        return candidateLastName;
    }

    public void setCandidateLastName(String candidateLastName) {
        this.candidateLastName = candidateLastName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
