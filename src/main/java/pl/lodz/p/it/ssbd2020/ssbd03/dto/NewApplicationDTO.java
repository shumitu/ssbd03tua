package pl.lodz.p.it.ssbd2020.ssbd03.dto;


/**
 * Klasa DTO obiektu danych dla nowo tworzonej encji zgłoszenia
 */
public class NewApplicationDTO implements ConvertableDTO {

    private double weight;
    private String examinationCode;
    private String motivationalLetter;
    private Long offerId;
    private String offerEtag;

    /**
     *
     * @param weight                    masa ciała kandydata
     * @param examinationCode           numer identyfikacyjny badania lekarskiego
     * @param motivationalLetter        list motywacyjny kandydata
     * @param offerId                   identyfikator oferty do której odnosi się zgłoszenie
     * @param offerEtag                 skrót opisujący wersję encji
     */
    public NewApplicationDTO(double weight, String examinationCode,
                String motivationalLetter, Long offerId, String offerEtag) {
        this.weight = weight;
        this.examinationCode = examinationCode;
        this.motivationalLetter = motivationalLetter;
        this.offerId = offerId;
        this.offerEtag = offerEtag;
    }

    /**
     * Konstruktor bezparametrowy
     */
    public NewApplicationDTO() {
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

    public String getOfferEtag() {
        return offerEtag;
    }

    public void setOfferEtag(String offerEtag) {
        this.offerEtag = offerEtag;
    }
}
