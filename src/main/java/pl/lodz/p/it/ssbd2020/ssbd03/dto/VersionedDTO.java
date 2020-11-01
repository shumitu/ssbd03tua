package pl.lodz.p.it.ssbd2020.ssbd03.dto;

import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.security.services.HmacSigner;

/**
 * Klasa abstrakcjyna dla obiektu DTO zawierającego podpisaną wersję encji.
 */
public abstract class VersionedDTO {
    protected String etag;

    /**
     * Konstruktor
     * @param version wersja encji
     */
    public VersionedDTO(Long version) throws AppException {
        this.etag = HmacSigner.getEtag(version);
    }

    /**
     * Konstruktor
     * @param etag podpisana wersja encji
     */
    public VersionedDTO(String etag) {
        this.etag = etag;
    }

    /**
     * Konstruktor bezparametrowy
     */
    public VersionedDTO() {
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }
}
