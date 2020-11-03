package pl.lodz.p.it.ssbd2020.ssbd03.entities;

import pl.lodz.p.it.ssbd2020.ssbd03.listeners.EntityListener;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@EntityListeners(EntityListener.class)
@Table(name = "login_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LoginInfo.findAll", query = "SELECT l FROM LoginInfo l"),
    @NamedQuery(name = "LoginInfo.findById", query = "SELECT l FROM LoginInfo l WHERE l.id = :id"),
    @NamedQuery(name = "LoginInfo.findByAccount", query = "SELECT l FROM LoginInfo l WHERE l.accountId = :accountId"),
    @NamedQuery(name = "LoginInfo.findByLastSuccessfulLogin", query = "SELECT l FROM LoginInfo l WHERE l.lastSuccessfulLogin = :lastSuccessfulLogin"),
    @NamedQuery(name = "LoginInfo.findByLastUnsuccessfulLogin", query = "SELECT l FROM LoginInfo l WHERE l.lastUnsuccessfulLogin = :lastUnsuccessfulLogin"),
    @NamedQuery(name = "LoginInfo.findByLoginAttemptCounter", query = "SELECT l FROM LoginInfo l WHERE l.loginAttemptCounter = :loginAttemptCounter"),
    @NamedQuery(name = "LoginInfo.findByIpAddress", query = "SELECT l FROM LoginInfo l WHERE l.ipAddress = :ipAddress"),
    @NamedQuery(name = "LoginInfo.findByVersion", query = "SELECT l FROM LoginInfo l WHERE l.version = :version")})

public class LoginInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "last_successful_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSuccessfulLogin;
    @Basic(optional = false)
    @Column(name = "last_unsuccessful_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUnsuccessfulLogin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "login_attempt_counter")
    private int loginAttemptCounter;
    @Basic(optional = false)
    @Size(min = 1, max = 39)
    @Column(name = "ip_address")
    private String ipAddress;
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false, updatable = false)
    @OneToOne(optional = false)
    private Account accountId;
    @Column(name = "version")
    @Version
    private long version;


    public LoginInfo() {
    }

    public LoginInfo(Account accountId, int loginAttemptCounter) {
        this.loginAttemptCounter = loginAttemptCounter;
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public Date getLastSuccessfulLogin() {
        return lastSuccessfulLogin;
    }

    public void setLastSuccessfulLogin(Date lastSuccessfulLogin) {
        this.lastSuccessfulLogin = lastSuccessfulLogin;
    }

    public Date getLastUnsuccessfulLogin() {
        return lastUnsuccessfulLogin;
    }

    public void setLastUnsuccessfulLogin(Date lastUnsuccessfulLogin) {
        this.lastUnsuccessfulLogin = lastUnsuccessfulLogin;
    }

    public int getLoginAttemptCounter() {
        return loginAttemptCounter;
    }

    public void setLoginAttemptCounter(int loginAttemptCounter) {
        this.loginAttemptCounter = loginAttemptCounter;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public long getVersion() {
        return version;
    }

    public Account getAccountId() {
        return accountId;
    }

    public void incrementLoginAttemptCounter() {
        this.loginAttemptCounter++;
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
        if (!(object instanceof LoginInfo)) {
            return false;
        }
        LoginInfo other = (LoginInfo) object;
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
