package pl.lodz.p.it.ssbd2020.ssbd03.exceptions;

import javax.persistence.NoResultException;

/**
 * Wyjątki powiązane z encją Candidate
 */
public class CandidateException extends AppException {

    /**
     * Konstruktor
     * @param message komunikat błedu
     * @param cause   błąd który wystąpił w aplikacji
     * @see AppException
     */
    protected CandidateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Konstruktor
     * @param message komunikat błedu
     * @see AppException
     */
    protected CandidateException(String message) {
        super(message);
    }

    /**
     * Wyjątek rzucany gdy wpis w tabeli Candidate nie zostanie znalezione w bazie
     * @param e wyjątek typu NoResultException
     * @return AccountException z komunikatem o nie odnalezieniu kandydata
     */
    static public CandidateException createExceptionCandidateNotExists(NoResultException e) {
        return new CandidateException(ERROR.CANDIDATE_NOT_FOUND.toString(), e);
    }

}

