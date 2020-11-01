package pl.lodz.p.it.ssbd2020.ssbd03.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd03.core.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.*;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class AccessLevelFacadeMok extends AbstractFacade<AccessLevel> implements AccessLevelFacadeLocal {

    @PersistenceContext(unitName = "pl.lodz.p.it.ssbd2020_ssbd03_mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccessLevelFacadeMok() {
        super(AccessLevel.class);
    }

    @Override
    @RolesAllowed(addAccessLevel)
    public void edit(AccessLevel accessLevel) throws AppException {
        super.edit(accessLevel);
    }

    @Override
    @RolesAllowed({displayOwnAccountDetails, displaySomeonesAccountDetails, editOwnAccount, editSomeonesAccount})
    public List<AccessLevel> findByAccount(Account account) {
        TypedQuery<AccessLevel> tq = getEntityManager().createNamedQuery("AccessLevel.findByAccount", AccessLevel.class);
        tq.setParameter("account", account);
        return tq.getResultList();
    }
    
}
