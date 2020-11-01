package pl.lodz.p.it.ssbd2020.ssbd03.core;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.TransactionException;

import javax.annotation.security.DenyAll;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceException;
import java.sql.SQLNonTransientConnectionException;
import java.util.List;

public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) throws AppException {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }

    public void edit(T entity) throws AppException {
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }

    public void remove(T entity) throws AppException {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @DenyAll
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @DenyAll
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @DenyAll
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    protected void handleDataBaseException(DatabaseException e) throws AppException {
        if (e.getCause() instanceof SQLNonTransientConnectionException) {
            throw AppException.createExceptionDatabaseConnectionProblem(e);
        } else {
            throw AppException.createExceptionDatabaseQueryProblem(e);
        }
    }

    public void lockPessimisticRead(T entity) throws AppException {
        try {
            getEntityManager().lock(entity, LockModeType.PESSIMISTIC_READ);
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }


}
