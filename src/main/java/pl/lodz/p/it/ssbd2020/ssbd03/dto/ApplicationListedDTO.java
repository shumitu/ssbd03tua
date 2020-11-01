package pl.lodz.p.it.ssbd2020.ssbd03.dto;

import java.util.Date;

public class ApplicationListedDTO implements Comparable<ApplicationListedDTO> {
    private Long id;
    private String firstName;
    private String lastName;
    private double weight;
    private Date creationDate;
    private boolean reviewed;
    private String categoryName;

    public ApplicationListedDTO(Long id, String firstName, String lastName, double weight, Date creationDate,
                                boolean reviewed, String categoryName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.weight = weight;
        this.creationDate = creationDate;
        this.reviewed = reviewed;
        this.categoryName = categoryName;
    }

    /**
     * Konstruktor bezparametrowy
     */

    public ApplicationListedDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public int compareTo(ApplicationListedDTO o) {
        return creationDate.compareTo(o.creationDate);
    }
}
