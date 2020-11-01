package pl.lodz.p.it.ssbd2020.ssbd03.mol.facades;

import org.apache.commons.lang3.NotImplementedException;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd03.core.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Candidate;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.CandidateException;
import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.applyToOffer;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.listOwnApplications;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class CandidateFacade extends AbstractFacade<Candidate> implements CandidateFacadeLocal {

    @PersistenceContext(unitName = "pl.lodz.p.it.ssbd2020_ssbd03_molPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CandidateFacade() {
        super(Candidate.class);
    }

    @Override
    @RolesAllowed({listOwnApplications, applyToOffer})
    public Candidate findByEmail(String email) throws AppException {
        try {
            TypedQuery<Candidate> query = em.createQuery("select e from Candidate e where e.account.email = :email", Candidate.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (NoResultException e) {
            throw CandidateException.createNotfoundException();
        } catch (PersistenceException e) {
            throw CandidateException.createExceptionDatabaseQueryProblem(e);
        }
        throw AppException.createOtherException();

    }

}
