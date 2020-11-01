package pl.lodz.p.it.ssbd2020.ssbd03.exceptions;

/**
 * Wyjątki powiązane z encją AccessLevel
 */

public class AccessLevelException extends AppException {

    /**
     * Konstruktor
     * @param message komunikat błedu
     * @param cause   błąd który wystąpił w aplikacji
     * @see AppException
     */

    protected AccessLevelException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Konstruktor
     * @param message komunikat błedu
     * @see AppException
     */

    protected AccessLevelException(String message) {
        super(message);
    }

    /**
     * Wyjątek rzucany przy próbie nadania poziomu dostępu, który dane konto już posiada
     * @return AccessLevelException z komunikatem o probie ponownego nadania instniejącego poziomu dostępu
     */

    static public AccessLevelException createExceptionAccessLevelAlreadyGranted() {
        return new AccessLevelException(ERROR.ACCESS_LEVEL_ALREADY_GRANTED.toString());
    }

    /**
     * Wyjątek rzucany przy próbie odwołoania się do poziomu dostępu, który nie istnieje w aplikacji
     * @return AccessLevelException z komunikatem o nie rozpoznaniu danego poziomu dostępu
     */

    static public AccessLevelException createExceptionAccessLevelNotRecognized() {
        return new AccessLevelException(ERROR.ACCESS_LEVEL_NAME_NOT_RECOGNIZED.toString());
    }

    /**
     * Wyjątek rzucany przy próbie odebrania poziomu dostępu, który nie był wcześniej przyznany
     * @return AccessLevelException z komunikatem o nieudanej próbie odebrania poziomu dostępu
     */
    static public AccessLevelException createExceptionAccessLevelNotGranted() {
        return new AccessLevelException(ERROR.ACCESS_LEVEL_NOT_GRANTED.toString());
    }
}
