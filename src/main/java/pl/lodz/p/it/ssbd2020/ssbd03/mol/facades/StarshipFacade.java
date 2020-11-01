
package pl.lodz.p.it.ssbd2020.ssbd03.mol.facades;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd03.core.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Starship;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.StarshipException;
import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import java.util.List;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.*;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class StarshipFacade extends AbstractFacade<Starship> implements StarshipFacadeLocal {

    @PersistenceContext(unitName = "pl.lodz.p.it.ssbd2020_ssbd03_molPU")
    private EntityManager em;

    @Resource(name = "CONSTRAINT_UNIQUE_FOR_STARSHIP_NAME")
    private String CONSTRAINT_UNIQUE_FOR_STARSHIP_NAME;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StarshipFacade() {
        super(Starship.class);
    }

    @Override
    @PermitAll
    public Starship findById(long id) throws AppException {
        try {
            TypedQuery<Starship> tq = getEntityManager().createNamedQuery("Starship.findById", Starship.class);
            tq.setParameter("id", id);
            return tq.getSingleResult();
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (NoResultException e) {
            throw StarshipException.createStarshipNotFoundException();
        } catch (PersistenceException e) {
            throw AppException.createExceptionDatabaseQueryProblem(e);
        }
        throw AppException.createOtherException();
    }

    /**
     * Metoda pobiera listę aktywnych statków
     * @return lista aktywnych statków
     * @author Sergiusz Parchatko
     */
    @PermitAll
    @Override
    public List<Starship> findAllActiveStarships() {
        TypedQuery<Starship> tq = getEntityManager().createNamedQuery("Starship.findAllOperational", Starship.class);
        return tq.getResultList();
    }

    @RolesAllowed({getAllStarships})
    @Override
    public List<Starship> findAll() {
        return super.findAll();
    }

    @Override
    @RolesAllowed(addStarship)
    public void create(Starship entity) throws AppException {
        try {
            super.create(entity);
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (PersistenceException e) {
            Throwable cause = e.getCause();
            if (cause instanceof DatabaseException && e.getMessage().
                    contains(CONSTRAINT_UNIQUE_FOR_STARSHIP_NAME)) {
                throw StarshipException.createStarshipNameTakenException();
            }
            throw AppException.createExceptionDatabaseQueryProblem(e);
        }
    }

    @Override
    @RolesAllowed({editStarship, changeStarshipActiveStatus})
    public void edit(Starship entity) throws AppException {
        try {
            super.edit(entity);
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (PersistenceException e) {
            Throwable cause = e.getCause();
            if (cause instanceof DatabaseException && e.getMessage().
                    contains(CONSTRAINT_UNIQUE_FOR_STARSHIP_NAME)) {
                throw StarshipException.createStarshipNameTakenException();
            }
            throw AppException.createExceptionDatabaseQueryProblem(e);
        }
    }

    @Override
    @RolesAllowed({addOffer, editOffer, assignStarshipToOffer, editStarship, changeStarshipActiveStatus})
    public void lockPessimisticRead(Starship starship) throws AppException {
        super.lockPessimisticRead(starship);
    }
}
