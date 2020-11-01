package pl.lodz.p.it.ssbd2020.ssbd03.entities;

import pl.lodz.p.it.ssbd2020.ssbd03.listeners.EntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Entity
@EntityListeners(EntityListener.class)
@Table(name = "account")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findById", query = "SELECT a FROM Account a WHERE a.id = :id"),
    @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password"),
    @NamedQuery(name = "Account.findByEmail", query = "SELECT a FROM Account a WHERE a.email = :email"),
    @NamedQuery(name = "Account.findByMotto", query = "SELECT a FROM Account a WHERE a.motto = :motto"),
    @NamedQuery(name = "Account.findByConfirmed", query = "SELECT a FROM Account a WHERE a.confirmed = :confirmed"),
    @NamedQuery(name = "Account.findByActive", query = "SELECT a FROM Account a WHERE a.active = :active"),
    @NamedQuery(name = "Account.findByPasswordChangeRequired", query = "SELECT a FROM Account a WHERE a.passwordChangeRequired = :passwordChangeRequired"),
    @NamedQuery(name = "Account.findByVersion", query = "SELECT a FROM Account a WHERE a.version = :version"),
    @NamedQuery(name = "Account.findByPhraseInName", query = "SELECT a FROM Account a, Candidate c " +
            "WHERE a = c.account AND (lower(c.firstName) " +
            "LIKE lower(concat('%', :phrase ,'%')) OR lower(c.lastName) LIKE lower(concat('%', :phrase ,'%')))" +
            "AND c.active = true")})

@SequenceGenerator(name="account_gen", sequenceName="account_id_seq", allocationSize=1)
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_gen")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "password")
    private String password;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(max = 128)
    @Size(min = 1, max = 128)
    @Column(name = "motto")
    private String motto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "confirmed")
    private boolean confirmed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @Basic(optional = false)
    @NotNull
    @Column(name = "password_change_required")
    private boolean passwordChangeRequired;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "account")
    private Collection<AccessLevel> accessLevelCollection = new ArrayList<AccessLevel>();
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "accountId")
    private LoginInfo loginInfo;
    @Column(name = "version")
    @Version
    private long version;

    public Account() {
    }

    public Account(String password, String email, boolean confirmed, boolean active, boolean passwordChangeRequired) {
        this.password = password;
        this.email = email;
        this.confirmed = confirmed;
        this.active = active;
        this.passwordChangeRequired = passwordChangeRequired;
    }

    public Account(String password, String email, String motto, boolean confirmed, boolean active, boolean passwordChangeRequired) {
        this.password = password;
        this.email = email;
        this.motto = motto;
        this.confirmed = confirmed;
        this.active = active;
        this.passwordChangeRequired = passwordChangeRequired;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getPasswordChangeRequired() {
        return passwordChangeRequired;
    }

    public void setPasswordChangeRequired(boolean passwordChangeRequired) {
        this.passwordChangeRequired = passwordChangeRequired;
    }

    public long getVersion() {
        return version;
    }
    
    @XmlTransient
    public Collection<AccessLevel> getAccessLevelCollection() {
        return accessLevelCollection;
    }
    
    public void setAccessLevelCollection(Collection<AccessLevel> accessLevelCollection) {
        this.accessLevelCollection = accessLevelCollection;
    }

    @XmlTransient
    public LoginInfo getLoginInfo() {
        return loginInfo;
    }
    
    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
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
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[ id=" + getId() + ", version=" + getVersion() + " ]";
    }

    /**
     * Metoda filtrująca liste poziomów dostępu  konta.
     *
     * @param accessLevelName nazwa poziomu dostępu, który ma zostać odnaleziony.
     * @return Optional<AccessLevel>. Jeśli poziom dostępu nie zostanie znaleziony,
     * Optional będzie pusty. W przeciwnym wypadku będzie zawierać poszukiwany poziom dostępu.
     * @author Jakub Fornalski
     */

    public Optional<AccessLevel> findAccessLevel(String accessLevelName) {
        return this.accessLevelCollection.stream().
                filter(o -> o.getLevel().equals(accessLevelName)).findAny();
    }
}
