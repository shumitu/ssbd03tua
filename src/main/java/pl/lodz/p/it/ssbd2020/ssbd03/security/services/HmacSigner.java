package pl.lodz.p.it.ssbd2020.ssbd03.security.services;

import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

/**
 * Klasa odpowiedzialna za podpisywanie HMAC
 */
public class HmacSigner {

    private SecretKey secretKey;

    private static HmacSigner instance = null;

    /**
     * Konstruktor bezparametrowwy
     */
    private HmacSigner() {
        try {
            KeyGenerator gen = KeyGenerator.getInstance("HMACSHA256");
            secretKey = gen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda zwraca obiekt danej klasy, zgodnie z wzorcem singleton
     * @return instancja klasy HmacSigner
     */
    public static HmacSigner getInstance() {
        if (instance == null)
            instance = new HmacSigner();
        return instance;
    }

    /**
     * Metoda oblicza skrót HmacSHA256 wersji encji
     * @param version wersja encji
     * @return skrót HmacSHA256
     * @throws AppException wyjątek aplikacyjny
     */
    public static String getEtag(Long version) throws AppException {
        return HmacSigner.sign(version.toString().getBytes());
    }

    /**
     * Metoda sprawdza czy dany skrót odpowiada wersji encji
     * @param etag skrót wersji
     * @param version wersja encji
     * @return true jeśli skrót jest poprawny
     * @throws AppException wyjątek aplikacyjny
     */
    public static boolean verifyEtag(String etag, Long version) throws AppException {
        return HmacSigner.verify(etag, version.toString().getBytes());
    }

    /**
     * Metoda oblicza skrót HmacSHA256
     * @param digest ciąg bajtów do podpisania
     * @return skrót HmacSHA256
     * @throws AppException wyjątek aplikacyjny
     */
    public static String sign(byte[] digest) throws AppException {
        try {
            HmacSigner signer = HmacSigner.getInstance();
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            sha256_HMAC.init(new SecretKeySpec(signer.secretKey.getEncoded(), "HmacSHA256"));
            byte[] result = sha256_HMAC.doFinal(digest);
            return new String(Hex.encode(result));
        } catch (Exception e) {
            e.printStackTrace();
            throw AppException.createOtherException();
        }
    }

    /**
     * Metoda weryfikuje poprawność podpisu
     * @param digest ciąg bajtów do zweryfikowania
     * @param signature skrót HmacSHA256
     * @return true jeśli skrót się zgadza
     * @throws AppException wyjątek aplikacyjny
     */
    public static boolean verify(String signature, byte[] digest) throws AppException {
        try {
            byte[] bytes = Hex.decode(signature);
            HmacSigner signer = HmacSigner.getInstance();
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            sha256_HMAC.init(new SecretKeySpec(signer.secretKey.getEncoded(), "HmacSHA256"));
            byte[] result = sha256_HMAC.doFinal(digest);
            return Arrays.areEqual(bytes, result);
        } catch (Exception e) {
            e.printStackTrace();
            throw AppException.createOtherException();
        }
    }
}
