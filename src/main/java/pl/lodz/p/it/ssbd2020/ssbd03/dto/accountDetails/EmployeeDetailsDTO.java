package pl.lodz.p.it.ssbd2020.ssbd03.dto.accountDetails;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.VersionedDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Employee;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

/**
 * Klasa dto szczegółowych informacji o pracowniku.
 */
public class EmployeeDetailsDTO extends VersionedDTO {
    private Long employeeNumber;

    /**
     * Konstruktor bezparametrowy
     */
    public EmployeeDetailsDTO()  {
        super();
    }

    /**
     * Konstruktor
     * @param employee encja pracownika
     */
    public EmployeeDetailsDTO(Employee employee) throws AppException {
        super(employee.getVersion());
        this.employeeNumber = employee.getEmployeeNumber();
    }


    public Long getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(Long employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}
