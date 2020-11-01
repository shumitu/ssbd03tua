package pl.lodz.p.it.ssbd2020.ssbd03.mok.services;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.CaptchaException;
import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import javax.interceptor.Interceptors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton stanowiący serwis dla Captchy
 */
@ApplicationScoped
@Interceptors(LoggingInterceptor.class)
public class CaptchaService {

    @Resource(name = "CAPTCHA_KEY")
    private String captchaKey;

    @Resource(name = "CAPTCHA_ENDPOINT")
    private String endpoint;


    /**
     * Metoda wysyła zapytanie do servera repatcha, aby zweryfikować poprawność przesłanego stringa captcha.
     * @param value Przesłany przez klienta String reCaptcha
     */
    @PermitAll
    public void validateRequest(String value) throws CaptchaException {
        try {
            String url = endpoint,
                    params = "secret=" + captchaKey + "&response=" + value;

            HttpURLConnection http = (HttpURLConnection) new URL(url).openConnection();
            http.setDoOutput(true);
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded; charset=UTF-8");
            OutputStream out = http.getOutputStream();
            out.write(params.getBytes("UTF-8"));
            out.flush();
            out.close();

            InputStream res = http.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(res, "UTF-8"));

            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            JSONObject json = new JSONObject(sb.toString());
            res.close();

            if (!json.getBoolean("success"))
                throw CaptchaException.createExceptionCaptchaIncorrect();

        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            throw CaptchaException.createExceptionErrorValidatingCaptcha();
        }

    }
}
