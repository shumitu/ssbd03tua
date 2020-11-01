package pl.lodz.p.it.ssbd2020.ssbd03.interceptors;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.ExceptionDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class AllExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        Logger.getGlobal().log(Level.SEVERE, "Unknown Other Exception: " + exception.toString() +
                " Cause: " + exception.getCause());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionDTO(
                AppException.createOtherException().getMessage())).build();
    }
}
