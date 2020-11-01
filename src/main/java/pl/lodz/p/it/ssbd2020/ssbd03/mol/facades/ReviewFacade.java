
package pl.lodz.p.it.ssbd2020.ssbd03.mol.facades;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;

import org.apache.commons.lang3.NotImplementedException;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Application;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Offer;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Review;
import pl.lodz.p.it.ssbd2020.ssbd03.core.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.*;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.ReviewException;
import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;

import java.util.List;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.*;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class ReviewFacade extends AbstractFacade<Review> implements ReviewFacadeLocal {

    @PersistenceContext(unitName = "pl.lodz.p.it.ssbd2020_ssbd03_molPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReviewFacade() {
        super(Review.class);
    }


    @Override
    @RolesAllowed({checkReview, checkMyReview, listApplications, listOwnApplications})
    public Review findByApplication(Application application) throws AppException, NotYetReviewedException {
        try {
            TypedQuery<Review> tq = getEntityManager().createNamedQuery("Review.findByApplication", Review.class);
            tq.setParameter("application", application);
            return tq.getSingleResult();
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (NoResultException e) {
            throw NotYetReviewedException.reviewNotFoundException(e);
        } catch (PersistenceException e) {
            throw ReviewException.createExceptionDatabaseQueryProblem(e);
        }
        throw AppException.createOtherException();
    }

    @Override
    @RolesAllowed({addApplicationToCategory})
    public List<Review> findByOffer(Offer offer) throws AppException {
        try {
            TypedQuery<Review> tq = em.createQuery("select e from Review e where e.application.offer = :offer", Review.class);
            tq.setParameter("offer", offer);
            return tq.getResultList();
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (PersistenceException e) {
            throw ReviewException.createExceptionDatabaseQueryProblem(e);
        }
        throw AppException.createOtherException();
    }


    @Override
    @RolesAllowed({editApplication, addApplicationToCategory, cancelApplication})
    public boolean checkIfReviewExists(Application application) throws AppException {
        try {
            TypedQuery<Review> tq = getEntityManager().createNamedQuery("Review.findByApplication", Review.class);
            tq.setParameter("application", application);
            return tq.getResultList().size() != 0;
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (PersistenceException e) {
            throw ReviewException.createExceptionDatabaseQueryProblem(e);
        }
        throw AppException.createOtherException();
    }

    @Override
    @RolesAllowed({addApplicationToCategory})
    public void create(Review review) throws AppException {
        try {
            super.create(review);
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (PersistenceException e) {
            Throwable cause = e.getCause();
            throw AppException.createExceptionDatabaseQueryProblem(cause);
        }
    }
}
