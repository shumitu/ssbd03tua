package pl.lodz.p.it.ssbd2020.ssbd03.exceptions;

import javax.ejb.ApplicationException;
import javax.persistence.NoResultException;

@ApplicationException(rollback = false)
public class NotYetReviewedException extends Exception {

    NotYetReviewedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotYetReviewedException(String message) {
        super(message);
    }

    /**
     * Wyjątek rzucany gdy nie znaleziono oceny zgłoszenia
     * @return ReviewException z komunikatem o nieodnalezieniu statusu zgłoszenia
     */
    static public NotYetReviewedException reviewNotFoundException() {
        return new NotYetReviewedException(ERROR.REVIEW_NOT_FOUND.toString());
    }

    /**
     * Wyjątek rzucany gdy nie znaleziono oceny zgłoszenia
     * @param e wyjątek typu NoResultException
     * @return ReviewException z komunikatem o nieodnalezieniu statusu zgłoszenia
     */
    static public NotYetReviewedException reviewNotFoundException(NoResultException e) {
        return new NotYetReviewedException(ERROR.REVIEW_NOT_FOUND.toString(), e);
    }
}
