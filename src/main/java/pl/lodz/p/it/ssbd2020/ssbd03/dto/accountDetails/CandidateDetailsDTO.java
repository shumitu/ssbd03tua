package pl.lodz.p.it.ssbd2020.ssbd03.dto.accountDetails;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.VersionedDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Candidate;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

/**
 * Klasa dto szczegółowych informacji o kandydacie
 */
public class CandidateDetailsDTO extends VersionedDTO {
    private String firstName;
    private String lastName;

    /**
     * Konstruktor
     * @param candidate encja kandydata
     */
    public CandidateDetailsDTO(Candidate candidate) throws AppException {
        super(candidate.getVersion());
        this.firstName = candidate.getFirstName();
        this.lastName = candidate.getLastName();
    }

    /**
     * Konstruktor bezparametrowy
     */
    public CandidateDetailsDTO()  {
        super();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
