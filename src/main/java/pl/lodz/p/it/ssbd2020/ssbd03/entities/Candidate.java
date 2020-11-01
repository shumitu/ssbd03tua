
package pl.lodz.p.it.ssbd2020.ssbd03.entities;


import pl.lodz.p.it.ssbd2020.ssbd03.listeners.EntityListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;



@Entity
@EntityListeners(EntityListener.class)
@DiscriminatorValue("CANDIDATE")
@Table(name = "candidate")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Candidate.findAll", query = "SELECT c FROM Candidate c"),
        @NamedQuery(name = "Candidate.findById", query = "SELECT c FROM Candidate c WHERE c.id = :id"),
        @NamedQuery(name = "Candidate.findByFirstName", query = "SELECT c FROM Candidate c WHERE c.firstName = :firstName"),
        @NamedQuery(name = "Candidate.findByLastName", query = "SELECT c FROM Candidate c WHERE c.lastName = :lastName"),
        @NamedQuery(name = "Candidate.findByVersion", query = "SELECT c FROM Candidate c WHERE c.version = :version")})
public class Candidate extends AccessLevel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "first_name")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "last_name")
    private String lastName;
    @OneToMany(mappedBy = "candidate")
    private Collection<Application> applicationCollection = new ArrayList<Application>();
    @Column(name = "version")
    @Version
    protected long version;


    public Candidate() {
    }


    public Candidate(boolean active, Account account, String firstName, String lastName) {
        super(CANDIDATE, active, account);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Candidate(boolean active, Account account) {
        super(CANDIDATE, active, account);
        this.firstName = "";
        this.lastName = "";
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Collection<Application> getApplicationCollection() {
        return applicationCollection;
    }

    public void setApplicationCollection(Collection<Application> applicationCollection) {
        this.applicationCollection = applicationCollection;
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
        if (!(object instanceof Candidate)) {
            return false;
        }
        Candidate other = (Candidate) object;
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
