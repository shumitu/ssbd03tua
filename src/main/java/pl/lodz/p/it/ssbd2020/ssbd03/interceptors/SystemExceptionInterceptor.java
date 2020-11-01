package pl.lodz.p.it.ssbd2020.ssbd03.interceptors;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.ExceptionDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.ERROR;

import javax.ejb.EJBException;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemExceptionInterceptor {
    @AroundInvoke
    public Object additionalInvokeForMethod(InvocationContext invocation) throws Exception {
        try {
            return invocation.proceed();
        }
        catch (EJBException e){
            Logger.getGlobal().log(Level.SEVERE,"System exception: " + e.getMessage() + " Cause: " + e.getCause());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
        catch (Exception e){
            Logger.getGlobal().log(Level.INFO,"Unknown exception: " + e.getMessage() + " Cause: " + e.getCause());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    new ExceptionDTO(ERROR.OTHER_EXCEPTION.toString())).build();
        }
    }
}
