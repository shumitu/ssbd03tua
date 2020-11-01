package pl.lodz.p.it.ssbd2020.ssbd03.dto;

import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Klasa DTO obiektu danych dla edycji własnego zgłoszenia
 */

public class UpdateApplicationDTO extends VersionedDTO {

    private Long applicationId;
    private double weight;
    private String examinationCode;
    private String motivationalLetter;

    /**
     * Konstruktor bezparametrowy
     */
    public UpdateApplicationDTO() {
    }

    /**
     * Konstruktor
     * @param applicationId identyfikator zgłoszenia
     * @param weight masa ciała kandydata
     * @param examinationCode numer badania lekarskiego kandydata
     * @param motivationalLetter odnośnik do listu motywacyjnego kandydata
     * @param etag wersja
     */
    public UpdateApplicationDTO(Long etag, Long applicationId, double weight, String examinationCode, String motivationalLetter) throws AppException {
        super(etag);
        this.applicationId = applicationId;
        this.weight = weight;
        this.examinationCode = examinationCode;
        this.motivationalLetter = motivationalLetter;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
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
}
