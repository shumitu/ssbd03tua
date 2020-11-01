package pl.lodz.p.it.ssbd2020.ssbd03.interceptors;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.security.enterprise.SecurityContext;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingInterceptor {
    @Inject
    SecurityContext securityContext;

    @AroundInvoke
    public Object additionalInvokeForMethod(InvocationContext invocation) throws Exception {
        StringBuilder sb = new StringBuilder("Business method call: " +
                invocation.getTarget().getClass().getName() + "." +
                invocation.getMethod().getName());


        if (securityContext.getCallerPrincipal() != null)
            sb.append(" Identity: " + securityContext.getCallerPrincipal().getName());

        try {
            Object[] parameters = invocation.getParameters();
            if (parameters != null) {
                for (Object param : parameters) {
                    if (param != null) {
                        sb.append(" Parameter: " + param.getClass().getName() +
                                "=" + param.toString());
                    } else {
                        sb.append(" Parameter: null");
                    }
                }
            }

            Object result = invocation.proceed();

            if (result != null) {
                sb.append(" Return: ").append(result.getClass().getName() + '=').append(result);
            } else {
                sb.append(" Return: null");
            }
            return result;
        } catch (Exception ex) {
            sb.append(" Exception: " + ex.getClass().getName() + " Cause: " + ex.getCause());
            throw ex;
        } finally {
            Logger.getGlobal().log(Level.INFO, sb.toString());
        }
    }
}