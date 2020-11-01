package pl.lodz.p.it.ssbd2020.ssbd03.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd03.entities.Employee;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface EmployeeFacadeLocal {

    void create(Employee employee) throws AppException;

    void edit(Employee employee) throws AppException;

    void remove(Employee employee) throws AppException;

    Employee find(Object id);

    List<Employee> findAll();

    List<Employee> findRange(int[] range);

    int count();

    public Employee findById(Long id) throws AppException;
    
}
