package pl.lodz.p.it.ssbd2020.ssbd03.interceptors;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.ExceptionDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class WebExceptionMapper implements ExceptionMapper<WebApplicationException> {
    @Override
    public Response toResponse(WebApplicationException exception) {
        if(exception instanceof ClientErrorException){
            Logger.getGlobal().log(Level.SEVERE, "Not Found Exception: " + exception.toString() +
                    " Cause: " + exception.getCause());
            return Response.status(Response.Status.NOT_FOUND).entity(new ExceptionDTO(
                    AppException.createNotfoundException().getMessage())).build();
        }
        if(exception instanceof ServerErrorException){
            Logger.getGlobal().log(Level.SEVERE, "Server Error Exception: " + exception.toString() +
                    " Cause: " + exception.getCause());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(
                    AppException.createOtherException().getMessage())).build();
        }
        Logger.getGlobal().log(Level.SEVERE, "Unknown Web Exception: " + exception.toString() +
                " Cause: " + exception.getCause());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(
                AppException.createOtherException().getMessage())).build();
    }
}
