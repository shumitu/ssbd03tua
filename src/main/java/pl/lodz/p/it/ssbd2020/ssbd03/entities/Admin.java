package pl.lodz.p.it.ssbd2020.ssbd03.entities;


import pl.lodz.p.it.ssbd2020.ssbd03.listeners.EntityListener;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@EntityListeners(EntityListener.class)
@DiscriminatorValue("ADMIN")
@Table(name = "admin")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Admin.findAll", query = "SELECT a FROM Admin a"),
        @NamedQuery(name = "Admin.findById", query = "SELECT a FROM Admin a WHERE a.id = :id"),
        @NamedQuery(name = "Admin.findByVersion", query = "SELECT a FROM Admin a WHERE a.version = :version")})
public class Admin extends AccessLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Version
    @Column(name = "version")
    protected long version;

    public Admin() {
    }

    public Admin(boolean active, Account account) {
        super(ADMIN, active, account);
    }

    public long getVersion() {
        return version;
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
        if (!(object instanceof Admin)) {
            return false;
        }
        Admin other = (Admin) object;
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
