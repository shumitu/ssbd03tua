package pl.lodz.p.it.ssbd2020.ssbd03.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd03.core.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Admin;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.addAccessLevel;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class AdminFacade extends AbstractFacade<Admin> implements AdminFacadeLocal {

    @PersistenceContext(unitName = "pl.lodz.p.it.ssbd2020_ssbd03_mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdminFacade() {
        super(Admin.class);
    }

    @Override
    @RolesAllowed(addAccessLevel)
    public void create(Admin admin) throws AppException {
        super.create(admin);
    }
    
}
