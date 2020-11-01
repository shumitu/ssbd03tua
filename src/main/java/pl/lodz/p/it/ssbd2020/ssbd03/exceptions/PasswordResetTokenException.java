package pl.lodz.p.it.ssbd2020.ssbd03.exceptions;

import javax.persistence.NoResultException;

/**
 * Wyjątki związane z tokenem dla zmiany chasła
 */
public class PasswordResetTokenException extends AppException {
    /**
     * Konstruktor
     * @param message komunikat błędu
     * @param cause błąd który wystąpił w aplikacji
     */
    private PasswordResetTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Konstruktor
     * @param message komunikat błędu
     */
    private PasswordResetTokenException(String message) {
        super(message);
    }

    /**
     * Wyjątek rzucany w przypadku gdy token dla resetu hasła nie istnieje
     * @param e wyjątek typu NoResultException
     * @return AppException z komunikatem o nieodnalezieniu tokenu
     */
    static public PasswordResetTokenException createExceptionPasswordResetTokenNotExists(NoResultException e) {
        return new PasswordResetTokenException(ERROR.TOKEN_NOT_FOUND.toString(), e);
    }

    /**
     * Wyjątek rzucany w przypadku gdy token dla resetu hasła wygasł
     * @return AppException z komunikatem o wygaśnięciu tokenu
     */
    static public PasswordResetTokenException createTokenExpiredException() {
        return new PasswordResetTokenException(ERROR.TOKEN_EXPIRED.toString());
    }

}
