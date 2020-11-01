package pl.lodz.p.it.ssbd2020.ssbd03.mok.facades;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd03.core.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Employee;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.EmployeeException;
import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.*;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class EmployeeFacadeMok extends AbstractFacade<Employee> implements EmployeeFacadeLocal {

    @PersistenceContext(unitName = "pl.lodz.p.it.ssbd2020_ssbd03_mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeeFacadeMok() {
        super(Employee.class);
    }

    @Override
    @RolesAllowed(addAccessLevel)
    public void create(Employee employee) throws AppException {
        super.create(employee);
    }


    @Override
    @RolesAllowed({displayOwnAccountDetails, displaySomeonesAccountDetails})
    public Employee findById(Long id) throws AppException {
        try {
            TypedQuery<Employee> tq = getEntityManager().createNamedQuery("Employee.findById", Employee.class);
            tq.setParameter("id", id);
            return tq.getSingleResult();
        } catch (DatabaseException e) {
            handleDataBaseException(e);
        } catch (NoResultException e) {
            throw EmployeeException.createExceptionEmployeeNotExists(e);
        } catch (PersistenceException e) {
            throw EmployeeException.createExceptionDatabaseQueryProblem(e);
        }
        throw AppException.createOtherException();
    }
    
}
