package pl.lodz.p.it.ssbd2020.ssbd03.mol.facades;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Category;
import pl.lodz.p.it.ssbd2020.ssbd03.core.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.CategoryException;
import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.addApplicationToCategory;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class CategoryFacade extends AbstractFacade<Category> implements CategoryFacadeLocal {

    @PersistenceContext(unitName = "pl.lodz.p.it.ssbd2020_ssbd03_molPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoryFacade() {
        super(Category.class);
    }

    @Override
    @RolesAllowed({addApplicationToCategory})
    public Category findByName(String name) throws AppException {
        try {
            TypedQuery<Category> tq = getEntityManager().createNamedQuery("Category.findByName", Category.class);
            tq.setParameter("name", name);
            return tq.getSingleResult();
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (NoResultException e) {
            throw CategoryException.categoryNotFoundException(e);
        } catch (PersistenceException e) {
            throw CategoryException.createExceptionDatabaseQueryProblem(e);
        }
        throw AppException.createOtherException();
    }
}
