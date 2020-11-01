package pl.lodz.p.it.ssbd2020.ssbd03.mol.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Category;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

@Local
public interface CategoryFacadeLocal {

    void create(Category category) throws AppException;

    void edit(Category category) throws AppException;

    void remove(Category category) throws AppException;

    Category find(Object id);

    List<Category> findAll();

    List<Category> findRange(int[] range);

    Category findByName(String name) throws AppException;

    int count();
    
}
