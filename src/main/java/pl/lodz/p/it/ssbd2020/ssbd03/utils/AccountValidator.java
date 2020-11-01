package pl.lodz.p.it.ssbd2020.ssbd03.utils;

/**
 * Klasa zawierająca walidatory dla pól formularzy powiązanych z encją Account
 */
public class AccountValidator {

    /**
     * Metoda sprawdza poprawność adresu email, jego strukturę oraz długość, a także czy w ciągu
     * znakowym występują niedozwolone znaki, mogące posłużyć w ataku sqli
     * @param email badany adres email
     * @return boolean zwracający stan walidacji
     */
    public static boolean isEmailValid(String email) {
        return email.strip().length() > 0
                && email.strip().length() < 255
                && email.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
                && email.matches("[^&;'=+%~<>$£¥•|_€]*");
    }

    /**
     * Metoda sprawdza czy podane hasło jest poprawne pod względem minimalnej długości
     * @param password badane hasło
     * @return boolean zwracający stan walidacji
     */
    public static boolean isPasswordLengthValid(String password) {
        return password.strip().length() >= 8;
    }

    /**
     * Metoda sprawdza czy podany adres ip jest poprawny pod względem długości a także typu adresu,
     * dopuszczane są adresy ipv4 oraz ipv6
     * @param ipAddress badany adres ip
     * @return boolean zwracający stan walidacji
     */
    public static boolean isIpAddressValid(String ipAddress) {
        return  ipAddress.strip().length() > 0
                && ipAddress.strip().length() < 40
                && (ipAddress.matches("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$")
    || ipAddress.matches("^([0-9A-Fa-f]{0,4}:){2,7}([0-9A-Fa-f]{1,4}$|((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|$)){4})$"));
    }

    /**
     * Metoda sprawdza czy podane imie jest poprawne pod względem długości oraz użytych znaków
     * @param firstName badane imię
     * @return boolean zwracający stan walidacji
     */
    public static boolean isFirstNameValid(String firstName) {
        return  firstName.strip().length() > 0
                && firstName.strip().length() < 17
                && firstName.matches("[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż]+")
                && firstName.matches("[^&;'-=+%~<>$£¥•|_€@]*");
    }

    /**
     * Metoda sprawdza czy podane nazwisko jest poprawne pod względem długości oraz użytych znaków
     * @param lastName badane nazwisko
     * @return boolean zwracający stan walidacji
     */
    public static boolean isLastNameValid(String lastName) {
        return  lastName.strip().length() > 0
                && lastName.strip().length() < 33
                && lastName.matches("[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż]+")
                && lastName.matches("[^&;'-=+%~<>$£¥•|_€@]*");
    }

    /**
     * Metoda sprawdza czy podane motto jest poprawne pod względem długości oraz użytych znaków
     * @param motto badane motto
     * @return boolean zwracający stan walidacji
     */
    public static boolean isMottoValid(String motto) {
        return motto.strip().length() > 0
                && motto.strip().length() < 129
                && motto.matches("[^&;'-=+%~<>$£¥•|_€@]*");
    }

    /**
     * Metoda sprawdza czy podana fraza jest poprawna pod względem długości oraz użytych znaków
     * @param phase badane nazwisko
     * @return boolean zwracający stan walidacji
     */
    public static boolean isFilterPhaseValid(String phase) {
        return  phase.strip().length() > 0
                && phase.strip().length() < 33
                && phase.matches("[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż]+")
                && phase.matches("[^&;'-=+%~<>$£¥•|_€@]*");
    }


}
