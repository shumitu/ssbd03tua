
package pl.lodz.p.it.ssbd2020.ssbd03.mol.facades;

import pl.lodz.p.it.ssbd2020.ssbd03.entities.Offer;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Starship;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

@Local
public interface OfferFacadeLocal {

    void create(Offer offer) throws AppException;

    void edit(Offer offer) throws AppException;

    void remove(Offer offer) throws AppException;

    Offer find(Object id);

    List<Offer> findAll() throws AppException;

    List<Offer> findRange(int[] range);

    List<Offer> findActive() throws AppException;

    Offer findById(long id) throws AppException;


    /**
     * Metoda sprawdza czy następuje konflikt przedziału czasowego dla tworzonej oferty oraz wybranego statku
     * @param starship wybrany statek
     * @param startTime początek lotu
     * @param endTime koniec lotu
     * @return boolean potwierdzający obecność konfliktu
     */
    boolean checkIfOverlappingForAdding(Starship starship, Date startTime, Date endTime) throws AppException;

    /**
     * Metoda sprawdza czy następuje konflikt przedziału czasowego dla edytowanej oferty oraz wybranego statku
     * @param starship wybrany statek
     * @param startTime początek lotu
     * @param endTime koniec lotu
     * @param id id oferty
     * @return boolean potwierdzający obecność konfliktu
     */
    boolean checkIfOverlappingForEditing(Starship starship, Date startTime, Date endTime, Long id) throws AppException;

    /**
     * Metoda sprawdza czy statek jest przypisany do jakieś oferty
     *
     * @param starship statek do sprawdzenia
     * @return true jeśli statek jest przypisany do jakieś oferty
     * @author Michał Tęgi
     */
    boolean isStarshipAssignedToOffer(Starship starship) throws AppException;

    int count();

    void lockPessimisticRead(Offer offer) throws AppException;

}
