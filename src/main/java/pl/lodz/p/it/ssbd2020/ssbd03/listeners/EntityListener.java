package pl.lodz.p.it.ssbd2020.ssbd03.listeners;

import javax.inject.Inject;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.security.enterprise.SecurityContext;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EntityListener {
    @Inject
    SecurityContext securityContext;

    public EntityListener() {
    }

    @PrePersist
    public void beforeInsert(Object entity) {
        StringBuilder sb = new StringBuilder("Entity insert: " + entity);
        if (securityContext.getCallerPrincipal() != null)
            sb.append(" Identity: " + securityContext.getCallerPrincipal().getName());
        Logger.getGlobal().log(Level.INFO, sb.toString());
    }

    @PostUpdate
    public void afterUpdate(Object entity) {
        StringBuilder sb = new StringBuilder("Entity update: " + entity);
        if (securityContext.getCallerPrincipal() != null)
            sb.append(" Identity: " + securityContext.getCallerPrincipal().getName());
        Logger.getGlobal().log(Level.INFO, sb.toString());
    }
}
