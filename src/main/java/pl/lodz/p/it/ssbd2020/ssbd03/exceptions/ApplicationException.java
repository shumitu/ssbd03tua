package pl.lodz.p.it.ssbd2020.ssbd03.exceptions;

import javax.persistence.NoResultException;

/**
 * Wyjątki związane ze zgłoszeniami kandydatów
 */
public class ApplicationException extends AppException {
    /**
     * Konstruktor
     * @param message komunikat błedu
     * @param cause   błąd który wystąpił w aplikacji
     * @see AppException
     */
    private ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Konstruktor
     * @param message komunikat błedu
     * @see AppException
     */
    private ApplicationException(String message) {
        super(message);
    }

    /**
     * Wyjątek rzucany gdy nie znaleziono zgłoszenia lub zgłoszenie nie należy do danego kandydata
     * @return ApplicationException z informacją o przyczynie wystąpienia
     */
    static public ApplicationException applicationNotFoundException() {
        return new ApplicationException(ERROR.APPLICATION_NOT_FOUND.toString());
    }

    /**
     * Wyjątek rzucany gdy nie znaleziono zgłoszenia lub zgłoszenie nie należy do danego kandydata
     * @return ApplicationException z informacją o przyczynie wystąpienia
     * @param e wyjątek typu NoResultException
     */
    static public ApplicationException applicationNotFoundException(NoResultException e) {
        return new ApplicationException(ERROR.APPLICATION_NOT_FOUND.toString(), e);
    }

    /**
     * Wyjątek rzucany w przypadku próbu usunięcia zgłoszenia, które zostało już ocenione
     * @return ApplicationException z informacją o przyczynie wystąpienia
     */
    static public ApplicationException applicationHasReviewException() {
        return new ApplicationException(ERROR.APPLICATION_HAS_REVIEW.toString());
    }

    /**
     * Wyjątek rzucany gdy wprowadzono niepoprawny list motywacyjny
     * @return ApplicationException z informacją o przyczynie wystąpienia
     */
    static public ApplicationException incorrectMotivationalLetterException() {
        return new ApplicationException(ERROR.INCORRECT_MOTIVATIONAL_LETTER.toString());
    }

    /**
     * Wyjątek rzucany gdy wprowadzono niepoprawny numer identyfikacyjny badania lekarskiego
     * @return ApplicationException z informacją o przyczynie wystąpienia
     */
    static public ApplicationException incorrectExaminationCodeException() {
        return new ApplicationException(ERROR.INCORRECT_EXAMINATION_CODE.toString());
    }

    /**
     * Wyjątek rzucany gdy wprowadzono niepoprawną wagę
     * @return ApplicationException z informacją o przyczynie wystąpienia
     */
    static public ApplicationException incorrectWeightException() {
        return new ApplicationException(ERROR.INCORRECT_WEIGHT.toString());
    }
    
    /**
     * Wyjątek rzucany, gdy oferta do której próbujemy dodać zgłoszenie jest zamknięta
     * @return ApplicationException z informacją o przyczynie wystąpienia
     */
    static public ApplicationException offerClosedException() {
        return new ApplicationException(ERROR.OFFER_CLOSED.toString());
    }
    
    /**
     * Wyjątek rzucany gdy kandydat próbuje zgłosić się ponownie do oferty
     * do której jest już zgłoszony
     * @return ApplicationException z informacją o przyczynnie wystąpienia
     */
    static public ApplicationException alreadyAppliedException() {
        return new ApplicationException(ERROR.ALREADY_APPLIED.toString());
    }
    
    /**
     * Wyjątek rzucany gdy kandydat próbuje zgłosić się do oferty,
     * która zmieniła się od czasu gdy użytkownik wczytał jej dane
     * @return ApplicationException z informacją o przyczynnie wystąpienia
     */
    static public ApplicationException offerHasChangedException() {
        return new ApplicationException(ERROR.OFFER_HAS_CHANGED.toString());
    }
}
