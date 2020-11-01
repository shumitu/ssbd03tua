package pl.lodz.p.it.ssbd2020.ssbd03.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd03.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.PasswordResetToken;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface PasswordResetTokenFacadeLocal {

    void create(PasswordResetToken passwordResetToken) throws AppException;

    void edit(PasswordResetToken passwordResetToken) throws AppException;

    void remove(PasswordResetToken passwordResetToken) throws AppException;

    PasswordResetToken find(Object id);

    List<PasswordResetToken> findAll();

    List<PasswordResetToken> findRange(int[] range);

    int count();

    /**
     * Metoda sprawdza czy użytkownik posiada już aktywny token zmiany hasła.
     * @param account konto użytkownika.
     * @return true jeśli konto ma już token zmiany hasła.
     */
    boolean checkIfTokenExistForAccount(Account account);

    /**
     * Metoda usuwa wszystkie istniejące tokeny zmiany hasła dla podanego konta
     * @param account konto użytkownika.
     * @return liczba usuniętych rekordów z bazy danych.
     */
    int deleteExistingTokensForAccount(Account account) throws AppException;

    /**
     * Metoda zwraca encje o danej wartości tokenu zmiany hasła.
     * @param token token zmiany hasła.
     * @return encja tokenu zmiany hasła.
     */
    PasswordResetToken findByToken(String token) throws AppException;
    
}
