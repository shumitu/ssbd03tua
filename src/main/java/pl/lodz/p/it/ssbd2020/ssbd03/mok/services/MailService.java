package pl.lodz.p.it.ssbd2020.ssbd03.mok.services;

import com.sun.mail.smtp.SMTPTransport;
import org.apache.commons.lang3.StringUtils;
import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import javax.interceptor.Interceptors;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasa zajmująca się obługą usług poczty elektroniczej.
 */
@ApplicationScoped
@Interceptors(LoggingInterceptor.class)
public class MailService {

    @Resource(name = "MAIL_SMTP_SERVER")
    private String MAIL_SMTP_SERVER;
    @Resource(name = "MAIL_SMTP_PORT")
    private String MAIL_SMTP_PORT;
    @Resource(name = "MAIL_USERNAME")
    private String MAIL_USERNAME;
    @Resource(name = "MAIL_PASSWORD")
    private String MAIL_PASSWORD;
    @Resource(name = "MAIL_ADDRESS")
    private String MAIL_ADDRESS;

    @Resource(name = "URL")
    private String URL;


    /**
     * Metoda wysyła email na dany adres.
     *
     * @param emailTo     adres odbiorcy
     * @param subject     temat wiadomości
     * @param htmlContent treść wiadomości w języku HTML
     * @throws MessagingException wyjątek związany z usługą wysyłania wiadomości email
     */
    @PermitAll
    public void sendMail(String emailTo, String subject, String htmlContent) throws MessagingException {
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", MAIL_SMTP_SERVER);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", MAIL_SMTP_PORT);

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(MAIL_ADDRESS));
        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(emailTo, false));
        try {
            msg.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
        } catch (UnsupportedEncodingException e) {
            msg.setSubject(subject);
        }

        msg.setContent(
                htmlContent,
                "text/html; charset=utf-8");
        msg.setSentDate(new Date());
        SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
        t.connect(MAIL_SMTP_SERVER, MAIL_USERNAME, MAIL_PASSWORD);
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();
    }
    

    /**
     * Metoda wysyła użytkownikowi powiadomienie email o zmianie jego hasła przez administratora
     * @param email adres mailowy użytkownika
     * @param newPassword hasło ustawione przez administratora. Zostanie ono
     * wysłane do użytkownika.
     * @throws MessagingException wyjątek związany z wysyłaniem wiadomości email
     */
    public void sendPasswordChangedMessage(String email, String newPassword) throws MessagingException {
        Locale locale = getLocale("default");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.MessagesBundle", locale);
        
        sendMail(email, bundle.getString("PasswordChangedTitle"), StringUtils.join(
            "<div>",
                "<h2>", bundle.getString("PasswordChangedMessage") ,"</h2>",
                "<b>", newPassword, "</b>",
                "<p>", bundle.getString("ContactSupportForInfo"), "</p>",
                "<a href=\"https://" + URL + "/login\">", bundle.getString("ClickToGoToPage"), "</a><br><br>",
                "<b>", bundle.getString("Team"), "</b>",
            "</div>"
        ));
    }
    

    /**
     * Metoda wysyła jednorazowy link resetujący hasło
     * @param email adres mailowy użytkownika, który zgłosił chęć zresetowania hasła
     * @param token skrót tokenu pozwalającego zresetować hasło
     * @param localeStr lokalizacja podana w formacie znakowym
     * @throws MessagingException wyjątek związany z wysyłaniem wiadomości email
     */
    public void sendPasswordResetLink(String email, String token, String localeStr) throws MessagingException {
        Locale locale = getLocale(localeStr);
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.MessagesBundle", locale);
        
        sendMail(email, bundle.getString("ResetPasswordTitle"), StringUtils.join(
            "<div>",
                "<h2>", bundle.getString("ResetPasswordMessage") ,"</h2>",
                "<h3>", bundle.getString("ResetLinkWarning"), "</h3>",
                "<a href=\"https://" + URL + "/password_reset?token=", token, "\">", bundle.getString("ResetPasswordTitle"), "</a><br><br>",
                "<b>", bundle.getString("Team"), "</b>",
            "</div>"
        ));
    }
    

    /**
     * Metoda wysyła link aktywacyjny dla nowo stworzonego konta
     * @param email adres na który ma zostać wysłany link aktywacyjny
     * @param accountToken skrót tokenu pozwalającego aktywować konto
     * @param localeStr lokalizacja podana w formacie znakowym
     * @throws MessagingException wyjątek związany z wysyłaniem wiadomości email
     */
    public void sendActivationLink(String email, String accountToken, String localeStr) throws MessagingException {
        Locale locale = getLocale(localeStr);
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.MessagesBundle", locale);
        
        sendMail(email, bundle.getString("VerifyEmailTitle"), StringUtils.join(
            "<div>",
                "<h2>", bundle.getString("VerifyEmailMessage"),"</h2>",
                "<a href=\"https://" + URL + "/activate_account?email=", email, "&token=", accountToken, "\">", bundle.getString("ClickToActivate"), "</a>",
                "<p>", bundle.getString("IgnoreThisEmail"), "</p>",
                "<b>", bundle.getString("Team"),"</b>",
            "</div>"
        ));
    }


    /**
     * Metoda wysyła powiadomienie o aktywacji konta.
     * @param email użytkownika, ka który zostanie wysłana wiadomość
     * @param localeStr lokalizacja podana w formacie znakowym
     * @throws MessagingException wyjątek związany z wysyłaniem wiadomości email
     */
    public void sendActivatedMessage(String email, String localeStr) throws MessagingException {
        Locale locale = getLocale(localeStr);
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.MessagesBundle", locale);
        
        sendMail(email, bundle.getString("AccountConfirmedTitle"), StringUtils.join(
            "<div>",
                "<h2>", bundle.getString("AccountConfirmedMessage"),"</h2>",
                "<a href=\"https://" + URL + "/login\">", bundle.getString("ClickToGoToPage"), "</a><br><br>",
                "<b>", bundle.getString("Team"),"</b>",
            "</div>"
        ));
    }
    

    /**
     * Metoda wysyła powiadomienie o zablokowaniu konta.
     * @param email email użytkownika, na który zostanie wysłana wiadomość
     * @throws MessagingException wyjątek związany z wysyłaniem wiadomości email
     */
    public void sendBlockMessage(String email) throws MessagingException {
        Locale locale = getLocale("default");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.MessagesBundle", locale);
        
        sendMail(email, bundle.getString("AccountBlockedTitle"), StringUtils.join(
            "<div>",
                "<h2>", bundle.getString("AccountBlockedMessage"), "</h2>",
                "<p>", bundle.getString("ContactSupportForInfo"), "</p>",
                "<a href=\"https://" + URL + "/login\">", bundle.getString("ClickToGoToPage"), "</a><br><br>",
                "<b>", bundle.getString("Team"), "</b>",
            "</div>"
        ));
    }

    
    /**
     * Metoda wysyła powiadomienie o zablokowaniu konta na skutek przekroczenia dozwolonej liczby
     * błędnych prób zalogowania się.
     * @param email adres email użytkownika na który zostanie wysłane powiadomienie
     * o zablokowaniu konta.
     * @throws MessagingException wyjątek związany z wysyłaniem wiadomości email
     */
    public void sendAutomaticBlockMessage(String email) throws MessagingException {
        Locale locale = getLocale("default");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.MessagesBundle", locale);
        
        sendMail(email, bundle.getString("AccountBlockedTitle"), StringUtils.join(
            "<div>",
                "<h2>", bundle.getString("BlockedByAttemptsMessage"), "</h2>",
                "<p>", bundle.getString("ContactSupportForInfo"), "</p>",
                "<a href=\"https://" + URL + "/login\">", bundle.getString("ClickToGoToPage"),"</a><br><br>",
                "<b>", bundle.getString("Team"), "</b>",
            "</div>"
        ));
    }


    /**
     * Metoda wysyła powiadomienie o odblokowaniu konta.
     * @param email użytkownika na który zostanie wysłana wiadomość
     * @throws MessagingException wyjątek związany z wysyłaniem wiadomości email
     */
    public void sendUnblockMessage(String email) throws MessagingException {
        Locale locale = getLocale("default");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.MessagesBundle", locale);
        
        sendMail(email, bundle.getString("AccountUnlockedTitle"), StringUtils.join(
            "<div>",
                "<h2>", bundle.getString("AccountUnlockedMessage"),"</h2>",
                "<a href=\"https://" + URL + "/login\">",
                    bundle.getString("ClickToGoToPage"), "</a><br><br>",
                "<b>", bundle.getString("Team"),"</b>",
            "</div>"
        ));
    }
    
    
    /**
     * Metoda przyjmująca znakowe oznaczenie lokalizacji, które przemienia na obiekt
     * W razie rzucenia wyjątku wewnątrz metody zwracana jest lokalizacja domyślna - Polska
     * @param localeStr Znakowe oznaczenie lokalizacji
     * @return Obiekt lokalizacji
     */
    private Locale getLocale(String localeStr) {
        if(localeStr.equals("default")) return new Locale("pl", "PL");
        String[] parts = localeStr.split("-");
        
        try {
            switch (parts.length) {
                case 1:
                    return new Locale(parts[0]);
                case 2:
                    return new Locale(parts[0], parts[1]);
                default:
                    return new Locale(parts[0], parts[1], parts[2]);
            }
        } catch (NullPointerException npe) {
            Logger.getGlobal().log(Level.SEVERE, npe.getMessage());
            return new Locale("pl", "PL");
        }
    }
}