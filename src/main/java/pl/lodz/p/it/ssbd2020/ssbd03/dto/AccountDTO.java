package pl.lodz.p.it.ssbd2020.ssbd03.dto;

import java.util.List;

/**
 * Klasa DTO obiektu danych dla encji konto
 */
public class AccountDTO implements Comparable<AccountDTO>, ConvertableDTO {
    private String email;
    private boolean confirmed;
    private boolean active;
    private boolean passwordChangeRequired;
    private List<String> accessLevelList;

    /**
     * Konstruktor
     * @param email login (email) użytkownika
     * @param confirmed boolean mówiący o tym, czy konto zostało potwierdzone
     * @param active boolean mówiący o tym, czy konto jest aktywne
     * @param passwordChangeRequired boolean mówiący o tym, czy konto wymaga zmiany hasła
     * @param accessLevelList lista poziomów dostępu dla konta
     */
    public AccountDTO(String email, boolean confirmed, boolean active, boolean passwordChangeRequired, List<String> accessLevelList) {
        this.email = email;
        this.confirmed = confirmed;
        this.active = active;
        this.passwordChangeRequired = passwordChangeRequired;
        this.accessLevelList = accessLevelList;
    }

    /**
     * Konstruktor bezparamterowy
     */
    public AccountDTO() {
    }

    public String getEmail() {
        return email;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isPasswordChangeRequired() {
        return passwordChangeRequired;
    }

    public List<String> getAccessLevelList() {
        return accessLevelList;
    }

    @Override
    public int compareTo(AccountDTO o) {
        return email.compareToIgnoreCase(o.getEmail());
    }
}
