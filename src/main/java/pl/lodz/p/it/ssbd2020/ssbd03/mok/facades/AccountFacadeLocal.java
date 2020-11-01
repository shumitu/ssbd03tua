package pl.lodz.p.it.ssbd2020.ssbd03.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd03.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AccountFacadeLocal {

    void create(Account account) throws AppException;

    void edit(Account account) throws AppException;

    void remove(Account account) throws AppException;

    Account find(Object id);

    List<Account> findAll();

    /**
     * Metoda pobiera wszystkie konta z podaną frazą w imieniu lub nazwisku
     * @param phrase fraza, która ma znajdować się w imieniu lub nazwisku
     * @return lista kont z podaną frazą
     */
    List<Account> findPhraseInName(String phrase);

    List<Account> findRange(int[] range);

    int count();

    /**
     * Metoda wyszukuje konto użytkownika po adresie email.
     * @param email email użytkownika.
     * @return Account zwraca encje konta użytkownika.
     * @throws AppException wyjątek aplikacyjny
     */
    Account findByEmail(String email) throws AppException;

    boolean checkIfAccountExists(String email) throws AppException;

    
}
