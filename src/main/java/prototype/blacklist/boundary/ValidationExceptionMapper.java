package prototype.blacklist.boundary;

import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {       
        return Response.status(Response.Status.BAD_REQUEST).header("X-Validation-Errors",
                exception.getConstraintViolations().stream().map(v -> v.getPropertyPath() + " " + v.getMessage()).collect(Collectors.joining(", "))
            ).build();
    }

}
