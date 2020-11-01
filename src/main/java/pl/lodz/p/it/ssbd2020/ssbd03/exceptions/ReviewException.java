package pl.lodz.p.it.ssbd2020.ssbd03.exceptions;

import javax.persistence.NoResultException;

/**
 * Wyjątki związane ze statusem zgłoszeń
 */
public class ReviewException extends AppException {
    /**
     * Konstruktor
     * @param message komunikat błedu
     * @param cause   błąd który wystąpił w aplikacji
     * @see AppException
     */
    private ReviewException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Konstruktor
     * @param message komunikat błedu
     * @see AppException
     */
    private ReviewException(String message) {
        super(message);
    }

    /**
     * Wyjątek rzucany gdy nie znaleziono oceny zgłoszenia
     * @return ReviewException z komunikatem o nieodnalezieniu statusu zgłoszenia
     */
    static public ReviewException reviewNotFoundException() {
        return new ReviewException(ERROR.REVIEW_NOT_FOUND.toString());
    }

    /**
     * Wyjątek rzucany gdy nie znaleziono oceny zgłoszenia
     * @return ReviewException
     * @param e wyjątek typu NoResultException
     * @return ReviewException z komunikatem o nieodnalezieniu statusu zgłoszenia
     */
    static public ReviewException reviewNotFoundException(NoResultException e) {
        return new ReviewException(ERROR.REVIEW_NOT_FOUND.toString(), e);
    }

    /**
     * Wyjątek rzucany gdy zgłoszenie zostało już ocenione
     * @return ReviewException z komunikatem o już istnieniu recenzji
     */
    static public ReviewException reviewAlreadyExistsException() {
        return new ReviewException(ERROR.REVIEW_ALREADY_EXISTS.toString());
    }

    /**
     * Wyjątek rzucany gdy zatwierdzenie recenzji spowoduje przekroczenie limitu wagi
     *
     * @return ReviewException z komunikatem o przekroczeniu maksymalnej,
     * dopuszczanej wagi, dla danej oferty
     */

    static public ReviewException reviewExceedsWeightLimit() {
        return new ReviewException(ERROR.REVIEW_EXCEEDS_WEIGHT_LIMIT.toString());
    }

    /**
     * Wyjątek rzucany, kiedy dojdzie do próby oceny aplikacji z nieaktualną wersją
     *
     * @return ReviewException z komunikatem o konieczności ponownej weryfikacji danych,
     */

    static public ReviewException reviewApplicationChanged() {
        return new ReviewException(ERROR.REVIEW_APPLICATION_CHANGED.toString());
    }
}
