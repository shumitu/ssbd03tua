package pl.lodz.p.it.ssbd2020.ssbd03.exceptions;

/**
 * Wyjątki powiązane z operacją zmiany hasła.
 */
public class ChangePasswordException extends AppException {
    
    /**
     * Konstruktor
     * @param message komunikat błedu
     * @param cause   błąd który wystąpił w aplikacji
     * @see AppException
     */
    private ChangePasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Konstruktor
     * @param message komunikat błedu
     * @see AppException
     */
    private ChangePasswordException(String message) {
        super(message);
    }

    /**
     * Wyjątek rzucany gdy hasło przesłane do aplikacji w celu autoryzacji zmiany
     * nie jest zgodne z hasłem pobranym z bazy danych.
     * @return AppException z komunikatem niezgodnym haśle.
     */
    static public ChangePasswordException createWrongPasswordException() {
        return new ChangePasswordException(ERROR.WRONG_PASSWORD.toString());
    }

    /**
     * Wyjątek rzucany gdy nowe hasło przesłane do aplikacji nie różni się
     * od obecnego hasła pobranego z bazy danych.
     * @return AppException z komunikatem o identycznym haśle.
     */
    static public ChangePasswordException createExceptionPasswordIsTheSame() {
        return new ChangePasswordException(ERROR.THE_SAME_PASSWORD.toString());
    }

    /**
     * Wyjątek rzucany gdy podane nowe hasło nie spełnia wymogu długości.
     * @return AppException z komunikatem o próbie zmiany hasła innego użytkownika.
     */
    static public ChangePasswordException createExceptionOldPasswordTooShort() {
        return new ChangePasswordException(ERROR.OLD_PASSWORD_TOO_SHORT.toString());
    }

    /**
     * Wyjątek rzucany gdy podane nowe hasło nie spełnia wymogu długości.
     * @return AppException z komunikatem o próbie zmiany hasła innego użytkownika.
     */
    static public ChangePasswordException createExceptionNewPasswordTooShort() {
        return new ChangePasswordException(ERROR.NEW_PASSWORD_TOO_SHORT.toString());
    }

    /**
     * Wyjątek rzucany gdy podane nowe hasło nie spełnia wymogu długości.
     * @return AppException z komunikatem o próbie zmiany hasła innego użytkownika.
     */
    static public ChangePasswordException createExceptionCantChangeOwn() {
        return new ChangePasswordException(ERROR.CANT_CHANGE_OWN_PASSWORD.toString());
    }

}
