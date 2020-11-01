package pl.lodz.p.it.ssbd2020.ssbd03.security.services;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import net.minidev.json.JSONArray;
import org.apache.commons.codec.binary.Base64;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.mok.facades.AccountFacadeLocal;
import pl.lodz.p.it.ssbd2020.ssbd03.security.Constants;
import pl.lodz.p.it.ssbd2020.ssbd03.security.utils.JWTCredential;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.*;

/**
 * Klasa odpowiedzialna za wystawianie i walidację żetonów JWT
 */
@RequestScoped
public class TokenService implements ITokenService {

    @Resource(name = "AUTH_SECRET_KEY")
    private String secretKey;

    private long tokenValidityTime;

    @Resource(name = "SECRET_KEY_LENGTH")
    private int SECRET_KEY_LENGTH;

    @Resource(name = "TOKEN_VALIDITY_MINUTES")
    private int TOKEN_VALIDITY_MINUTES;

    @Resource(name = "PASSWORD_RESET_TOKEN_LENGTH")
    private int PASSWORD_RESET_TOKEN_LENGTH;


    @Inject
    private AuthService authService;
    @Inject
    private AccountFacadeLocal accountFacade;

    @PostConstruct
    public void init() {
        tokenValidityTime = TimeUnit.MINUTES.toMillis(TOKEN_VALIDITY_MINUTES);
    }

    /**
     * Metoda wystawiająca żeton
     * @param result obiekt reprezentujący wynik uwierzytelnienia.
     * @return wygenerowany żeton
     * @throws JOSEException
     * @throws AppException wyjątek aplikacyjny
     * @author Michał Tęgi
     */
    @Override
    @PermitAll
    public SignedJWT issueToken(CredentialValidationResult result) throws JOSEException, AppException {
        String email = result.getCallerPrincipal().getName();
        Account account = accountFacade.findByEmail(email);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(email)
                .claim(AUTHORITIES_KEY, result.getCallerGroups())
                .claim(FORCE_PASSWORD_CHANGE, account.getPasswordChangeRequired())
                .expirationTime(new Date(new Date().getTime() + tokenValidityTime))
                .build();
        SignedJWT token = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        JWSSigner signer = new MACSigner(secretKey);
        token.sign(signer);
        return token;
    }


    /**
     * Meteda sprawdzająca poprawność podpisu żetonu.
     * @return true jeśli podpis jest prawidłowy, inaczej false
     * @author Michał Tęgi
     */
    private boolean validateSignature(SignedJWT jwt) {
        try {
            JWSVerifier verifier = new MACVerifier(secretKey);
            return jwt.verify(verifier);
        } catch (JOSEException e) {
            return false;
        }
    }

    /**
     * Metoda przeprowadzająca walidację żetonu
     * @param token żeton JWT
     * @return boolean stanowiący wynik walidacji
     * @author Michał Tęgi
     */
    @Override
    @PermitAll
    public boolean validateToken(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            Date exp = jwt.getJWTClaimsSet().getExpirationTime();
            String sub = jwt.getJWTClaimsSet().getSubject();
            JSONArray json = (JSONArray) jwt.getJWTClaimsSet().getClaim(AUTHORITIES_KEY);
            String[] accessLevelsFromToken = json.toArray(new String[0]);
            Set<String> accessLevelsFromSub = authService.getAccessLevels(sub);
            for (int i = 0; i < accessLevelsFromToken.length; i++) {
                if (!accessLevelsFromSub.contains(accessLevelsFromToken[i]))
                    return false;
            }
            return !exp.before(new Date()) && validateSignature(jwt);
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Metoda odpowiedzialna za pobranie roszczeń z żetonu JWT
     * @param token żeton JWT.
     * @return roszczenia żetonu JWT
     * @author Michał Tęgi
     */
    @Override
    @RolesAllowed(logIn)
    public Map<String, Object> getClaims(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            return jwt.getJWTClaimsSet().getClaims();
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Metoda generująca żeton dla resetu hasła
     * @return wygenerowany żeton
     * @author Michał Tęgi
     */
    @Override
    @PermitAll
    public String generatePasswordResetToken() {
        SecureRandom rand = new SecureRandom();
        byte[] bytes = new byte[PASSWORD_RESET_TOKEN_LENGTH];
        rand.nextBytes(bytes);
        return Base64.encodeBase64URLSafeString(bytes);
    }

    /**
     * Metoda wyciąga role użytkownika oraz jego identyfikator z żetonu JWT
     * @param token żeton JWT.
     * @return obiekt JWTCredential
     * @see JWTCredential
     * @author Michał Tęgi
     */
    @Override
    @PermitAll
    public JWTCredential getCredentials(String token) {
        if (token == null) return GUEST;
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            JWTClaimsSet claimsSet = jwt.getJWTClaimsSet();
            JSONArray authorities = (JSONArray) claimsSet.getClaim(AUTHORITIES_KEY);
            List<String> authoritiesList = new ArrayList<>();
            authorities.forEach(a -> authoritiesList.add(a.toString()));
            return new JWTCredential(claimsSet.getSubject(), new HashSet<>(authoritiesList));
        } catch (ParseException e) {
            return null;
        }
    }
}
