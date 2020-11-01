package pl.lodz.p.it.ssbd2020.ssbd03.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "auth_view")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuthView.findAll", query = "SELECT a FROM AuthView a"),
    @NamedQuery(name = "AuthView.findById", query = "SELECT a FROM AuthView a WHERE a.id = :id"),
    @NamedQuery(name = "AuthView.findByEmail", query = "SELECT a FROM AuthView a WHERE a.email = :email"),
    @NamedQuery(name = "AuthView.findByPassword", query = "SELECT a FROM AuthView a WHERE a.password = :password"),
    @NamedQuery(name = "AuthView.findByAccessLevel", query = "SELECT a FROM AuthView a WHERE a.accessLevel = :accessLevel")})
public class AuthView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "id")
    @Id
    private BigInteger id;
    @Size(max = 254)
    @Column(name = "email")
    private String email;
    @Size(max = 64)
    @Column(name = "password")
    private String password;
    @Size(max = 16)
    @Column(name = "access_level")
    private String accessLevel;

    public AuthView() {
    }

    public BigInteger getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAccessLevel() {
        return accessLevel;
    }
    
}
