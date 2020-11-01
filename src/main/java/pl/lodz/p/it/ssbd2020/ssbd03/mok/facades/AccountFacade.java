package pl.lodz.p.it.ssbd2020.ssbd03.mok.facades;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd03.core.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AccountException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.ConcurrencyException;
import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;

import javax.annotation.Resource;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import java.util.List;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.listAccounts;
import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.listAccountsReports;


@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class AccountFacade extends AbstractFacade<Account> implements AccountFacadeLocal {

    @PersistenceContext(unitName = "pl.lodz.p.it.ssbd2020_ssbd03_mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Resource(name = "CONSTRAINT_UNIQUE_FOR_ACCOUNT_EMAIL")
    private String CONSTRAINT_UNIQUE_FOR_ACCOUNT_EMAIL;

    public AccountFacade() {
        super(Account.class);
    }

    @RolesAllowed({listAccounts,listAccountsReports})
    @Override
    public List<Account> findAll() {
        return super.findAll();
    }

    /**
     * Metoda pobiera wszystkie konta z podaną frazą w imieniu lub nazwisku
     * @param phrase fraza, która ma znajdować się w imieniu lub nazwisku
     * @return lista kont z podaną frazą
     */
    @RolesAllowed(listAccounts)
    @Override
    public List<Account> findPhraseInName(String phrase) {
        TypedQuery<Account> tq = getEntityManager().createNamedQuery("Account.findByPhraseInName", Account.class);
        tq.setParameter("phrase", phrase);
        return tq.getResultList();
    }

    @Override
    @PermitAll
    public void create(Account entity) throws AppException {
        try {
            super.create(entity);
        }
        catch (DatabaseException e){
            handleDataBaseException(e);
        }
        catch (PersistenceException e){
            Throwable cause = e.getCause();
            if(cause instanceof DatabaseException && e.getMessage().
                    contains(CONSTRAINT_UNIQUE_FOR_ACCOUNT_EMAIL)){
                throw AccountException.createExceptionEmailTaken();
            }
            throw AppException.createExceptionDatabaseQueryProblem(e);
        }
    }

    @Override
    @PermitAll
    public void edit(Account entity) throws AppException {
        try {
            super.edit(entity);
        } catch (DatabaseException e){
            handleDataBaseException(e);
        } catch (OptimisticLockException e) {
            throw ConcurrencyException.createResourceModifiedException();
        }
        catch (PersistenceException e){
            Throwable cause = e.getCause();
            throw AppException.createExceptionDatabaseQueryProblem(cause);
        }
    }
    

    @Override
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Account findByEmail(String email) throws AppException {
        try {
            TypedQuery<Account> tq = getEntityManager().createNamedQuery("Account.findByEmail", Account.class);
            tq.setParameter("email", email);
            return tq.getSingleResult();
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (NoResultException e) {
            throw AccountException.createExceptionAccountNotExists(e);
        }  catch (PersistenceException e){
            throw AppException.createExceptionDatabaseQueryProblem(e);
        }
        throw AppException.createOtherException();
    }

    @Override
    @DenyAll
    public boolean checkIfAccountExists(String email) throws AppException {
        try {
            TypedQuery<Long> query = em.createQuery("select count(e) from Account e where e.email = :email", Long.class);
            query.setParameter("email", email);
            Long count = query.getSingleResult();
            return (!count.equals(0L));
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        }   catch (PersistenceException e){
            throw AppException.createExceptionDatabaseQueryProblem(e);
        }
        throw AppException.createOtherException();
    }

}
