package pl.lodz.p.it.ssbd2020.ssbd03.mok.facades;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd03.core.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.PasswordResetToken;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.PasswordResetTokenException;
import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class PasswordResetTokenFacade extends AbstractFacade<PasswordResetToken> implements PasswordResetTokenFacadeLocal {

    @PersistenceContext(unitName = "pl.lodz.p.it.ssbd2020_ssbd03_mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PasswordResetTokenFacade() {
        super(PasswordResetToken.class);
    }

    @Override
    @PermitAll
    public void create(PasswordResetToken passwordResetToken) throws AppException {
        super.create(passwordResetToken);
    }

    @Override
    @PermitAll
    public boolean checkIfTokenExistForAccount(Account account) {
        TypedQuery<Long> query = em.createQuery("select count(e) from PasswordResetToken e where e.account = :accountId", Long.class);
        query.setParameter("accountId", account);
        Long count = query.getSingleResult();
        return (!count.equals(0L));
    }

    @Override
    @PermitAll
    public int deleteExistingTokensForAccount(Account account) throws AppException {
        try {
            Query query = em.createQuery("delete from PasswordResetToken e where e.account = :accountId");
            query.setParameter("accountId", account);
            return query.executeUpdate();
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (PersistenceException e) {
            throw AppException.createExceptionDatabaseQueryProblem(e);
        }
        return 0;
    }

    @Override
    @PermitAll
    public PasswordResetToken findByToken(String token) throws AppException {
        try {
            TypedQuery<PasswordResetToken> query = em.createQuery("select e from PasswordResetToken e where e.token = :token", PasswordResetToken.class);
            query.setParameter("token", token);
            return query.getSingleResult();
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (NoResultException e) {
            throw PasswordResetTokenException.createExceptionPasswordResetTokenNotExists(e);
        } catch (PersistenceException e) {
            throw AppException.createExceptionDatabaseQueryProblem(e);
        }
        throw AppException.createOtherException();
    }

}
