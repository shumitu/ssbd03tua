package pl.lodz.p.it.ssbd2020.ssbd03.mol.facades;

import java.util.List;
import javax.ejb.Local;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.IdDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Application;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Candidate;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

@Local
public interface ApplicationFacadeLocal {

    void create(Application application) throws AppException;

    void edit(Application application) throws AppException;

    void remove(Application application) throws AppException;

    Application find(Object id);

    List<Application> findAll();

    List<Application> findRange(int[] range);

    int count();

    Application findById(long id) throws AppException;

    List<Application> findByCandidate(Candidate candidate) throws AppException;

    void lockPessimisticRead(Application application) throws AppException;
}
