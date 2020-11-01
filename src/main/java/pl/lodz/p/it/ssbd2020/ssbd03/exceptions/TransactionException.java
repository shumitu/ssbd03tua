package pl.lodz.p.it.ssbd2020.ssbd03.exceptions;

/**
 * Wyjątki związane z transakcjami
 */
public class TransactionException extends AppException {
    /**
     * Konstruktor
     * @param message komunikat błedu
     * @param cause   błąd który wystąpił w aplikacji
     * @see AppException
     */
    private TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Konstruktor
     * @param message komunikat błedu
     * @see AppException
     */
    private TransactionException(String message) {
        super(message);
    }

    /**
     * @return TransactionException opakowujący wyjątek EJBTransactionRolledbackException.
     */
    static public TransactionException createExceptionTransactionRollback() {
        return new TransactionException(ERROR.TRANSACTION_ROLLBACK.toString());
    }
}
