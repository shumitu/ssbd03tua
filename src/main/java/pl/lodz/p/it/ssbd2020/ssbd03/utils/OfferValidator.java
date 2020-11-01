package pl.lodz.p.it.ssbd2020.ssbd03.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Klasa zawierająca walidatory dla pól formularzy powiązanych z encją Offer
 */
public class OfferValidator {

    /**
     * Metoda sprawdza czy data zakończenia lotu jest późniejsza od daty rozpoczęcia lotu
     * @param startDate data rozpoczęcia lotu
     * @param endDate data zakończenia lotu
     * @return boolean zwracający stan walidacji
     */
    public static boolean isEndDateAfterStartDate(Date startDate, Date endDate) {
        return endDate.after(startDate);
    }

    /**
     * Metoda sprawdza czy data rozpoczęcia lotu co najmniej poprzedza wczorajszy dzień
     * @param startDate data rozpoczęcia lotu
     * @return boolean zwracający stan walidacji
     */
    public static boolean isStartDateValid(Date startDate) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date yesterday = cal.getTime();
        return startDate.after(yesterday);
    }

    /**
     * Metoda sprawdza czy data zakończenia lotu co najmniej poprzedza wczorajszy dzień
     * @param endDate data zakończenia lotu
     * @return boolean zwracający stan walidacji
     */
    public static boolean isEndDateValid(Date endDate) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date yesterday = cal.getTime();
        return endDate.after(yesterday);
    }

    /**
     * Metoda sprawdza czy cel jest poprawny pod względem składni i długości
     * @param destination wprowadzony cel podróży
     * @return boolean zwracający stan walidacji
     */
    public static boolean isDestinationValid(String destination) {
        return destination.strip().length() > 0
                && destination.strip().length() < 257
                && destination.matches("[^&;'-=+%~<>$£¥•|_€@]*");
    }

    /**
     * Metoda sprawdza czy opis jest poprawny pod względem składni i minimalnej długości
     * @param description opis oferty
     * @return boolean zwracający stan walidacji
     */
    public static boolean isDescriptionValid(String description) {
        return description.strip().length() > 0
                && description.matches("[^&;'-=+%~<>$£¥•|_€@]*");
    }

    /**
     * Metoda sprawdza czy podana cena jest nieujemna
     * @param price podana cena podlegająca walidacji
     * @return boolean zwracający stan walidacji
     */
    public static boolean isPriceValid(int price) { return price > 0; }

    /**
     * Metoda sprawdza czy podany koszt jest poprawny
     * @param totalPrice podany koszt przewoźnika
     * @return boolean zwracający stan walidacji
     */
    public static boolean isTotalCostValid(long totalPrice) { return totalPrice > 0; }

    /**
     * Metoda sprawdza czy waga całkowita jest nieujemna
     * @param totalWeight podana waga całkowita
     * @return boolean zwracający stan walidacji
     */
    public static boolean isTotalWeightValid(double totalWeight) { return totalWeight > 0; }

    /**
     * Metoda sprawdza czy id statku jest poprawne
     * @param starshipId id statku
     * @return boolean zwracający stan walidacji
     */
    public static boolean isStarshipIdValid(long starshipId) { return starshipId >= 0; }
}
