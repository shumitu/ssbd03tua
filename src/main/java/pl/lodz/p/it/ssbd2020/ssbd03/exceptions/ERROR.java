package pl.lodz.p.it.ssbd2020.ssbd03.exceptions;

public enum ERROR {
    /*
        Concurrency
    */
    RESOURCE_MODIFIED("error:concurrency.resource_modified"),
    /*
        Database
     */
    DATABASE_QUERY_PROBLEM("error:db.query"),
    DATABASE_CONNECTION_PROBLEM("error:db.connection"),
    NO_TRANSACTION_IN_PROGRESS("error:jta.transaction"),
    /*
        Account
     */
    ACCOUNT_NOT_FOUND("error:account.notfound"),
    EMPLOYEE_NOT_FOUND("error:employee.notfound"),
    CANDIDATE_NOT_FOUND("error:candidate.notfound"),
    PASSWORD_TOO_SHORT("error:account.password_too_short"),
    INCORRECT_PASSWORD("error:account.wrong_password"),
    EMAIL_TAKEN("error:account.email_taken"),
    ACTIVATION_FAILED("error:account.activation_failed"),
    INCORRECT_MOTTO("error:account.incorrect_motto"),
    INCORRECT_FIRSTNAME("error:account.wrong_firstname"),
    INCORRECT_LASTNAME("error:account.wrong_lastname"),
    INCORRECT_EMAIL("error:account.wrong_email"),
    ALREADY_BLOCKED("error:account.already_blocked"),
    ALREADY_UNBLOCKED("error:account.already_unblocked"),
    ALREADY_CONFIRMED("error:account.already_confirmed"),
    LOGIN_INFO_NOT_FOUND("error:account.login_info_notfound"),
    LOGIN_INFO_WRONG_IP_ADDRESS("error:account.login_info_wrong_ip_address"),
    NOT_CONFIRMED("error:account.not_confirmed"),
    INCORRECT_PHRASE("error:filterPhrase.incorrect"),
    /*
        AccessLevel
     */
    ACCESS_LEVEL_ALREADY_GRANTED("error:access_level.already_granted"),
    ACCESS_LEVEL_NAME_NOT_RECOGNIZED("error:access_level.not_recognized"),
    ACCESS_LEVEL_NOT_GRANTED("error:access_level.not_granted"),
    /*
        Offer
     */
    OFFER_NOT_FOUND("error:offer.not_found"),
    OFFER_ALREADY_OPEN("error:offer.already_open"),
    OFFER_ALREADY_CLOSED("error:offer.already_closed"),
    OFFER_HAS_APPLICATIONS_DELETE("error:offer.has_applications.delete"),
    OFFER_HAS_APPLICATIONS_ASSIGN_SHIP("error:offer.has_applications.assign_starship"),
    OFFER_DELETE_IS_NOT_HIDDEN("error:offer.delete_is_not_hidden"),
    OFFER_DELETE_IS_OPEN("error:offer.delete_is_open"),
    OFFER_HAS_APPLICATIONS_EDIT("error:offer.has_applications.edit"),
    OFFER_ALREADY_VISIBLE("error:offer.already_visible"),
    OFFER_ALREADY_HIDDEN("error:offer.already_hidden"),
    INCORRECT_DESCRIPTION("error:offer.incorrect_description"),
    INCORRECT_DESTINATION("error:offer.incorrect_destination"),
    INCORRECT_DATES_ORDER("error:offer.incorrect_dates_order"),
    INCORRECT_START_DATE("error:offer.incorrect_start_date"),
    INCORRECT_END_DATE("error:offer.incorrect_end_date"),
    INCORRECT_PRICE("error:offer.incorrect_price"),
    INCORRECT_TOTAL_COST("error:offer.incorrect_total_cost"),
    INCORRECT_TOTAL_WEIGHT("error:offer.incorrect_total_weight"),
    INCORRECT_STARSHIP_ID("error:offer.incorrect_starship_id"),
    INCORRECT_STARSHIP_STATUS("error:offer.incorrect_starship_status"),
    OVERLAPPING_OFFER("error:offer.overlapping_offer"),

    /*
        Starship
     */
    NOT_POSITIVE_VALUE("error:starship.not_positive_value"),
    WRONG_YEAR("error:starship.year.incorrect"),
    STARSHIP_NAME_TAKEN("error:starship.name.taken"),
    STARSHIP_NAME_INCORRECT("error:starship.name.incorrect"),
    STARSHIP_NAME_TOO_LONG("error:starship.name.too_long"),
    STARSHIP_NAME_TOO_SHORT("error:starship.name.too_short"),
    STARSHIP_ALREADY_ACTIVE("error:starship.already_active"),
    STARSHIP_ALREADY_INACTIVE("error:starship.already_inactive"),
    STARSHIP_NOT_OPERATIONAL("error:starship.not_operational"),
    STARSHIP_NOT_FOUND("error:starship.not_found"),
    STARSHIP_ASSIGNED_TO_OFFER("error:starship.assigned"),
    STARSHIP_ALREADY_ASSIGNED("error:starship.already_assigned"),
    STARSHIP_ASSIGNED_SELF("error:starship.assigned_self"),
    /*
        Application
    */
    INCORRECT_MOTIVATIONAL_LETTER("error:application.incorrect_motivational_letter"),
    INCORRECT_EXAMINATION_CODE("error:application.incorrect_examination_code"),
    INCORRECT_WEIGHT("error:application.incorrect_weight"),
    APPLICATION_NOT_FOUND("error:application.not_found"),
    APPLICATION_HAS_REVIEW("error:application.has_review"),
    OFFER_CLOSED("error:application.offer_closed"),
    OFFER_HAS_CHANGED("error:application.offer_changed"),
    ALREADY_APPLIED("error:application.already_applied"),
    /*
        Review
    */
    REVIEW_NOT_FOUND("error:review.not_found"),
    REVIEW_ALREADY_EXISTS("error:review.already_exists"),
    REVIEW_EXCEEDS_WEIGHT_LIMIT("error:review.weight_limit_exceeded"),
    REVIEW_APPLICATION_CHANGED("error:review.application_changed"),
    /*
        Category
    */
    CATEGORY_NOT_FOUND("error:category.not_found"),
    /*
        Change Password
    */
    PASSWORD_DOES_NOT_MATCH("error:change_password.password_does_not_match"),
    THE_SAME_PASSWORD("error:change_password.password_is_the_same"),
    WRONG_PASSWORD("error:change_password.wrong_password"),
    FORBIDDEN_EMAIL("error:change_password.forbidden_email"),
    OLD_PASSWORD_TOO_SHORT("error:change_password.old_password_too_short"),
    NEW_PASSWORD_TOO_SHORT("error:change_password.new_password_too_short"),
    CANT_CHANGE_OWN_PASSWORD("error:change_password.cant_change_own_password"),
    /*
        Password Reset Token
     */
    TOKEN_NOT_FOUND("error:password_reset_token.notfound"),
    TOKEN_EXPIRED("error:password_reset_token.expired"),
    /*
        Transaction
     */
    REPEATED_TRANSACTION_ROLLBACK("error:transaction.repeated_transaction_rollback"),
    TRANSACTION_ROLLBACK("error:transaction.transaction_rollback"),

    /*
    Captcha
     */
    CAPTCHA_INCORRECT("error:captcha.incorrect"),
    CAPTCHA_CONNECTION_PROBLEM("error:captcha.cant_access_validation_server"),
    /*
        Other
     */
    OTHER_EXCEPTION("error:other"),

    /*
        Not Found
     */
    NOT_FOUND_EXCEPTION("error:not_found");

    private final String key;

    ERROR(final String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }
}
