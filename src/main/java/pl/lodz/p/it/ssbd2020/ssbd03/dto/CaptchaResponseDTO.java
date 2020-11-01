package pl.lodz.p.it.ssbd2020.ssbd03.dto;

/**
 * Klasa DTO używana do Captchy
 */
public class CaptchaResponseDTO {

    private boolean success;
    private String hostname;

    /**
     * Konstruktor bezparamterowy
     */
    public CaptchaResponseDTO() { }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

}
