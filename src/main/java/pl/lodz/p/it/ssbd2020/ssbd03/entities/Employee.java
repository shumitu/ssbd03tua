package pl.lodz.p.it.ssbd2020.ssbd03.entities;


import pl.lodz.p.it.ssbd2020.ssbd03.listeners.EntityListener;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@EntityListeners(EntityListener.class)
@DiscriminatorValue("EMPLOYEE")
@Table(name = "employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e"),
    @NamedQuery(name = "Employee.findById", query = "SELECT e FROM Employee e WHERE e.id = :id"),
    @NamedQuery(name = "Employee.findByEmployeeNumber", query = "SELECT e FROM Employee e WHERE e.employeeNumber = :employeeNumber"),
    @NamedQuery(name = "Employee.findByVersion", query = "SELECT e FROM Employee e WHERE e.version = :version")})

public class Employee extends AccessLevel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "employee_number", insertable = false, updatable = false, columnDefinition="serial")
    private long employeeNumber;

    @Column(name = "version")
    @Version
    protected long version;


    public Employee() {
    }


    public Employee(boolean active, Account account, long employeeNumber) {
        super(EMPLOYEE, active, account);
        this.employeeNumber = employeeNumber;
    }

    public Employee(boolean active, Account account) {
        super(EMPLOYEE, active, account);
    }

    public long getVersion() {
        return version;
    }



    public long getEmployeeNumber() {
        return employeeNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[ id=" + getId() + ", version=" + getVersion() + " ]";
    }
}
