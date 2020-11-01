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
@Table(name = "application")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Application.findAll", query = "SELECT a FROM Application a"),
    @NamedQuery(name = "Application.findById", query = "SELECT a FROM Application a WHERE a.id = :id"),
        @NamedQuery(name = "Application.findByCandidate", query = "SELECT a FROM Application a WHERE a.candidate = :candidate"),
    @NamedQuery(name = "Application.findByWeight", query = "SELECT a FROM Application a WHERE a.weight = :weight"),
    @NamedQuery(name = "Application.findByExaminationCode", query = "SELECT a FROM Application a WHERE a.examinationCode = :examinationCode"),
    @NamedQuery(name = "Application.findByMotivationalLetter", query = "SELECT a FROM Application a WHERE a.motivationalLetter = :motivationalLetter"),
    @NamedQuery(name = "Application.findByCreatedTime", query = "SELECT a FROM Application a WHERE a.createdTime = :createdTime"),
    @NamedQuery(name = "Application.findByVersion", query = "SELECT a FROM Application a WHERE a.version = :version")})
@SequenceGenerator(name="application_gen", sequenceName="application_id_seq", allocationSize=1)
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_gen")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "weight")
    private double weight;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "examination_code")
    private String examinationCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "motivational_letter")
    private String motivationalLetter;
    @JoinColumn(name = "offer_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    private Offer offer;
    @JoinColumn(name = "candidate_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    private Candidate candidate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;
    @Column(name = "version")
    @Version
    private long version;

    public Application() {
    }

    public Application(double weight, String examinationCode, String motivationalLetter, Date createdTime, Offer offer, Candidate candidate) {
        this.weight = weight;
        this.examinationCode = examinationCode;
        this.motivationalLetter = motivationalLetter;
        this.offer = offer;
        this.candidate = candidate;
        this.createdTime = createdTime;
    }

    public Long getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getExaminationCode() {
        return examinationCode;
    }

    public void setExaminationCode(String examinationCode) {
        this.examinationCode = examinationCode;
    }

    public String getMotivationalLetter() {
        return motivationalLetter;
    }

    public void setMotivationalLetter(String motivationalLetter) {
        this.motivationalLetter = motivationalLetter;
    }

    public long getVersion() {
        return version;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
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
        if (!(object instanceof Application)) {
            return false;
        }
        Application other = (Application) object;
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
