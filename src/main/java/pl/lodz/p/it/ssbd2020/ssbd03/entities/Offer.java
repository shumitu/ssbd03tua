package pl.lodz.p.it.ssbd2020.ssbd03.entities;

import pl.lodz.p.it.ssbd2020.ssbd03.listeners.EntityListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@EntityListeners(EntityListener.class)
@Table(name = "offer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Offer.findAll", query = "SELECT o FROM Offer o"),
    @NamedQuery(name = "Offer.findById", query = "SELECT o FROM Offer o WHERE o.id = :id"),
    @NamedQuery(name = "Offer.findByFlightStartTime", query = "SELECT o FROM Offer o WHERE o.flightStartTime = :flightStartTime"),
    @NamedQuery(name = "Offer.findByFlightEndTime", query = "SELECT o FROM Offer o WHERE o.flightEndTime = :flightEndTime"),
    @NamedQuery(name = "Offer.findByDestination", query = "SELECT o FROM Offer o WHERE o.destination = :destination"),
    @NamedQuery(name = "Offer.findByPrice", query = "SELECT o FROM Offer o WHERE o.price = :price"),
    @NamedQuery(name = "Offer.findByDescription", query = "SELECT o FROM Offer o WHERE o.description = :description"),
    @NamedQuery(name = "Offer.findByHidden", query = "SELECT o FROM Offer o WHERE o.hidden = :hidden"),
    @NamedQuery(name = "Offer.findByOpen", query = "SELECT o FROM Offer o WHERE o.open = :open"),
    @NamedQuery(name = "Offer.findByTotalCost", query = "SELECT o FROM Offer o WHERE o.totalCost = :totalCost"),
    @NamedQuery(name = "Offer.findByTotalWeight", query = "SELECT o FROM Offer o WHERE o.totalWeight = :totalWeight"),
    @NamedQuery(name = "Offer.findByVersion", query = "SELECT o FROM Offer o WHERE o.version = :version"),
    @NamedQuery(name = "Offer.findByOverlappingForAdding", query = "SELECT count(o) FROM Offer o WHERE o.starship = :starship" +
                                    " AND NOT (o.flightStartTime > :endTime OR o.flightEndTime < :startTime)"),
    @NamedQuery(name = "Offer.findByOverlappingForEditing", query = "SELECT count(o) FROM Offer o WHERE o.starship = :starship" +
                                    " AND NOT (o.flightStartTime > :endTime OR o.flightEndTime < :startTime)" +
                                    " AND NOT o.id = :id")})
@SequenceGenerator(name="offer_gen", sequenceName="offer_id_seq", allocationSize=1)
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offer_gen")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "flight_start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date flightStartTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "flight_end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date flightEndTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "destination")
    private String destination;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private int price;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hidden")
    private boolean hidden;
    @Basic(optional = false)
    @NotNull
    @Column(name = "open")
    private boolean open;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_cost")
    private long totalCost;
    @Basic(optional = false)
    @Column(name = "total_weight")
    private double totalWeight;
    @OneToMany(mappedBy = "offer")
    private Collection<Application> applicationCollection = new ArrayList<Application>();
    @Version
    @Column(name = "version")
    private long version;
    @JoinColumn(name = "starship_id", referencedColumnName = "id")
    @ManyToOne
    private Starship starship;

    public Offer() {
    }


    public Offer(Date flightStartTime, Date flightEndTime, String destination, int price, String description,
                 boolean hidden, boolean open, long totalCost, Starship starship) {
        this.flightStartTime = flightStartTime;
        this.flightEndTime = flightEndTime;
        this.destination = destination;
        this.price = price;
        this.description = description;
        this.hidden = hidden;
        this.open = open;
        this.totalCost = totalCost;
        this.starship = starship;
    }

    public Long getId() {
        return id;
    }

    public Date getFlightStartTime() {
        return flightStartTime;
    }

    public void setFlightStartTime(Date flightStartTime) {
        this.flightStartTime = flightStartTime;
    }

    public Date getFlightEndTime() {
        return flightEndTime;
    }

    public void setFlightEndTime(Date flightEndTime) {
        this.flightEndTime = flightEndTime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean getOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(long totalCost) {
        this.totalCost = totalCost;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public long getVersion() {
        return version;
    }

    public Starship getStarship() {
        return starship;
    }

    public void setStarship(Starship starship) {
        this.starship = starship;
    }

    @XmlTransient
    public Collection<Application> getApplicationCollection() {
        return applicationCollection;
    }

    public void setApplicationCollection(Collection<Application> applicationCollection) {
        this.applicationCollection = applicationCollection;
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
        if (!(object instanceof Offer)) {
            return false;
        }
        Offer other = (Offer) object;
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
