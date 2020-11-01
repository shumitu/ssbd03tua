package pl.lodz.p.it.ssbd2020.ssbd03.exceptions;

import javax.persistence.NoResultException;

/**
 * Wyjątki powiązane z encją LoginInfo
 */
public class LoginInfoException extends AppException {

    /**
     * Konstruktor
     * @param message komunikat błedu
     * @param cause   błąd który wystąpił w aplikacji
     * @see AppException
     */
    protected LoginInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Konstruktor
     * @param message komunikat błedu
     * @see AppException
     */
    protected LoginInfoException(String message) {
        super(message);
    }

    /**
     * Wyjątek rzucany gdy dane logowania dla konta nie zostaną znalezione w bazie
     * @param e wyjątek typu NoResultException
     * @return AccountException z komunikatem o nie odnalezieniu danych logowania
     */
    static public LoginInfoException createExceptionLoginInfoNotExists(NoResultException e) {
        return new LoginInfoException(ERROR.LOGIN_INFO_NOT_FOUND.toString(), e);
    }

    /**
     * Wyjątek rzucany gdy pobrany adres ip ma zły format
     * @return AccountException z komunikatem o nie odnalezieniu danych logowania
     */
    static public LoginInfoException createExceptionWrongIpAddress() {
        return new LoginInfoException(ERROR.LOGIN_INFO_WRONG_IP_ADDRESS.toString());
    }
}
