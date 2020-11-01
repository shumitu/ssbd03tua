package pl.lodz.p.it.ssbd2020.ssbd03.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd03.entities.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AccessLevelFacadeLocal {

    void create(AccessLevel accessLevel) throws AppException;

    void edit(AccessLevel accessLevel) throws AppException;

    void remove(AccessLevel accessLevel)  throws AppException;

    AccessLevel find(Object id);

    List<AccessLevel> findAll();

    List<AccessLevel> findRange(int[] range);

    int count();

    List<AccessLevel> findByAccount(Account account);
    
}
