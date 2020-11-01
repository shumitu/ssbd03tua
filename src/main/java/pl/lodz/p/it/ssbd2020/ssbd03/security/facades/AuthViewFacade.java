package pl.lodz.p.it.ssbd2020.ssbd03.security.facades;

import pl.lodz.p.it.ssbd2020.ssbd03.core.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.AuthView;
import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import javax.annotation.security.PermitAll;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class AuthViewFacade extends AbstractFacade<AuthView> implements AuthViewFacadeLocal {

    @PersistenceContext(unitName = "pl.lodz.p.it.ssbd2020_ssbd03_authPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuthViewFacade() {
        super(AuthView.class);
    }

    @Override
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<AuthView> findByEmail(String email) {
        TypedQuery<AuthView> tq = getEntityManager().createNamedQuery("AuthView.findByEmail", AuthView.class);
        tq.setParameter("email", email);
        return tq.getResultList();
    }
    
}
