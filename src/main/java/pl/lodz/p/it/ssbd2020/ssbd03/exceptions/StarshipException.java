package pl.lodz.p.it.ssbd2020.ssbd03.exceptions;

/**
 * Wyjątki powiązane z encją Starship
 */
public class StarshipException extends AppException {

    /**
     * Konstruktor
     *
     * @param message komunikat błedu
     * @param cause   błąd który wystąpił w aplikacji
     * @see AppException
     */
    protected StarshipException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Konstruktor
     *
     * @param message komunikat błedu
     * @see AppException
     */
    protected StarshipException(String message) {
        super(message);
    }


    /**
     * Wyjątek rzucany gdy jakaś z wymaganych wartości jest niedodatnia
     *
     * @return StarshipException z komunikatem o wartości niedodatniej
     */
    static public StarshipException createNotPositiveValueException() {
        return new StarshipException(ERROR.NOT_POSITIVE_VALUE.toString());
    }

    /**
     * Wyjątek rzucany gdy rok produkcji nie spełnia ograniczeń
     *
     * @return StarshipException z komunikatem o błędnym roku produkcji
     */
    static public StarshipException createWrongYearOfManufactureException() {
        return new StarshipException(ERROR.WRONG_YEAR.toString());
    }

    /**
     * Wyjątek rzucany gdy nazwa statku jest zajęta
     *
     * @return StarshipException z komunikatem o zajętej nazwie statku
     */
    static public StarshipException createStarshipNameTakenException() {
        return new StarshipException(ERROR.STARSHIP_NAME_TAKEN.toString());
    }

    /**
     * Wyjątek rzucany gdy nazwa statku jest za długa
     *
     * @return StarshipException z komunikatem o za długiej nazwie statku
     */
    static public StarshipException createStarshipNameTooLongException() {
        return new StarshipException(ERROR.STARSHIP_NAME_TOO_LONG.toString());
    }

    /**
     * Wyjątek rzucany gdy nazwa statku jest za krótka
     *
     * @return StarshipException z komunikatem o za krótkiej nazwie statku
     */
    static public StarshipException createStarshipNameTooShortException() {
        return new StarshipException(ERROR.STARSHIP_NAME_TOO_SHORT.toString());
    }

    /**
     * Wyjątek rzucany gdy nazwa statku zawiera niepoprawne znaki
     *
     * @return StarshipException z komunikatem o niepoprawnej nazwie statku
     */
    static public StarshipException createStarshipNameIncorrectException() {
        return new StarshipException(ERROR.STARSHIP_NAME_INCORRECT.toString());
    }

    /**
     * Wyjątek rzucany gdy statek jest już aktywny
     *
     * @return StarshipException z komunikatem o aktywnym statku
     */
    static public StarshipException createStarshipAlreadyActiveException() {
        return new StarshipException(ERROR.STARSHIP_ALREADY_ACTIVE.toString());
    }

    /**
     * Wyjątek rzucany gdy statek jest już nieaktywny
     *
     * @return StarshipException z komunikatem o nieaktywnym statku
     */
    static public StarshipException createStarshipAlreadyInActiveException() {
        return new StarshipException(ERROR.STARSHIP_ALREADY_INACTIVE.toString());
    }

    /**
     * Wyjątek rzucany gdy statek o danym id nie istnieje
     *
     * @return StarshipException z komunikatem o nieistniejącym statku
     */
    static public StarshipException createStarshipNotFoundException() {
        return new StarshipException(ERROR.STARSHIP_NOT_FOUND.toString());
    }

    /**
     * Wyjątek rzucany gdy statek przypisywany do oferty nie jest aktywny
     *
     * @return StarshipException z komunikatem o nieaktywnym statku
     */
    static public StarshipException createStarshipNotOperationalException() {
        return new StarshipException(ERROR.STARSHIP_NOT_OPERATIONAL.toString());
    }

    /**
     * Wyjątek rzucany gdy statek przypisywany do oferty jest przypisany do innej oferty w danej chwili czasu
     *
     * @return StarshipException z komunikatem o przypisanym statku
     */
    static public StarshipException createStarshipAlreadyAssignedException() {
        return new StarshipException(ERROR.STARSHIP_ALREADY_ASSIGNED.toString());
    }

    /**
     * Wyjątek rzucany gdy statek przypisywany do oferty jest już do niej przypisany
     *
     * @return StarshipException z komunikatem o przypisanym statku
     */
    static public StarshipException createStarshipSelfAssignedException() {
        return new StarshipException(ERROR.STARSHIP_ASSIGNED_SELF.toString());
    }

    /**
     * Wyjątek rzucany jeśli edytowany statek jest przypisany do oferty
     *
     * @return StarshipException z komunikatem o statku przypisanym do oferty
     */
    static public StarshipException createStarshipAssignedToOfferException() {
        return new StarshipException(ERROR.STARSHIP_ASSIGNED_TO_OFFER.toString());
    }


}
