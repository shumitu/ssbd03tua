package pl.lodz.p.it.ssbd2020.ssbd03.exceptions;

/**
 * Wyjątki powiązane z Captchą
 */
public class CaptchaException extends AppException {

    /**
     * Konstruktor
     * @param message komunikat błedu
     * @param cause   błąd który wystąpił w aplikacji
     * @see AppException
     */
    protected CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Konstruktor
     * @param message komunikat błedu
     * @see AppException
     */
    protected CaptchaException(String message) {
        super(message);
    }

    /**
     * Wyjątek rzucany gdy Captcha jest nieprawidłowa
     * @return AppException z komunikatem o nieprawidłowej Captchy
     */
    static public CaptchaException createExceptionCaptchaIncorrect() {
        return new CaptchaException(ERROR.CAPTCHA_INCORRECT.toString());
    }

    /**
     * Wyjątek rzucany gdy podczas sprawdzania Captchy wystąpił błąd
     * @return AppException z komunikatem o błędzie podczas walidacji
     */
    static public CaptchaException createExceptionErrorValidatingCaptcha() {
        return new CaptchaException(ERROR.CAPTCHA_CONNECTION_PROBLEM.toString());
    }
}
