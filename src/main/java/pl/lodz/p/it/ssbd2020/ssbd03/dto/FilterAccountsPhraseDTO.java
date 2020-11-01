package pl.lodz.p.it.ssbd2020.ssbd03.dto;

/**
 * Klasa DTO dla listy filtrowanej na podstawie przekazanego wzorca
 */
public class FilterAccountsPhraseDTO {
    private String filterPhrase;

    /**
     * Konstruktor bezparamterowy
     */
    public FilterAccountsPhraseDTO() {
    }

    public FilterAccountsPhraseDTO(String filterPhrase) {
        this.filterPhrase = filterPhrase;
    }

    public String getFilterPhrase() {
        return filterPhrase;
    }

    public void setFilterPhrase(String filterPhrase) {
        this.filterPhrase = filterPhrase;
    }
}
