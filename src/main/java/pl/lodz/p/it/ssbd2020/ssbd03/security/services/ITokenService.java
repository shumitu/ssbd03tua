package pl.lodz.p.it.ssbd2020.ssbd03.security.services;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.security.utils.JWTCredential;

import javax.security.enterprise.identitystore.CredentialValidationResult;
import java.util.Map;

/**
 * Interjes menadżera tokenów JWT, korzystający z biblioteki com.nimbusds.jose
 */
public interface ITokenService {
    /**
     * Metoda wydaje token na podstawie tożsamości użytkownika.
     *
     * @param result obiekt reprezentujący wynik uwierzytelnienia.
     * @return podpisany token JWT.
     * @throws JOSEException jeśli wystąpi błąd przy generowaniu tokenu
     * @author Michał Tęgi
     */
    SignedJWT issueToken(CredentialValidationResult result) throws JOSEException, AppException;

    /**
     * Metoda sprawdzająca poprawność tokenu JWT.
     * @param token tokenJWT
     * @return false jeśli podpis jest nieprawidłowy lub ważność tokenu wygasła, inaczej true.
     * @author Michał Tęgi
     */
    boolean validateToken(String token);

    /**
     * Metoda zwracająca dane użytkownika z tokenu JWT.
     * @param token token JWT.
     * @return login użytkownika oraz jego role.
     * @author Michał Tęgi
     */
    JWTCredential getCredentials(String token);

    /**
     * Metoda zwracająca zawartość tokenu JWT.
     * @param token token JWT.
     * @return zawartość tokenu JWT.
     * @author Michał Tęgi
     */
    Map<String, Object> getClaims(String token);

    /**
     * Metoda generuje token zmiany hasła
     *
     * @return string w formacie Base64
     * @author Michał Tęgi
     */
    String generatePasswordResetToken();
}
