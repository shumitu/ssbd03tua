package pl.lodz.p.it.ssbd2020.ssbd03.dto;

public class ApplicationCategoryDTO extends VersionedDTO {

    private Long applicationId;
    private String categoryName;

    public ApplicationCategoryDTO() {
        super();
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}
