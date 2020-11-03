package pl.lodz.p.it.ssbd2020.ssbd03.entities;

import pl.lodz.p.it.ssbd2020.ssbd03.listeners.EntityListener;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "level", discriminatorType = DiscriminatorType.STRING)

@Entity
@EntityListeners(EntityListener.class)
@Table(name = "access_level")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "AccessLevel.findAll", query = "SELECT a FROM AccessLevel a"),
        @NamedQuery(name = "AccessLevel.findById", query = "SELECT a FROM AccessLevel a WHERE a.id = :id"),
        @NamedQuery(name = "AccessLevel.findByAccount", query = "SELECT a FROM AccessLevel a WHERE a.account = :account"),
        @NamedQuery(name = "AccessLevel.findByLevel", query = "SELECT a FROM AccessLevel a WHERE a.level = :level"),
        @NamedQuery(name = "AccessLevel.findByActive", query = "SELECT a FROM AccessLevel a WHERE a.active = :active"),
        @NamedQuery(name = "AccessLevel.findByVersion", query = "SELECT a FROM AccessLevel a WHERE a.version = :version")})
public class AccessLevel implements Serializable {

    public static final String ADMIN = "ADMIN";
    public static final String EMPLOYEE = "EMPLOYEE";
    public static final String CANDIDATE = "CANDIDATE";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    protected Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "level")
    protected String level;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    protected boolean active;
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    protected Account account;
    @Column(name = "version")
    @Version
    protected long version;

    public AccessLevel() {
    }

    public AccessLevel(String level, boolean active, Account account) {
        this.level = level;
        this.active = active;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public String getLevel() {
        return level;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getVersion() {
        return version;
    }

    public Account getAccount() {
        return account;
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
        if (!(object instanceof AccessLevel)) {
            return false;
        }
        AccessLevel other = (AccessLevel) object;
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
