package pl.lodz.p.it.ssbd2020.ssbd03.dto;

import java.util.Date;

public class ApplicationCandidateListedDTO implements Comparable<ApplicationCandidateListedDTO> {
    private Long id;
    private Date creationTime;
    private boolean reviewed;
    private String categoryName;
    private String destination;
    private Date startTime;
    private Date endTime;

    public ApplicationCandidateListedDTO(Long id, Date creationTime, boolean reviewed, String categoryName,
                                         String destination, Date startTime, Date endTime) {
        this.id = id;
        this.creationTime = creationTime;
        this.reviewed = reviewed;
        this.categoryName = categoryName;
        this.destination = destination;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Konstruktor bezparametrowy
     */
    public ApplicationCandidateListedDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public int compareTo(ApplicationCandidateListedDTO o) {
        return creationTime.compareTo(o.creationTime);
    }
}
