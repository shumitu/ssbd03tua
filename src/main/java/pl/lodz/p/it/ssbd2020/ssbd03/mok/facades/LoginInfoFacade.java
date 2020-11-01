package pl.lodz.p.it.ssbd2020.ssbd03.mok.facades;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd03.core.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.LoginInfo;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.LoginInfoException;
import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class LoginInfoFacade extends AbstractFacade<LoginInfo> implements LoginInfoFacadeLocal {

    @PersistenceContext(unitName = "pl.lodz.p.it.ssbd2020_ssbd03_mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LoginInfoFacade() {
        super(LoginInfo.class);
    }

    @PermitAll
    @Override
    public void edit(LoginInfo entity) throws AppException {
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
    @PermitAll
    public LoginInfo findByAccount(Account account) throws AppException {
        try {
            TypedQuery<LoginInfo> tq = getEntityManager().createNamedQuery("LoginInfo.findByAccount", LoginInfo.class);
            tq.setParameter("accountId", account);
            return tq.getSingleResult();
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (NoResultException e) {
            throw LoginInfoException.createExceptionLoginInfoNotExists(e);
        }  catch (PersistenceException e){
            throw AppException.createExceptionDatabaseQueryProblem(e);
        }
        throw AppException.createOtherException();
    }



}
