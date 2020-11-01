package pl.lodz.p.it.ssbd2020.ssbd03.core;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractService {

    @Resource
    protected SessionContext sessionContext;
    private String transactionId;

    private boolean lastTransactionRollback;

    private int callCounter = 0;

    @Resource(name = "TRANSACTION_LIMIT")
    private int maxTransactionCount;

    protected static final Logger LOGGER = Logger.getGlobal();

    @PermitAll
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public boolean isLastTransactionRollback() {
        return lastTransactionRollback;
    }

    public void afterBegin() {
        transactionId = Long.toString(System.currentTimeMillis())
                + ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        LOGGER.log(Level.INFO, "Transaction TXid={0} started in {1}, identity: {2}, attempt: {3}",
                new Object[]{transactionId, this.getClass().getName(), sessionContext.getCallerPrincipal().getName(),
                        callCounter});
    }

    public void beforeCompletion() {
        LOGGER.log(Level.INFO, "Transaction TXid={0} before completion in {1}, identity: {2}, attempt: {3}",
                new Object[]{transactionId, this.getClass().getName(), sessionContext.getCallerPrincipal().getName(),
                        callCounter});
    }

    public void afterCompletion(boolean committed) {
        lastTransactionRollback = !committed;
        LOGGER.log(Level.INFO, "Transaction TXid={0} after completion in {1}, identity: {2}, attempt: {3}, result: {4}",
                new Object[]{transactionId, this.getClass().getName(), sessionContext.getCallerPrincipal().getName(),
                        callCounter, committed ? "COMMIT" : "ROLLBACK"});
        callCounter++;
    }

    @PermitAll
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public int getCallCounter() {
        return callCounter;
    }

    @PermitAll
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public int getMaxTransactionCount() {
        return maxTransactionCount;
    }
}