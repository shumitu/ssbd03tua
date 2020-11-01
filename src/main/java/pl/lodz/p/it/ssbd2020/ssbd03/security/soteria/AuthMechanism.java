package pl.lodz.p.it.ssbd2020.ssbd03.security.soteria;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import pl.lodz.p.it.ssbd2020.ssbd03.config.CorsFilter;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd03.security.services.ITokenService;
import pl.lodz.p.it.ssbd2020.ssbd03.security.utils.JWTCredential;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.AUTHORIZATION_HEADER;
import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.BEARER;

/**
 * Implementacja HttpAuthenticationMechanism wykorzystująca tokeny JWT
 * @see HttpAuthenticationMechanism
 */
@RequestScoped
@Interceptors(LoggingInterceptor.class)
public class AuthMechanism implements HttpAuthenticationMechanism {

    @Inject
    private IdentityStoreImpl identityStore;
    @Inject
    private ITokenService tokenService;

    /**
     * Implementacja metody validateRequest z interfejsu HttpAuthenticationMechanism.
     * Metoda ta odpowiada za kontrolę dostępu do zasobów. Jest ona wykonywana dla każdego żądania.
     *
     * @param request  żądanie jakie wykonał klient.
     * @param response odpowiedź jaka zostanie wysłana klientowi.
     * @param context  obiekt zawierający informacje o żądaniu http.
     * @return wynik autoryzacji dla danego żądania.
     * @see HttpAuthenticationMechanism
     */
    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext context) {
        CorsFilter.addCors(response);
        String token = getTokenFromHeader(context);
        String email = null;
        String password = null;

        if (token != null) {
            return validateWithToken(token, context);
        } else {
            String path = request.getPathInfo();
            if (path == null) return context.doNothing();
            else if (path.equals("/login")) {
                email = request.getParameter("email");
                password = request.getParameter("password");
            }
            if (email != null && password != null) {
                CredentialValidationResult result = identityStore.validate(new UsernamePasswordCredential(email, password));
                if (result.getStatus() == CredentialValidationResult.Status.VALID) {
                    return createTokenAndNotifyAboutLogin(result, context);
                }
                return context.responseUnauthorized();
            } else if (context.isProtected()) {
                return context.responseUnauthorized();
            }
            return context.doNothing();
        }
    }

    /**
     * Metoda odpowiada za pobranie tokenu z nagłówka Authorization żądania.
     *
     * @param context obiekt zawierający informacje o żądaniu http.
     * @return hash tokenu JWT.
     */
    private String getTokenFromHeader(HttpMessageContext context) {
        String authorizationHeader = context.getRequest().getHeader(AUTHORIZATION_HEADER);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            return authorizationHeader.substring(BEARER.length());
        }
        return null;
    }

    /**
     * Metoda odpowiada za autoryzację żądania wykorzystując token JWT.
     *
     * @param token   hash tokenu JWT
     * @param context obiekt zawierający informacje o żądaniu http
     * @return wynik autoryzacji dla danego żądania
     */
    private AuthenticationStatus validateWithToken(String token, HttpMessageContext context) {
        if (tokenService.validateToken(token)) {
            JWTCredential principal = tokenService.getCredentials(token);
            return context.notifyContainerAboutLogin(principal.getPrincipal(), principal.getAuthorities());
        }
        return context.responseUnauthorized();
    }

    /**
     * Metoda odpowiada za autoryzację żądania wykorzystując dane klienta
     *
     * @param credentialValidationResult obiekt reprezentujący wynik uwierzytelnienia
     * @param context                    obiekt zawierający informacje o żądaniu http
     * @return wynik autoryzacji dla danego żądania
     */
    private AuthenticationStatus createTokenAndNotifyAboutLogin(CredentialValidationResult credentialValidationResult, HttpMessageContext context) {
        try {
            SignedJWT token = tokenService.issueToken(credentialValidationResult);
            context.getResponse().setHeader(AUTHORIZATION_HEADER, BEARER + token.serialize());
        } catch (JOSEException | AppException e) {
            context.responseUnauthorized();
            e.printStackTrace();
        }
        return context.notifyContainerAboutLogin(credentialValidationResult);
    }
}
