package pl.lodz.p.it.ssbd2020.ssbd03.entities;

import pl.lodz.p.it.ssbd2020.ssbd03.listeners.EntityListener;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@EntityListeners(EntityListener.class)
@Table(name = "review")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Review.findAll", query = "SELECT r FROM Review r"),
    @NamedQuery(name = "Review.findById", query = "SELECT r FROM Review r WHERE r.id = :id"),
    @NamedQuery(name = "Review.findByApplication", query = "SELECT r from Review  r WHERE r.application= :application"),
    @NamedQuery(name = "Review.findByVersion", query = "SELECT r FROM Review r WHERE r.version = :version")})
@SequenceGenerator(name="review_gen", sequenceName="review_id_seq", allocationSize=1)
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_gen")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "version")
    @Version
    private long version;
    @JoinColumn(name = "application_id", referencedColumnName = "id", nullable = false, updatable = false)
    @OneToOne(optional = false)
    private Application application;
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false, updatable = true)
    @ManyToOne(optional = false)
    private Category category;
    @JoinColumn(name = "reviewer_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employee reviewer;

    public Review() {
    }

    public Review(Application application, Category category, Employee reviewer) {
        this.application = application;
        this.category = category;
        this.reviewer = reviewer;
    }

    public Long getId() {
        return id;
    }


    public long getVersion() {
        return version;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Employee getReviewer() {
        return reviewer;
    }

    public void setReviewer(Employee reviewer) {
        this.reviewer = reviewer;
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
        if (!(object instanceof Review)) {
            return false;
        }
        Review other = (Review) object;
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
