package pl.lodz.p.it.ssbd2020.ssbd03.entities;

import pl.lodz.p.it.ssbd2020.ssbd03.listeners.EntityListener;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@EntityListeners(EntityListener.class)
@Table(name = "starship")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Starship.findAll", query = "SELECT s FROM Starship s"),
    @NamedQuery(name = "Starship.findById", query = "SELECT s FROM Starship s WHERE s.id = :id"),
    @NamedQuery(name = "Starship.findByName", query = "SELECT s FROM Starship s WHERE s.name = :name"),
    @NamedQuery(name = "Starship.findByCrewCapacity", query = "SELECT s FROM Starship s WHERE s.crewCapacity = :crewCapacity"),
    @NamedQuery(name = "Starship.findByMaximumWeight", query = "SELECT s FROM Starship s WHERE s.maximumWeight = :maximumWeight"),
    @NamedQuery(name = "Starship.findByFuelCapacity", query = "SELECT s FROM Starship s WHERE s.fuelCapacity = :fuelCapacity"),
    @NamedQuery(name = "Starship.findByMaximumSpeed", query = "SELECT s FROM Starship s WHERE s.maximumSpeed = :maximumSpeed"),
    @NamedQuery(name = "Starship.findByYearOfManufacture", query = "SELECT s FROM Starship s WHERE s.yearOfManufacture = :yearOfManufacture"),
    @NamedQuery(name = "Starship.findByOperational", query = "SELECT s FROM Starship s WHERE s.operational = :operational"),
    @NamedQuery(name = "Starship.findByVersion", query = "SELECT s FROM Starship s WHERE s.version = :version"),
    @NamedQuery(name = "Starship.findAllOperational", query = "SELECT s FROM Starship s where s.operational = true")})

@SequenceGenerator(name="starship_gen", sequenceName="starship_id_seq", allocationSize=1)
public class Starship implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "starship_gen")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "crew_capacity")
    private short crewCapacity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "maximum_weight")
    private double maximumWeight;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fuel_capacity")
    private double fuelCapacity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "maximum_speed")
    private double maximumSpeed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "year_of_manufacture")
    private short yearOfManufacture;
    @Basic(optional = false)
    @NotNull
    @Column(name = "operational")
    private boolean operational;
    @Column(name = "version")
    @Version
    private long version;

    public Starship() {
    }

    public Starship(String name, short crewCapacity, double maximumWeight, double fuelCapacity,
                    double maximumSpeed, short yearOfManufacture, boolean operational) {
        this.name = name;
        this.crewCapacity = crewCapacity;
        this.maximumWeight = maximumWeight;
        this.fuelCapacity = fuelCapacity;
        this.maximumSpeed = maximumSpeed;
        this.yearOfManufacture = yearOfManufacture;
        this.operational = operational;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getCrewCapacity() {
        return crewCapacity;
    }

    public void setCrewCapacity(short crewCapacity) {
        this.crewCapacity = crewCapacity;
    }

    public double getMaximumWeight() {
        return maximumWeight;
    }

    public void setMaximumWeight(double maximumWeight) {
        this.maximumWeight = maximumWeight;
    }

    public double getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public double getMaximumSpeed() {
        return maximumSpeed;
    }

    public void setMaximumSpeed(double maximumSpeed) {
        this.maximumSpeed = maximumSpeed;
    }

    public short getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(short yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public boolean getOperational() {
        return operational;
    }

    public void setOperational(boolean operational) {
        this.operational = operational;
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
        if (!(object instanceof Starship)) {
            return false;
        }
        Starship other = (Starship) object;
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
