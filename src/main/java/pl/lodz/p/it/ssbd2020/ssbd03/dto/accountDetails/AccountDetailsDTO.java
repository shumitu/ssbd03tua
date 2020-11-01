package pl.lodz.p.it.ssbd2020.ssbd03.dto.accountDetails;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.ConvertableDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.VersionedDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

import java.util.Date;
import java.util.List;

/**
 * Klasa DTO obiektu danych dla szczegółów konta
 */
public class AccountDetailsDTO extends VersionedDTO implements ConvertableDTO {
    private String email;
    private String motto;
    private boolean confirmed;
    private boolean active;
    private boolean passwordChangeRequired;
    private List<String> accessLevelList;
    private Date lastSuccessfulLogin;
    private Date lastUnsuccessfulLogin;

    private CandidateDetailsDTO candidate;
    private EmployeeDetailsDTO employee;


    /**
     * Kostruktor
     * @param email login (email) użytkownika
     * @param motto zmienna przechowująca motto ustawione przez użytkownika
     * @param confirmed boolean mówiący o tym, czy konto zostało potwierdzone
     * @param active boolean mówiący o tym, czy konto jest aktywne
     * @param passwordChangeRequired boolean mówiący o tym, czy konto wymaga zmiany hasła
     * @param accessLevelList lista poziomów dostępu dla konta
     * @param lastSuccessfulLogin timestamp ostatniego, poprawnego logowania
     * @param lastUnsuccessfulLogin timestamp ostatniego, niepoprawnego logowania
     */
    public AccountDetailsDTO(Long version, String email, String motto, boolean confirmed, boolean active, boolean passwordChangeRequired,
                             List<String> accessLevelList, Date lastSuccessfulLogin, Date lastUnsuccessfulLogin) throws AppException {
        super(version);
        this.email = email;
        this.motto = motto;
        this.confirmed = confirmed;
        this.active = active;
        this.passwordChangeRequired = passwordChangeRequired;
        this.accessLevelList = accessLevelList;
        this.lastSuccessfulLogin = lastSuccessfulLogin;
        this.lastUnsuccessfulLogin = lastUnsuccessfulLogin;
        this.candidate = null;
        this.employee = null;
    }

    /**
     * Kostruktor
     *
     * @param email                  login (email) użytkownika
     * @param motto                  zmienna przechowująca motto ustawione przez użytkownika
     * @param confirmed              boolean mówiący o tym, czy konto zostało potwierdzone
     * @param active                 boolean mówiący o tym, czy konto jest aktywne
     * @param passwordChangeRequired boolean mówiący o tym, czy konto wymaga zmiany hasła
     * @param accessLevelList        lista poziomów dostępu dla konta
     * @param lastSuccessfulLogin    timestamp ostatniego, poprawnego logowania
     */
    public AccountDetailsDTO(Long version, String email, String motto, boolean confirmed, boolean active, boolean passwordChangeRequired,
                             List<String> accessLevelList, Date lastSuccessfulLogin) throws AppException {
        super(version);
        this.email = email;
        this.motto = motto;
        this.confirmed = confirmed;
        this.active = active;
        this.passwordChangeRequired = passwordChangeRequired;
        this.accessLevelList = accessLevelList;
        this.lastSuccessfulLogin = lastSuccessfulLogin;
        this.lastUnsuccessfulLogin = null;
        this.candidate = null;
        this.employee = null;
    }

    /**
     * Konstruktor bezparametrowy
     */
    public AccountDetailsDTO() {
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

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPasswordChangeRequired() {
        return passwordChangeRequired;
    }

    public void setPasswordChangeRequired(boolean passwordChangeRequired) {
        this.passwordChangeRequired = passwordChangeRequired;
    }

    public List<String> getAccessLevelList() {
        return accessLevelList;
    }

    public void setAccessLevelList(List<String> accessLevelList) {
        this.accessLevelList = accessLevelList;
    }

    public Date getLastSuccessfulLogin() {
        return lastSuccessfulLogin;
    }

    public void setLastSuccessfulLogin(Date lastSuccessfulLogin) {
        this.lastSuccessfulLogin = lastSuccessfulLogin;
    }

    public Date getLastUnsuccessfulLogin() {
        return lastUnsuccessfulLogin;
    }

    public void setLastUnsuccessfulLogin(Date lastUnsuccessfulLogin) {
        this.lastUnsuccessfulLogin = lastUnsuccessfulLogin;
    }

    public CandidateDetailsDTO getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateDetailsDTO candidate) {
        this.candidate = candidate;
    }

    public EmployeeDetailsDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDetailsDTO employee) {
        this.employee = employee;
    }
}
