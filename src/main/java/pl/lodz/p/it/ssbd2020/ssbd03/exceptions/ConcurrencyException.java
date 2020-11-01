package pl.lodz.p.it.ssbd2020.ssbd03.exceptions;

/**
 * Wyjątki powiązane z przetwarzaniem współbieżnym
 */
public class ConcurrencyException extends AppException {
    /**
     * Konstruktor
     * @param message komunikat błedu
     * @param cause   błąd który wystąpił w aplikacji
     * @see AppException
     */
    private ConcurrencyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Konstruktor
     * @param message komunikat błedu
     * @see AppException
     */
    private ConcurrencyException(String message) {
        super(message);
    }


    /**
     * @return AppException z komunikatem o niepowodzeniu ponowienia transakcji.
     */
    static public ConcurrencyException createResourceModifiedException() {
        return new ConcurrencyException(ERROR.RESOURCE_MODIFIED.toString());
    }
}
