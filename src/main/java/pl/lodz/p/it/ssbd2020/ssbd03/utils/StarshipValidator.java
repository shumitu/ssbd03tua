package pl.lodz.p.it.ssbd2020.ssbd03.utils;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.StarshipDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.StarshipException;

/**
 * Klasa zawierająca walidatory dla pól formularzy powiązanych z encją Starship
 */
public class StarshipValidator {

    /**
     * Metoda sprawdza czy podana ilość załogi statku jest poprawna
     *
     * @param crewCapacity ilość załogi staku
     * @return boolean zwracający stan walidacji
     */
    private static boolean isCrewCapacityValid(short crewCapacity) {
        return crewCapacity > 0;
    }

    /**
     * Metoda sprawdza czy podana maksymalna waga pasażerów statku jest poprawna
     *
     * @param maximumWeight maksymalna waga pasażerów
     * @return boolean zwracający stan walidacji
     */
    private static boolean isMaximumWeightValid(double maximumWeight) {
        return maximumWeight > 0;
    }

    /**
     * Metoda sprawdza czy podana pojemność paliwa statku jest poprawna
     *
     * @param fuelCapacity pojemność paliwa
     * @return boolean zwracający stan walidacji
     */
    private static boolean isFuelCapacityValid(double fuelCapacity) {
        return fuelCapacity > 0;
    }

    /**
     * Metoda sprawdza czy podana maksymalna prędkość statku jest poprawna
     *
     * @param maximumSpeed maksymalna prędkość
     * @return boolean zwracający stan walidacji
     */
    private static boolean isMaximumSpeedValid(double maximumSpeed) {
        return maximumSpeed > 0;
    }

    /**
     * Metoda sprawdza czy podany rok produkcji statku jest poprawny
     *
     * @param yearOfManufacture rok produkcji
     * @return boolean zwracający stan walidacji
     */
    private static boolean isYearOfManufactureValid(short yearOfManufacture) {
        return yearOfManufacture > 2000;
    }

    /**
     * Metoda sprawdza czy podana nazwa statku zawiera niepoprawne znaki
     *
     * @param name nazwa staku
     * @return boolean zwracający stan walidacji
     */
    private static boolean isStarshipNameValid(String name) {
        return name.matches("[^&;'-=+%~<>$£¥•|_€@]*");
    }

    /**
     * Metoda sprawdza czy podana nazwa jest za długa
     *
     * @param name nazwa staku
     * @return boolean zwracający stan walidacji
     */
    private static boolean isStarshipNameTooLong(String name) {
        return name.strip().length() > 32;
    }

    /**
     * Metoda sprawdza czy podana nazwa jest za krótka
     *
     * @param name nazwa staku
     * @return boolean zwracający stan walidacji
     */
    private static boolean isStarshipNameTooShort(String name) {
        return name.strip().length() <= 0;
    }

    /**
     * Metoda sprawdza obiektDTO jest poprawny biznesowo
     *
     * @param starshipDTO nazwa staku
     * @throws StarshipException wyjątek rzucany gdy dane są niepoprawne
     */
    public static void validateStarshipDTO(StarshipDTO starshipDTO) throws StarshipException {
        if (StarshipValidator.isStarshipNameTooShort(starshipDTO.getName())) {
            throw StarshipException.createStarshipNameTooShortException();
        }
        if (StarshipValidator.isStarshipNameTooLong(starshipDTO.getName())) {
            throw StarshipException.createStarshipNameTooLongException();
        }
        if (!StarshipValidator.isStarshipNameValid(starshipDTO.getName())) {
            throw StarshipException.createStarshipNameIncorrectException();
        }
        if (!StarshipValidator.isYearOfManufactureValid(starshipDTO.getYearOfManufacture())) {
            throw StarshipException.createWrongYearOfManufactureException();
        }
        if (!(StarshipValidator.isCrewCapacityValid(starshipDTO.getCrewCapacity())
                && StarshipValidator.isFuelCapacityValid(starshipDTO.getFuelCapacity())
                && StarshipValidator.isMaximumWeightValid(starshipDTO.getMaximumWeight())
                && StarshipValidator.isMaximumSpeedValid(starshipDTO.getMaximumSpeed()))) {
            throw StarshipException.createNotPositiveValueException();
        }
    }
}
