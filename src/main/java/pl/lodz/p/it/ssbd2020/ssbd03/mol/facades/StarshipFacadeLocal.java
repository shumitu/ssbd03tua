
package pl.lodz.p.it.ssbd2020.ssbd03.mol.facades;

import pl.lodz.p.it.ssbd2020.ssbd03.entities.Offer;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Starship;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface StarshipFacadeLocal {

    void create(Starship starship) throws AppException;

    void edit(Starship starship) throws AppException;

    void remove(Starship starship) throws AppException;

    Starship findById(long id) throws AppException;

    Starship find(Object id);

    List<Starship> findAll();

    List<Starship> findAllActiveStarships();

    List<Starship> findRange(int[] range);

    int count();

    void lockPessimisticRead(Starship starship) throws AppException;
}
