
package pl.lodz.p.it.ssbd2020.ssbd03.mol.facades;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd03.core.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Offer;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Starship;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.OfferException;
import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.*;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class OfferFacade extends AbstractFacade<Offer> implements OfferFacadeLocal {

    @PersistenceContext(unitName = "pl.lodz.p.it.ssbd2020_ssbd03_molPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OfferFacade() {
        super(Offer.class);
    }

    @Override
    @PermitAll
    public Offer findById(long id) throws AppException {
        try {
            TypedQuery<Offer> tq = getEntityManager().createNamedQuery("Offer.findById", Offer.class);
            tq.setParameter("id", id);
            return tq.getSingleResult();
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (NoResultException e) {
            throw OfferException.createExceptionOfferNotExists(e);
        } catch (PersistenceException e) {
            throw OfferException.createExceptionDatabaseQueryProblem(e);
        }
        throw AppException.createOtherException();
    }


    @Override
    @RolesAllowed(addOffer)
    public boolean checkIfOverlappingForAdding(Starship starship, Date startTime, Date endTime) throws AppException {
        try {
            TypedQuery<Long> tq = getEntityManager().createNamedQuery("Offer.findByOverlappingForAdding", Long.class);
            tq.setParameter("starship", starship);
            tq.setParameter("startTime", startTime);
            tq.setParameter("endTime", endTime);
            Long count = tq.getSingleResult();
            return (!count.equals(0L));
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        }  catch (PersistenceException e) {
            throw OfferException.createExceptionDatabaseQueryProblem(e);
        }
        throw AppException.createOtherException();
    }


    @Override
    @RolesAllowed(editOffer)
    public boolean checkIfOverlappingForEditing(Starship starship, Date startTime, Date endTime, Long id) throws AppException {
        try {
            TypedQuery<Long> tq = getEntityManager().createNamedQuery("Offer.findByOverlappingForEditing", Long.class);
            tq.setParameter("starship", starship);
            tq.setParameter("startTime", startTime);
            tq.setParameter("endTime", endTime);
            tq.setParameter("id", id);
            Long count = tq.getSingleResult();
            return (!count.equals(0L));
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        }  catch (PersistenceException e) {
            throw OfferException.createExceptionDatabaseQueryProblem(e);
        }
        throw AppException.createOtherException();
    }

    @Override
    public boolean isStarshipAssignedToOffer(Starship starship) throws AppException {
        try {
            TypedQuery<Long> tq = getEntityManager()
                    .createQuery("select count(o) from Offer o where o.starship = :starship", Long.class);
            tq.setParameter("starship", starship);
            Long count = tq.getSingleResult();
            return (!count.equals(0L));
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        }  catch (PersistenceException e) {
            throw OfferException.createExceptionDatabaseQueryProblem(e);
        }
        throw AppException.createOtherException();
    }

    @Override
    @PermitAll
    public List<Offer> findActive() throws AppException {
        try {
            TypedQuery<Offer> tq = getEntityManager().createNamedQuery("Offer.findByHidden", Offer.class);
            tq.setParameter("hidden", false);
            return tq.getResultList();
        }
        catch (DatabaseException e){
            handleDataBaseException(e);
        }
        catch (PersistenceException e){
            Throwable cause = e.getCause();
            throw AppException.createExceptionDatabaseQueryProblem(cause);
        }
        throw AppException.createOtherException();
    }

    @Override
    @RolesAllowed(getAllOffers)
    public List<Offer> findAll()  {
        return super.findAll();
    }

    @Override
    @RolesAllowed({openOffer, closeOffer, changeOfferVisibility, editOffer})
    public void edit(Offer entity) throws AppException {
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
    @RolesAllowed(removeOffer)
    public void remove(Offer entity) throws AppException {
        try {
            super.remove(entity);
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
    @RolesAllowed(addOffer)
    public void create(Offer entity) throws AppException {
        try {
            super.create(entity);
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (PersistenceException e) {
            Throwable cause = e.getCause();
            throw AppException.createExceptionDatabaseQueryProblem(cause);
        }
    }

    @Override
    @RolesAllowed({applyToOffer, editOffer, removeOffer, addApplicationToCategory})
    public void lockPessimisticRead(Offer offer) throws AppException {
        super.lockPessimisticRead(offer);
    }

}
