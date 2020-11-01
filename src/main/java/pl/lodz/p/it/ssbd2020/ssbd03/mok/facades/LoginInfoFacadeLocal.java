package pl.lodz.p.it.ssbd2020.ssbd03.mok.facades;

import java.util.List;
import javax.ejb.Local;

import pl.lodz.p.it.ssbd2020.ssbd03.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.LoginInfo;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

@Local
public interface LoginInfoFacadeLocal {

    void edit(LoginInfo loginInfo) throws AppException;

    void remove(LoginInfo loginInfo) throws AppException;

    LoginInfo find(Object id);

    List<LoginInfo> findAll();

    List<LoginInfo> findRange(int[] range);

    int count();

    /**
     * Metoda wyszukuje odpowiednią encję zawierającą dane logowania użytkownika
     * przy użyciu konretnego konta
     * @param account poszukiwane konto użytkownika
     * @return zwraca encję danych logowania
     * @throws AppException wyjątek aplikacyjny
     */
    LoginInfo findByAccount(Account account) throws AppException;
    
}
