package base.network;

public enum ResponseStatus {

    NO_INTERNET_CONNECTION,
    CONNECTION_ERROR,
    BACKEND_ERROR,
    INCOMPATIBLE_VERSION,
    UNAUTHORIZED,
    INACTIVE,
    ERROR,
    DUPLICATE,
    EMPTY,
    OK,
    DUPLICATEAPPLY,
    UPDATE_SUCCESSFUL,
    PASSWORD_EMPTY,
    UPDATE_FAILED,
    PASSWORD_FOUND,
    USER_NOT_FOUND,
    USER_PASSWORD_EMPTY,
    LOGIN_SUCCESSFUl,
    PASSWORD_NOT_MATCH,
    PHONE_NUMBER_NOT_FOUND,
    PASSWORD_SUCCESSFULLY_CHANGED,
    CURRENT_PASSWORD_NOT_VALID,
    EMAIL_CONFIRMATION_SENT,
    ISREGISTRATION_VALUE_1,
    REMINDER_TRUE,
    REMINDER_FALSE,
    CUSTOMER_ID_NOT_FOUND,
    INVALID_TOKEN,
    topassigned,
    ;

    public static ResponseStatus byName(String name) {
        if (NO_INTERNET_CONNECTION.name().equals(name))
            return NO_INTERNET_CONNECTION;
        if (CONNECTION_ERROR.name().equals(name))
            return CONNECTION_ERROR;
        if (BACKEND_ERROR.name().equals(name))
            return BACKEND_ERROR;
        if (INCOMPATIBLE_VERSION.name().equals(name))
            return INCOMPATIBLE_VERSION;
        if (UNAUTHORIZED.name().equals(name))
            return UNAUTHORIZED;
        if (INACTIVE.name().equals(name))
            return INACTIVE;
        if (ERROR.name().equals(name))
            return ERROR;
        if (DUPLICATE.name().equals(name))
            return DUPLICATE;
        if (EMPTY.name().equals(name))
            return EMPTY;
        if ("1".equals(name))
            return OK;
        if (PASSWORD_EMPTY.name().equals(name))
            return PASSWORD_EMPTY;
        if (UPDATE_FAILED.name().equals(name))
            return UPDATE_FAILED;
        if (UPDATE_SUCCESSFUL.name().equals(name))
            return UPDATE_SUCCESSFUL;
        if (PASSWORD_FOUND.name().equals(name))
            return PASSWORD_FOUND;
        if (USER_PASSWORD_EMPTY.name().equals(name))
            return USER_PASSWORD_EMPTY;
        if (LOGIN_SUCCESSFUl.name().equals(name))
            return LOGIN_SUCCESSFUl;
        if (PASSWORD_NOT_MATCH.name().equals(name))
            return PASSWORD_NOT_MATCH;
        if (PHONE_NUMBER_NOT_FOUND.name().equals(name))
            return PHONE_NUMBER_NOT_FOUND;
        if (EMAIL_CONFIRMATION_SENT.name().equals(name))
            return EMAIL_CONFIRMATION_SENT;
        if (REMINDER_TRUE.name().equals(name))
            return REMINDER_TRUE;
        if (REMINDER_FALSE.name().equals(name))
            return REMINDER_FALSE;
        if (CUSTOMER_ID_NOT_FOUND.name().equals(name))
            return CUSTOMER_ID_NOT_FOUND;
        if (topassigned.name().equals(name))
            return topassigned;

        return null;
    }

    public static ResponseStatus byString(String name){
        return null;
    }

}
