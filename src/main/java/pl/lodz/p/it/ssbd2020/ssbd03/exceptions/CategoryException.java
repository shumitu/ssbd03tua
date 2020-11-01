package pl.lodz.p.it.ssbd2020.ssbd03.exceptions;

import javax.persistence.NoResultException;

public class CategoryException extends AppException {
    /**
     * Konstruktor
     *
     * @param message komunikat błedu
     * @param cause   błąd który wystąpił w aplikacji
     * @see AppException
     */
    private CategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Konstruktor
     *
     * @param message komunikat błedu
     * @see AppException
     */
    private CategoryException(String message) {
        super(message);
    }

    /**
     * Wyjątek rzucany gdy nie znaleziono kategorii
     * @return CategoryException z komunikatem o nieodnalezieniu danej kategorii
     */
    static public CategoryException categoryNotFoundException() {
        return new CategoryException(ERROR.CATEGORY_NOT_FOUND.toString());
    }

    /**
     * Wyjątek rzucany gdy nie znaleziono kategorii
     * @param e wyjątek typu NoResultException
     * @return CategoryException z komunikatem o nieodnalezieniu danej kategorii
     */
    static public CategoryException categoryNotFoundException(NoResultException e) {
        return new CategoryException(ERROR.CATEGORY_NOT_FOUND.toString(), e);
    }
}
