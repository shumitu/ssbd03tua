package pl.lodz.p.it.ssbd2020.ssbd03.mok.facades;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd03.core.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Candidate;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.CandidateException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.ConcurrencyException;
import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.*;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class CandidateFacadeMok extends AbstractFacade<Candidate> implements CandidateFacadeLocal {

    @PersistenceContext(unitName = "pl.lodz.p.it.ssbd2020_ssbd03_mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


    public CandidateFacadeMok() {
        super(Candidate.class);
    }

    @Override
    @PermitAll
    public void create(Candidate candidate) throws AppException {
        super.create(candidate);
    }

    @Override
    @RolesAllowed({editOwnAccount, editSomeonesAccount})
    public void edit(Candidate candidate) throws AppException {
        try {
            super.edit(candidate);
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (OptimisticLockException e) {
            throw ConcurrencyException.createResourceModifiedException();
        } catch (PersistenceException e) {
            Throwable cause = e.getCause();
            throw AppException.createExceptionDatabaseQueryProblem(cause);
        }
    }

    @Override
    @RolesAllowed({displayOwnAccountDetails, displaySomeonesAccountDetails, editSomeonesAccount, editOwnAccount})
    public Candidate findById(Long id) throws AppException {
        try {
            TypedQuery<Candidate> tq = getEntityManager().createNamedQuery("Candidate.findById", Candidate.class);
            tq.setParameter("id", id);
            return tq.getSingleResult();
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (NoResultException e) {
            throw CandidateException.createExceptionCandidateNotExists(e);
        } catch (PersistenceException e) {
            throw CandidateException.createExceptionDatabaseQueryProblem(e);
        }
        throw AppException.createOtherException();
    }
    
}
