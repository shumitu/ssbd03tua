package pl.lodz.p.it.ssbd2020.ssbd03.dto.updatedAccountDetails;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.VersionedDTO;

public class UpdatedCandidateDetailsDTO extends VersionedDTO {
    private String firstName;
    private String lastName;

    public UpdatedCandidateDetailsDTO(String etag, String firstName, String lastName) {
        super(etag);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UpdatedCandidateDetailsDTO(){}

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
