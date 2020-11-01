package pl.lodz.p.it.ssbd2020.ssbd03.dto;


/**
 * Klasa DTO używana do aktywacji konta
 */
public class BlockAccountDTO {
    private String email;
    private Boolean active;

    /**
     * Konstruktor bezparametrowy
     */
    public BlockAccountDTO() {
    }

    /**
     * Konstruktor
     * @param active boolean mówiący o tym czy dane konto ma zostać zablokowane, czy odblokowane
     * @param email  - email użytkownika, na którym dokonujemy zmian
     */
    public BlockAccountDTO(Boolean active, String email) {
        this.active = active;
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public String getEmail() {
        return email;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
