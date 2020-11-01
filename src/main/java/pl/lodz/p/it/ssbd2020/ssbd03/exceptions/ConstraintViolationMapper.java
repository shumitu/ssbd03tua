package pl.lodz.p.it.ssbd2020.ssbd03.exceptions;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Mapper dla wyjątków związanych z ConstraintViolation
 */
@Provider
public class ConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(prepareMessage(exception))
                .type("text/plain")
                .build();
    }

    private String prepareMessage(ConstraintViolationException exception) {
        StringBuilder msg = new StringBuilder();
        for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
            String property = cv.getPropertyPath().toString();
            String trimmed = property.substring(property.lastIndexOf('.') + 1).trim();
            msg.append(trimmed).append(": ").append(cv.getMessage()).append("\n");
        }
        return msg.toString();
    }
}
