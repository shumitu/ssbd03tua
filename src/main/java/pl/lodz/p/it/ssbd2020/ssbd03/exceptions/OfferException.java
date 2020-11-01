package pl.lodz.p.it.ssbd2020.ssbd03.exceptions;

import javax.persistence.NoResultException;

/**
 * Wyjątki powiązane z encją Offer
 */
public class OfferException extends AppException {

    /**
     * Konstruktor
     * @param message komunikat błedu
     * @param cause   błąd który wystąpił w aplikacji
     * @see AppException
     */
    OfferException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Konstruktor
     * @param message komunikat błedu
     * @see AppException
     */
    public OfferException(String message) {
        super(message);
    }

    /**
     * Wyjątek rzucany przy próbie zamknięcia oferty która jest już zamknięta
     * @return OfferException z komunikatem o braku poziomu dostępu u użytkownika
     */
    static public OfferException createExceptionOfferAlreadyClosed(){
        return new OfferException(ERROR.OFFER_ALREADY_CLOSED.toString());
    }
    
    /**
     * Wyjątek rzucany przy próbie otwarcia oferty która jest już otwarta
     * 
     * @return OfferException z komunikatem o tym, że oferta jest już otwarta
     */
    static public OfferException createExceptionOfferAlreadyOpen() {
        return new OfferException(ERROR.OFFER_ALREADY_OPEN.toString());
    }

    /**
     * Wyjątek rzucany przy próbie usunięcia oferty która ma już jakieś zgłoszenia
     * @return OfferException z komunikatem o niemożności usunięcia oferty
     */
    static public OfferException createExceptionOfferHasApplications(){
        return new OfferException(ERROR.OFFER_HAS_APPLICATIONS_DELETE.toString());
    }

    /**
     * Wyjątek rzucany przy próbie usunięcia oferty która nie jest ukryta
     * @return OfferException z komunikatem o niemożności usunięcia oferty
     */
    static public OfferException createExceptionCannotDeleteIsNotHidden(){
        return new OfferException(ERROR.OFFER_DELETE_IS_NOT_HIDDEN.toString());
    }

    /**
     * Wyjątek rzucany przy próbie usunięcia oferty która jest otwarta
     * @return OfferException z komunikatem o niemożności usunięcia oferty
     */
    static public OfferException createExceptionCannotDeleteIsOpen(){
        return new OfferException(ERROR.OFFER_DELETE_IS_OPEN.toString());
    }

    /**
     * Wyjątek rzucany gdy wpis w tabeli Offer nie zostanie znaleziony w bazie
     *
     * @param e wyjątek typu NoResultException
     * @return OfferException z komunikatem o nieodnalezieniu oferty
     */
    static public OfferException createExceptionOfferNotExists(NoResultException e) {
        return new OfferException(ERROR.OFFER_NOT_FOUND.toString(), e);
    }


    /**
     * Wyjątek rzucany przy próbie ukrycia oferty, która jest już ukryta
     *
     * @return OfferException z komunikatem, mówiącym że oferta jest już ukryta
     */

    static public OfferException createExceptionAlreadyHidden() {
        return new OfferException(ERROR.OFFER_ALREADY_HIDDEN.toString());
    }

    /**
     * Wyjątek rzucany przy próbie upublicznienia oferty, która jest już publiczna
     *
     * @return OfferException z komunikatem, mówiącym że oferta jest już upublicziona
     */
    static public OfferException createExceptionAlreadyVisible() {
        return new OfferException(ERROR.OFFER_ALREADY_VISIBLE.toString());
    }

    /**
     * Wyjątek rzucany gdy podano nieprawidłowy składniowo opis oferty
     * @return OfferException z komunikatem o błednym opisie
     */
    static public OfferException createExceptionIncorrectDescription(){
        return new OfferException(ERROR.INCORRECT_DESCRIPTION.toString());
    }

    /**
     * Wyjątek rzucany gdy podano nieprawidłowy składniowo cel podróży
     * @return OfferException z komunikatem o błędnym celu podróży
     */
    static public OfferException createExceptionIncorrectDestination(){
        return new OfferException(ERROR.INCORRECT_DESTINATION.toString());
    }

    /**
     * Wyjątek rzucany w przypadku gdy data rozpoczęcia lotu poprzedza datę zakończenia lotu
     * @return OfferException z komunikatem o złej kolejności dat
     */
    static public OfferException createExceptionIncorrectDatesOrder(){
        return new OfferException(ERROR.INCORRECT_DATES_ORDER.toString());
    }

    /**
     * Wyjątek rzucany gdy data rozpoczęcia jest nieprawidłowa
     * @return OfferException z komunikatem o nieprawidłowej dacie rozpoczęcia
     */
    static public OfferException createExceptionIncorrectStartDate(){
        return new OfferException(ERROR.INCORRECT_START_DATE.toString());
    }

    /**
     * Wyjątek rzucany gdy data zakończenia jest nieprawidłowa
     * @return OfferException z komunikatem o nieprawidłowej dacie zakończenia
     */
    static public OfferException createExceptionIncorrectEndDate(){
        return new OfferException(ERROR.INCORRECT_END_DATE.toString());
    }

    /**
     * Wyjątek rzucany gdy podana cena jest nieprawidłowa
     * @return OfferException z komunikatem o nieprawidłowej cenie
     */
    static public OfferException createExceptionIncorrectPrice(){
        return new OfferException(ERROR.INCORRECT_PRICE.toString());
    }

    /**
     * Wyjątek rzucany gdy podany łączny koszt jest nieprawidłowy
     * @return OfferException z komunikatem o nieprawidłowym koszcie
     */
    static public OfferException createExceptionIncorrectTotalCost(){
        return new OfferException(ERROR.INCORRECT_TOTAL_COST.toString());
    }

    /**
     * Wyjątek rzucany gdy podane id statku jest nieprawidłowe
     * @return OfferException z komunikatem o nieprawidłowym id
     */
    static public OfferException createExceptionIncorrectStarshipId(){
        return new OfferException(ERROR.INCORRECT_STARSHIP_ID.toString());
    }

    /**
     * Wyjątek rzucany gdy wybrany statek został w międzyczasie dezaktywowany
     * @return OfferException z komunikatem o zmianie stanu statku
     */
    static public OfferException createExceptionStarshipNotOperational(){
        return new OfferException(ERROR.INCORRECT_STARSHIP_STATUS.toString());
    }

    /**
     * Wyjątek rzucany gdy odnaleziono nakładającą się ofertę
     * @return OfferException z odpowiednim komunikatem
     */
    static public OfferException createExceptionOverlappingOfferFound(){
        return new OfferException(ERROR.OVERLAPPING_OFFER.toString());
    }

    /**
     * Wyjątek rzucany gdy następuje próba edycji oferty posiadającej zgłoszenia
     * @return OfferException z odpowiednim komunikatem o braku możliwości edycji
     */
    static public OfferException editExceptionHasApplications(){
        return new OfferException(ERROR.OFFER_HAS_APPLICATIONS_EDIT.toString());
    }

    /**
     * Wyjątek rzucany gdy następuje próba przypisania statku do oferty która ma zgłoszenia
     * @return OfferException z odpowiednim komunikatem o braku możliwości przypisania statku do oferty
     */
    static public OfferException assignStarshipExceptionHasApplications(){
        return new OfferException(ERROR.OFFER_HAS_APPLICATIONS_ASSIGN_SHIP.toString());
    }

}
