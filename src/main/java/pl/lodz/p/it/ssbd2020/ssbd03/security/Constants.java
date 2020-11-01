package pl.lodz.p.it.ssbd2020.ssbd03.security;

import pl.lodz.p.it.ssbd2020.ssbd03.security.utils.JWTCredential;

/**
 * Stałe powiązane z uwierzytelnieniem i autoryzacją.
 */
public final class Constants {

    /**
     * Przypadek użycia logowanie
     */

    public static final String logIn = "LOGIN";

    /**
     * Przypadek użycia wyświetlenia listy kont
     */

    public static final String listAccounts = "LIST-ACCOUNTS";

    /**
     * Przypadek użycia wyświetlenia raportów dla kont
     */

    public static final String listAccountsReports = "LIST-ACCOUNTS-REPORTS";

    /**
     * Przypadek użycia wyświetlenia szczegółów danego konta
     */

    public static final String displaySomeonesAccountDetails = "DISPLAY-SOMEONES-ACCOUNT-DETAILS";

    /**
     * Przypadek użycia wyświetlenia listy szczegółów własnego konta
     */

    public static final String displayOwnAccountDetails = "DISPLAY-OWN-ACCOUNT-DETAILS";

    /**
     * Przypadek użycia zmiany własnego hasła
     */

    public static final String changeOwnPassword = "CHANGE-OWN-PASSWORD";

    /**
     * Przypadek użycia zmiany hasła innego użytkownika
     */

    public static final String changeSomeonesPassword = "CHANGE-SOMEONES-PASSWORD";

    /**
     * Przypadek użycia edytowania danych własnego konta
     */

    public static final String editOwnAccount = "EDIT-OWN-ACCOUNT";

    /**
     * Przypadek użycia edytowania danych konta innego użytkownika
     */

    public static final String editSomeonesAccount = "EDIT-SOMEONES-ACCOUNT";

    /**
     * Przypadek użycia zmiany statusu aktywacji konta
     */

    public static final String changeActiveStatus = "CHANGE-ACTIVE-STATUS";

    /**
     * Przypadek użycia wysłania linku potwierdzającego
     */
    public static final String sendConfirmationLink = "SEND-CONFIRMATION-LINK";

    /**
     * Przypadek użycia dodawania poziomu dostępu
     */

    public static final String addAccessLevel = "ADD-ACCESS-LEVEL";

    /**
     * Przypadek użycia odbierania poziomu dostępu
     */

    public static final String revokeAccessLevel = "REVOKE-ACCESS-LEVEL";

    /**
     * Przypadek użycia zmiany poziomu dostępu
     */

    public static final String changeAccessLevel = "CHANGE-ACCESS-LEVEL";

    /**
     * SEKCJA MOL
     */

    /**
     * Przypadek użycia wyświetlania szczegółów oferty przez kandydata
     */

    public static final String displayOfferDetailsCandidate = "DISPLAY-OFFER-DETAILS-CANDIDATE";

    /**
     * Przypadek użycia wyświetlania szczegółów oferty przez pracownika
     */

    public static final String displayOfferDetailsEmployee = "DISPLAY-OFFER-DETAILS-EMPLOYEE";

    /**
     * Przypadek użycia dodania oferty
     */

    public static final String addOffer = "ADD-OFFER";

    /**
     * Przypadek użycia pobrania wszystkich ofert
     */

    public static final String getAllOffers = "GET-ALL-OFFERS";

    /**
     * Przypadek użycia usunęcia oferty
     */

    public static final String removeOffer = "REMOVE-OFFER";

    /**
     * Przypadek użycia edycji oferty
     */

    public static final String editOffer = "EDIT-OFFER";

    /**
     * Przypadek użycia otwarcia zgłoszeń do oferty
     */

    public static final String openOffer = "OPEN-OFFER";

    /**
     * Przypadek użycia zamknięcia oferty
     */

    public static final String closeOffer = "CLOSE-OFFER";

    /**
     * Przypadki użycia ukrycia i upublicznienia oferty
     */

    public static final String changeOfferVisibility = "CHANGE-OFFER-VISIBILITY";

    /**
     * Przypadek użycia składania aplikacji na ofertę
     */

    public static final String applyToOffer = "APPLY-TO-OFFER";

    /**
     * Przypadek użycia wyświetlania listy wszystkich zgłoszeń
     */

    public static final String listApplications = "LIST-APPLICATIONS";

    /**
     * Przypadek użycia wyświetlania listy własnych zgłoszeń
     */

    public static final String listOwnApplications = "LIST-OWN-APPLICATIONS";

    /**
     * Przypadek użycia przypisywania zgłoszenia do kategorii
     */

    /**
     * Przypadek użycia pobrania wszystkich statków
     */
    public static final String getAllStarships = "GET-ALL-STARSHIPS";

    public static final String addApplicationToCategory = "ADD-TO-CATEGORY";

    /**
     * Przypadek użycia wyświetlania szczegółów statku przez pracownika
     */

    public static final String displayStarshipDetails = "DISPLAY-STARSHIP-DETAILS";

    /**
     * Przypadek użycia dodania nowego statku
     */

    public static final String addStarship = "ADD-STARSHIP";

    /**
     * Przypadek użycia edycji statku
     */

    public static final String editStarship = "EDIT-STARSHIP";

    /**
     * Przypadek użycia przypisania statku do oferty
     */

    public static final String assignStarshipToOffer = "ASSIGN-STARSHIP-TO-OFFER";

    /**
     * Przypadek użycia zmiany statusu statku
     */

    public static final String changeStarshipActiveStatus = "CHANGE-STARSHIP-ACTIVE-STATUS";

    /**
     * Przypadek użycia edycji własnego zgłoszenia
     */

    public static final String editApplication = "EDIT-APPLICATION";

    /**
     * Przypadek użycia anulowania swojego zgłoszenia
     */

    public static final String cancelApplication = "CANCEL-APPLICATION";

    /**
     * Przypadek użycia sprawdzenia statusu swojego zgłoszenia
     */

    public static final String checkReview = "CHECK-REVIEW";

    public static final String checkMyReview = "CHECK-MY-REVIEW";

    /**
     * Przypadek użycia wyświetlenia szczegółów danego zgłoszenia
     */

    public static final String displayApplicationDetails = "DISPLAY-APPLICATION-DETAILS";

    public static final String displayMyApplicationDetails = "DISPLAY-MY-APPLICATION-DETAILS";

    /**
     * SEKCJA USTAWIEŃ
     */

    /**
     * Początek nagłowka Authorization.
     */
    public static final String BEARER = "Bearer ";

    /**
     * Nagłowek Authorization.
     */
    public static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * Klucz dla zbioru ról użytkownika.
     */
    public static final String AUTHORITIES_KEY = "auth";

    /**
     * Klucz dla pola wymuszonej zmiany hasła.
     */
    public static final String FORCE_PASSWORD_CHANGE = "FORCE_PASSWORD_CHANGE";

    /**
     * Obiekt {@link JWTCredential} dla użytkownika niezalogowanego.
     */
    public static final JWTCredential GUEST = new JWTCredential("GUEST");

}
