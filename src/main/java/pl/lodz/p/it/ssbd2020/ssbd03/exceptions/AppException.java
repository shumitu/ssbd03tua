package pl.lodz.p.it.ssbd2020.ssbd03.exceptions;

import javax.ejb.ApplicationException;

/**
 * Klasa bazowa wyjątku aplikacyjnego.
 */
@ApplicationException(rollback = true)
public class AppException extends Exception {

    /**
     * Konstruktor
     * @param message komunikat błedu
     * @param cause   błąd który wystąpił w aplikacji
     */
    AppException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Konstruktor
     * @param message komunikat błedu
     */
    public AppException(String message) {
        super(message);
    }

    /**
     * @param e wyjątek rzucony w trakcie kominikacji z bazą danych.
     * @return AppException z komunikatem o problemie z wykonaniem zapytania do bazy dadnych.
     */
    static public AppException createExceptionDatabaseQueryProblem(Throwable e) {
        return new AppException(ERROR.DATABASE_QUERY_PROBLEM.toString(), e);
    }

    /**
     * @param e wyjątek rzucony w trakcie kominikacji z bazą danych.
     * @return AppException z komunikatem o problemie z połączeniem z bazą danych.
     */
    static public AppException createExceptionDatabaseConnectionProblem(Throwable e) {
        return new AppException(ERROR.DATABASE_CONNECTION_PROBLEM.toString(), e);
    }
    
    /**
     * @return AppException z komunikatem o niepowodzeniu ponowienia transakcji.
     */
    static public AppException createExceptionForRepeatedTransactionRollback() {
        return new AppException(ERROR.REPEATED_TRANSACTION_ROLLBACK.toString());
    }

    static public AppException createOtherException() {
        return new AppException(ERROR.OTHER_EXCEPTION.toString());
    }

    static public AppException createNotfoundException() {
        return new AppException(ERROR.NOT_FOUND_EXCEPTION.toString());
    }

}
