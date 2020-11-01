package pl.lodz.p.it.ssbd2020.ssbd03.security.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.SystemExceptionInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd03.security.services.TokenService;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.*;

/**
 * Klasa obsługująca zasób rest /login
 */
@RequestScoped
@Interceptors(SystemExceptionInterceptor.class)
@Path("/login")
@DeclareRoles(logIn)
public class LoginEndpoint {

    @Inject
    TokenService tokenService;

    /**
     * Metoda pozwala pobrać użytkownikowi dane o swojej tożsamości, rolach oraz
     * przydzielonym tokenie JWT
     * @return JSON zawierający dane zapisane w tokenie JWT.
     * @author Michał Tęgi
     */
    @GET
    @RolesAllowed(logIn)
    public Response getCredentials(@HeaderParam(AUTHORIZATION_HEADER) String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            String token = authorizationHeader.substring(BEARER.length());
            return Response.status(Response.Status.OK).entity(tokenService.getClaims(token)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    /**
     * Metoda służąca do przesłania loginu i hasła, obsługa żądania zachodzi w
     * security.AuthMechanism
     * @return Kod 200 jeśli udało się zalogować, 401 jeśli nie.
     * @author Michał Tęgi
     */
    @POST
    @PermitAll
    public Response login() {
        return Response.ok().build();
    }


}
