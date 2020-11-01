package pl.lodz.p.it.ssbd2020.ssbd03.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Klasa DTO obiektu znaczników czasu
 */
public class LoginTimestampsDTO {
    private Date lastSuccessfulLogin;
    private Date lastUnsuccessfulLogin;

    /**
     * Konstruktor
     *
     * @param lastSuccessfulLogin   znacznik czasu dla ostatniego, poprawnego logowania
     * @param lastUnsuccessfulLogin znacznik czasu dla ostatniego, niepoprawnego logowania
     */
    public LoginTimestampsDTO(Date lastSuccessfulLogin, Date lastUnsuccessfulLogin) {
        this.lastSuccessfulLogin = lastSuccessfulLogin;
        this.lastUnsuccessfulLogin = lastUnsuccessfulLogin;
    }

    /**
     * Konstruktor bezparametrowy
     */
    public LoginTimestampsDTO() {
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
}
