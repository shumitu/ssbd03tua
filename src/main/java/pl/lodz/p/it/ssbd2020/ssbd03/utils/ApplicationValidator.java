package pl.lodz.p.it.ssbd2020.ssbd03.utils;

/**
 * Klasa zawierająca walidatory dla pól formularzy powiązanych z encją Application
 */
public class ApplicationValidator {

    /**
     * Metoda sprawdza czy odnośnik do listu motywacyjnego jest poprawny pod względem składni i długości
     * @param motivationalLetter wprowadzony odnośnik do listu motywacyjnego podróży
     * @return boolean zwracający stan walidacji
     */
    public static boolean isMotivationalLetterValid(String motivationalLetter) {
        return motivationalLetter.strip().length() > 0
                && motivationalLetter.matches("[^&+%~<>$£¥•|_€@=;'-]+");
    }

    /**
     * Metoda sprawdza czy numer identyfikacyjny badania lekarskiego
     * jest poprawny pod względem składni i długości
     * @param examinationCode wprowadzony numer identyfikacyjny badania lekarskiego
     * @return boolean zwracający stan walidacji
     */
    public static boolean isExaminationCodeValid(String examinationCode) {
        return examinationCode.strip().length() > 0
                && examinationCode.strip().length() < 1024
                && examinationCode.matches("[^&+%~<>$£¥•|_€@=;'-]+");
    }


    /**
     * Metoda sprawdza czy podana waga jest nieujemna
     * @param weight podana waga podlegająca walidacji
     * @return boolean zwracający stan walidacji
     */
    public static boolean isWeightValid(double weight) { return weight > 0; }
}
