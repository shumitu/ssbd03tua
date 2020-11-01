package pl.lodz.p.it.ssbd2020.ssbd03.dto;

/**
 * Klasa DTO obiektu danych dla encji statku służący do przekazywania zapytania
 * o dane statku do serwera
 */
public class StarshipRequestDTO {

    private Long id;

    /**
     * 
     * @param id identyfikator statku
     */
    public StarshipRequestDTO(Long id) {
        this.id = id;
    }
    
    /**
     * Konstruktor bezparametrowy
     */
    public StarshipRequestDTO() {
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
}
