package pl.lodz.p.it.ssbd2020.ssbd03.mol.facades;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;

import org.apache.commons.lang3.NotImplementedException;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Application;
import pl.lodz.p.it.ssbd2020.ssbd03.core.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Candidate;

import java.util.List;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.ApplicationException;
import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.*;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class ApplicationFacade extends AbstractFacade<Application> implements ApplicationFacadeLocal {

    @PersistenceContext(unitName = "pl.lodz.p.it.ssbd2020_ssbd03_molPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ApplicationFacade() {
        super(Application.class);
    }

    @Override
    @RolesAllowed({displayMyApplicationDetails, displayApplicationDetails, editApplication, checkReview, checkMyReview,
            cancelApplication, addApplicationToCategory})
    public Application findById(long id) throws AppException {
        try {
            TypedQuery<Application> tq = getEntityManager().createNamedQuery("Application.findById", Application.class);
            tq.setParameter("id", id);
            return tq.getSingleResult();
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (NoResultException e) {
            throw ApplicationException.applicationNotFoundException(e);
        } catch (PersistenceException e) {
            throw ApplicationException.createExceptionDatabaseQueryProblem(e);
        }
        throw AppException.createOtherException();
    }

    /**
     * Metoda zwraca listę zgłoszeń utworzonych przez danego kandydata
     *
     * @param candidate encja kandydata, której zgłoszenia mają zostać zwrócone
     * @return Lista zawierająca encje typu Application
     */

    @Override
    @RolesAllowed({listOwnApplications})
    public List<Application> findByCandidate(Candidate candidate) throws AppException {
        try {
            TypedQuery<Application> tq = getEntityManager().createNamedQuery("Application.findByCandidate", Application.class);
            tq.setParameter("candidate", candidate);
            return tq.getResultList();
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (PersistenceException e) {
            throw ApplicationException.createExceptionDatabaseQueryProblem(e);
        }
        throw AppException.createOtherException();
    }

    @Override
    @RolesAllowed(applyToOffer)
    public void create(Application entity) throws AppException {
        try {
            super.create(entity);
        }
        catch (DatabaseException e){
            handleDataBaseException(e);
        }
        catch (PersistenceException e){
            Throwable cause = e.getCause();
            throw AppException.createExceptionDatabaseQueryProblem(cause);
        }
    }

    @Override
    @RolesAllowed(editApplication)
    public void edit(Application entity) throws AppException {
        try {
            super.edit(entity);
        }
        catch (DatabaseException e){
            handleDataBaseException(e);
        }
        catch (PersistenceException e){
            Throwable cause = e.getCause();
            throw AppException.createExceptionDatabaseQueryProblem(cause);
        }
    }

    @Override
    @RolesAllowed(cancelApplication)
    public void remove(Application entity) throws AppException {
        try {
            super.remove(entity);
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (PersistenceException e) {
            Throwable cause = e.getCause();
            throw AppException.createExceptionDatabaseQueryProblem(cause);
        }
    }

    @Override
    @RolesAllowed({addApplicationToCategory, editApplication, cancelApplication})
    public void lockPessimisticRead(Application entity) throws AppException {
        super.lockPessimisticRead(entity);

    }
}
