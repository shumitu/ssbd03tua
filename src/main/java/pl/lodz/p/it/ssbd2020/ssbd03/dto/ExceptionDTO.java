package pl.lodz.p.it.ssbd2020.ssbd03.dto;


/**
 * Klasa DTO obiektu wyjątku
 */
public class ExceptionDTO {
    private String message;

    /**
     * Konstruktor bezparamterowy
     */
    public ExceptionDTO() {
    }

    /**
     * Konstruktor
     * @param message wiadomość wyjątku
     */
    public ExceptionDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
