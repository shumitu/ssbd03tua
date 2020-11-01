package pl.lodz.p.it.ssbd2020.ssbd03.security.services;

import org.bouncycastle.util.encoders.Hex;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Klasa odpowiedzialna za obsługę szyfrowania SHA256
 */
@ApplicationScoped
public class HashService {

    /**
     * Metoda szyfrująca
     * @param string wejściowa wiadomość
     * @return zaszyfrowana wiadomość
     * @throws AppException wyjątek aplikacyjny
     * @author Michał Tęgi
     */
    @PermitAll
    public String SHA256(String string) throws AppException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(string.getBytes(StandardCharsets.UTF_8));
            return new String(Hex.encode(hash));
        } catch (NoSuchAlgorithmException e) {
            throw AppException.createOtherException();
        }
    }

}
