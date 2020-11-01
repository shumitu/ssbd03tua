package pl.lodz.p.it.ssbd2020.ssbd03.entities;

import pl.lodz.p.it.ssbd2020.ssbd03.listeners.EntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@EntityListeners(EntityListener.class)
@Table(name = "password_reset_token")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PasswordResetToken.findAll", query = "SELECT p FROM PasswordResetToken p"),
    @NamedQuery(name = "PasswordResetToken.findById", query = "SELECT p FROM PasswordResetToken p WHERE p.id = :id"),
    @NamedQuery(name = "PasswordResetToken.findByToken", query = "SELECT p FROM PasswordResetToken p WHERE p.token = :token"),
    @NamedQuery(name = "PasswordResetToken.findByExpirationTime", query = "SELECT p FROM PasswordResetToken p WHERE p.expirationTime = :expirationTime"),
    @NamedQuery(name = "PasswordResetToken.findByVersion", query = "SELECT p FROM PasswordResetToken p WHERE p.version = :version")})
@SequenceGenerator(name="password_reset_token_gen", sequenceName="password_reset_token_id_seq", allocationSize=1)
public class PasswordResetToken implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "password_reset_token_gen")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 64, max = 64)
    @Column(name = "token")
    private String token;
    @Basic(optional = false)
    @NotNull
    @Column(name = "expiration_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationTime;
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false, updatable = false)
    @OneToOne(optional = false)
    private Account account;
    @Column(name = "version")
    @Version
    private long version;

    public PasswordResetToken() {
    }

    public PasswordResetToken(String token, Date expirationTime, Account account) {
        this.token = token;
        this.expirationTime = expirationTime;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
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
        if (!(object instanceof PasswordResetToken)) {
            return false;
        }
        PasswordResetToken other = (PasswordResetToken) object;
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
