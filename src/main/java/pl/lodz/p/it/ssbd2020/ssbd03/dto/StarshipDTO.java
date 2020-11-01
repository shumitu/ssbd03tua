package pl.lodz.p.it.ssbd2020.ssbd03.dto;


import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

/**
 * Klasa DTO obiektu danych dla encji statku
 */
public class StarshipDTO extends VersionedDTO implements Comparable<StarshipDTO>, ConvertableDTO {

    
    private Long id;
    
    private String name;

    private short crewCapacity;
    
    private double maximumWeight;

    private double fuelCapacity;

    private double maximumSpeed;

    private short yearOfManufacture;
    
    private boolean operational;

    /**
     * Konstruktor
     * @param id id statku w bazie danych
     * @param name nazwa statku
     * @param crewCapacity liczba miejsc dla załogi
     * @param maximumWeight maksymalny udźwig statku
     * @param fuelCapacity pojemność zbiornika paliwa
     * @param maximumSpeed maksymalna prędkość
     * @param yearOfManufacture rok produkcji
     * @param operational informacja, czy statek jest sprawny
     */
    public StarshipDTO(Long version, Long id, String name, short crewCapacity, double maximumWeight, double fuelCapacity, double maximumSpeed, short yearOfManufacture, boolean operational) throws AppException {
        super(version);
        this.id = id;
        this.name = name;
        this.crewCapacity = crewCapacity;
        this.maximumWeight = maximumWeight;
        this.fuelCapacity = fuelCapacity;
        this.maximumSpeed = maximumSpeed;
        this.yearOfManufacture = yearOfManufacture;
        this.operational = operational;
    }

    /**
     * Konstruktor bezparametrowy
     */
    public StarshipDTO() {
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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

    public boolean isOperational() {
        return operational;
    }

    public void setOperational(boolean operational) {
        this.operational = operational;
    }

    @Override
    public int compareTo(StarshipDTO o) {
        return id.compareTo(o.getId());
    }
}
