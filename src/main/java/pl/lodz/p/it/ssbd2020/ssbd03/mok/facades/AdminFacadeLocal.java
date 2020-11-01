package pl.lodz.p.it.ssbd2020.ssbd03.mok.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Admin;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

@Local
public interface AdminFacadeLocal {

    void create(Admin admin) throws AppException;

    void edit(Admin admin) throws AppException;

    void remove(Admin admin) throws AppException;

    Admin find(Object id);

    List<Admin> findAll();

    List<Admin> findRange(int[] range);

    int count();
    
}
