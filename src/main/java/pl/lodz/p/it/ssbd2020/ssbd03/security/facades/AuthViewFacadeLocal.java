package pl.lodz.p.it.ssbd2020.ssbd03.security.facades;

import pl.lodz.p.it.ssbd2020.ssbd03.entities.AuthView;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AuthViewFacadeLocal {

    void create(AuthView authView) throws AppException;

    void edit(AuthView authView) throws AppException;

    void remove(AuthView authView) throws AppException;

    AuthView find(Object id);

    List<AuthView> findAll();

    List<AuthView> findRange(int[] range);

    int count();

    List<AuthView> findByEmail(String email);
    
}
