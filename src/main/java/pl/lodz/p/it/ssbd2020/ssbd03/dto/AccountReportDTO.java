package pl.lodz.p.it.ssbd2020.ssbd03.dto;

import java.util.Date;

/**
 * Klasa DTO obiektu danych dla raportu logowania
 */
public class AccountReportDTO implements Comparable<AccountReportDTO>, ConvertableDTO {
    private String email;
    private Date lastSuccessfulLogin;
    private Date lastUnsuccessfulLogin;
    private String ipAddress;

    /**
     * Konstruktor
     * @param email email użytkownika
     * @param lastSuccessfulLogin znacznik ostatniego poprawnego logowania
     * @param lastUnsuccessfulLogin znacznik ostatniego niepoprawnego logowania
     * @param ipAddress adres ip z którego dokonano autoryzacji
     */
    public AccountReportDTO(String email, Date lastSuccessfulLogin, Date lastUnsuccessfulLogin, String ipAddress) {
        this.email = email;
        this.lastSuccessfulLogin = lastSuccessfulLogin;
        this.lastUnsuccessfulLogin = lastUnsuccessfulLogin;
        this.ipAddress = ipAddress;
    }

    /**
     * Konstruktor bezparametrowy
     */
    public AccountReportDTO() { }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public int compareTo(AccountReportDTO o) { return email.compareToIgnoreCase(o.getEmail()); }
}
