package pl.lodz.p.it.ssbd2020.ssbd03.exceptions;

import javax.persistence.NoResultException;

/**
 * Wyjątki powiązane z encją Account
 */
public class AccountException extends AppException {

    /**
     * Konstruktor
     * @param message komunikat błedu
     * @param cause   błąd który wystąpił w aplikacji
     * @see AppException
     */
    protected AccountException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Konstruktor
     * @param message komunikat błedu
     * @see AppException
     */
    protected AccountException(String message) {
        super(message);
    }

    /**
     * Wyjątek rzucany gdy dane konto nie zostanie znalezione w bazie
     * @param e wyjątek typu NoResultException
     * @return AccountException z komunikatem o nie odnalezieniu konta
     */
    static public AccountException createExceptionAccountNotExists(NoResultException e) {
        return new AccountException(ERROR.ACCOUNT_NOT_FOUND.toString(), e);
    }

    /**
     * Wyjątek rzucany gdy hasło przesłane do aplikacji jest za krótkie
     * @return AppException z komunikatem o za krótkim haśle
     */
    static public AccountException createExceptionPasswordTooShort(){
        return new AccountException(ERROR.PASSWORD_TOO_SHORT.toString());
    }
    
    /**
     * Wyjątek rzucany gdy istnieje już konto z podanym adresem email
     * @return AppException z komunikatem o tym, że email jest już wykorzystany
     */
    static public AccountException createExceptionEmailTaken(){
        return new AccountException(ERROR.EMAIL_TAKEN.toString());
    }

    /**
     * Wyjątek rzucany gdy wprowadzono niepoprawny adres email
     * @return AppException z komunikatem o tym, że email jest niepoprawny
     */
    static public AccountException createExceptionIncorrectEmail(){
        return new AccountException(ERROR.INCORRECT_EMAIL.toString());
    }

    /**
     * Wyjątek rzucany gdy podczas rejestracji wprowadzono niepoprawne imię
     * @return AppException z komunikatem o tym, że podane imię podczas rejestracji jest nieprawidłowe
     */
    static public AccountException createExceptionIncorrectFirstName(){
        return new AccountException(ERROR.INCORRECT_FIRSTNAME.toString());
    }

    /**
     * Wyjątek rzucany gdy podczas rejestracji wprowadzono niepoprawne nazwisko
     * @return AppException z komunikatem o tym, że podane podczas rejestracji nazwisko jest nieprawidłowe
     */
    static public AccountException createExceptionIncorrectLastName(){
        return new AccountException(ERROR.INCORRECT_LASTNAME.toString());
    }

    /**
     * Wyjątek rzucany gdy podczas rejestracji podane zostało puste motto
     * @return AppException z komunikatem o tym, że podane podczas rejestracji motto jest puste
     */
    static public AccountException createExceptionIncorrectMotto(){
        return new AccountException(ERROR.INCORRECT_MOTTO.toString());
    }

    /**
     * Wyjątek rzucany gdy podane w procesie edycji danych konta motto jest puste
     * @return AppException z komunikatem o tym, że motto jest puste
     */
    static public AccountException updateExceptionIncorrectMotto(){
        return new AccountException(ERROR.INCORRECT_MOTTO.toString());
    }

    /**
     * Wyjątek rzucany gdy w procesie edycji danych konta wprowadzono niepoprawne imię
     * @return AppException z komunikatem o tym, że podane imię jest nieprawidłowe
     */
    static public AccountException updateExceptionIncorrectFirstName(){
        return new AccountException(ERROR.INCORRECT_FIRSTNAME.toString());
    }

    /**
     * Wyjątek rzucany gdy w procesie edycji danych konta wprowadzono niepoprawne nazwisko
     * @return AppException z komunikatem o tym, że podane nazwisko jest nieprawidłowe
     */
    static public AccountException updateExceptionIncorrectLastName(){
        return new AccountException(ERROR.INCORRECT_LASTNAME.toString());
    }

    /**
     * Wyjątek rzucany gdy wprowadzono niepoprawną frazę wyszukiwania
     * @return AppException z komunikatem o tym, że podana fraza jest nieprawidłowa
     */
    static public AccountException searchExceptionIncorrectPhrase(){
        return new AccountException(ERROR.INCORRECT_PHRASE.toString());
    }

    /**
     * Wyjątek rzucany gdy aktywacja konta sie nie udała
     * @return AppException z komunikatem o tym, że link aktywacyjny jest niepoprawny.
     */
    static public AccountException createExceptionActivationFailed() {
        return new AccountException(ERROR.ACTIVATION_FAILED.toString());
    }

    /**
     * Wyjątek rzucany gdy użytkownik jest już zablokowany
     * @return AppException z komunikatem że użytkownik jest już zablokowany.
     */
    static public AccountException blockExceptionAccountAlreadyBlocked(){
        return new AccountException(ERROR.ALREADY_BLOCKED.toString());
    }

    /**
     * Wyjątek rzucany gdy użytkownik jest już odblokowany
     * @return AppException z komunikatem że użytkownik jest już odblokowany.
     */
    static public AccountException blockExceptionAccountAlreadyUnblocked(){
        return new AccountException(ERROR.ALREADY_UNBLOCKED.toString());
    }

    /**
     * Wyjątek rzucany gdy użytkownik jest już potwierdzony
     * @return AppException z komunikatem że użytkownik jest już potwierdzony
     */
    static public AccountException confirmExceptionAlreadyConfirmed(){
        return new AccountException(ERROR.ALREADY_CONFIRMED.toString());
    }

    /**
     * Wyjątek rzucany gdy użytkownik rozpoczyna procedurę resetu hasła dla niepotwierdzonego konta.
     * @return AppException z komunikatem że użytkownik nie został potwierdzony
     */
    static public AccountException createExceptionAccountNotConfirmed(){
        return new AccountException(ERROR.NOT_CONFIRMED.toString());
    }
}
