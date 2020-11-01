package pl.lodz.p.it.ssbd2020.ssbd03.dto.updatedAccountDetails;


import pl.lodz.p.it.ssbd2020.ssbd03.dto.VersionedDTO;


/**
 * Klasa DTO obiektu danych dla zaktualizowanych danych konta
 */
public class UpdatedAccountDetailsDTO extends VersionedDTO {
    private String email;
    private String motto;

    private String captcha;

    private UpdatedCandidateDetailsDTO candidate;

    /**
     *
     * @param etag podpisana wersja encji
     * @param candidate obiekt dto zaktualizowanych danych kandydata
     * @param email email (login) użytkownika
     * @param motto nowe motto użytkownika
     */
    public UpdatedAccountDetailsDTO(String etag, String email, UpdatedCandidateDetailsDTO candidate, String motto) {
        super(etag);
        this.email = email;
        this.motto = motto;
        this.candidate = candidate;
    }

    /**
     * Konstruktor bezparametrowy
     */
    public UpdatedAccountDetailsDTO() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public UpdatedCandidateDetailsDTO getCandidate() {
        return candidate;
    }

    public void setCandidate(UpdatedCandidateDetailsDTO candidate) {
        this.candidate = candidate;
    }
}
