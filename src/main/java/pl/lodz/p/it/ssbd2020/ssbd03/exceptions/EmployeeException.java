package pl.lodz.p.it.ssbd2020.ssbd03.exceptions;

import javax.persistence.NoResultException;

/**
 * Wyjątki powiązane z encją Employee
 */
public class EmployeeException extends AppException {

    /**
     * Konstruktor
     * @param message komunikat błedu
     * @param cause   błąd który wystąpił w aplikacji
     * @see AppException
     */
    protected EmployeeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Konstruktor
     * @param message komunikat błedu
     * @see AppException
     */
    protected EmployeeException(String message) {
        super(message);
    }

    /**
     * Wyjątek rzucany gdy wpis w tabeli Employee nie zostanie znalezione w bazie
     * @param e wyjątek typu NoResultException
     * @return AccountException z komunikatem o nie odnalezieniu pracownika
     */
    static public EmployeeException createExceptionEmployeeNotExists(NoResultException e) {
        return new EmployeeException(ERROR.EMPLOYEE_NOT_FOUND.toString(), e);
    }

}
