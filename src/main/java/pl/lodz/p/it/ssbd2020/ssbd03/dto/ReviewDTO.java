package pl.lodz.p.it.ssbd2020.ssbd03.dto;

import pl.lodz.p.it.ssbd2020.ssbd03.entities.Application;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Category;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Employee;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Review;

import javax.validation.constraints.NotNull;

/**
 * Klasa DTO obiektu danych dla encji ocena (review)
 */
public class ReviewDTO implements ConvertableDTO {

    private Long id;
    private Long employeeNumber;
    private String categoryName;

    /**
     * Konstruktor bezparametrowy
     */
    public ReviewDTO() {
    }

    /**
     * Konstruktor
     * @param id identyfikator oceny zgłoszenia
     * @param employeeNumber numer pracownika oceniającego zgłoszenie
     * @param categoryName nazwa kategorii, do której przypisano zgłoszenie
     */
    public ReviewDTO(Long id, Long employeeNumber, String categoryName) {
        this.id = id;
        this.employeeNumber = employeeNumber;
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(Long employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
