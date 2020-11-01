package pl.lodz.p.it.ssbd2020.ssbd03.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd03.entities.Candidate;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CandidateFacadeLocal {

    void create(Candidate candidate) throws AppException;

    void edit(Candidate candidate) throws AppException;

    void remove(Candidate candidate) throws AppException;

    Candidate find(Object id);

    List<Candidate> findAll();

    List<Candidate> findRange(int[] range);

    int count();

    public Candidate findById(Long id) throws AppException;
    
}
