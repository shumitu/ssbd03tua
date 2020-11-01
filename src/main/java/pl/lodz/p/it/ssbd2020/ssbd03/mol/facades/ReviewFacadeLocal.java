
package pl.lodz.p.it.ssbd2020.ssbd03.mol.facades;

import java.util.List;
import javax.ejb.Local;

import pl.lodz.p.it.ssbd2020.ssbd03.entities.Application;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Offer;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Review;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.NotYetReviewedException;

@Local
public interface ReviewFacadeLocal {

    void create(Review review) throws AppException;

    void edit(Review review) throws AppException;

    void remove(Review review) throws AppException;

    Review find(Object id);

    List<Review> findAll();

    List<Review> findRange(int[] range);

    int count();

    public List<Review> findByOffer(Offer offer) throws AppException;

    Review findByApplication(Application application) throws AppException, NotYetReviewedException;

    boolean checkIfReviewExists(Application application) throws AppException;
}
