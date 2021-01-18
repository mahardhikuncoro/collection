package base.utils.enm;

public enum ResponseCode {
    OK,
    UNAUTHORIZED,
    INACTIVE,
    EMPTY,
    INCOMPATIBLE_VERSION,
    RUNTIME_EXCEPTION,
    INVALID_APP,
    APP_NULL,
    INVALID_SUB_CODE,
    TASK_ASSIGN,
    INVALID_DEVICE,
    IS_NOT_EMPLOYEE,IS_NOT_CONNECTED_TOHRD,UNAUTHORIZED_HRD,IS_RESIGN,IS_NOTSAMEPOSITION,DUPLICATE
    ;

    public static ResponseCode byOrdinal(int ordinal) {
        switch(ordinal) {
            case 0:
                return OK;
            case 1:
                return UNAUTHORIZED;
            case 2:
                return INACTIVE;
            case 3:
                return EMPTY;
            case 6:
                return INCOMPATIBLE_VERSION;
            case 9:
                return RUNTIME_EXCEPTION;
            case 41:
                return INVALID_APP;
            case 42:
                return APP_NULL;
            case 43:
                return INVALID_SUB_CODE;
            case 44:
                return INVALID_DEVICE;
            case 7:
                return TASK_ASSIGN;
            case 45:
                return IS_NOT_EMPLOYEE;
            case 46:
                return IS_NOT_CONNECTED_TOHRD;
            case 47:
                return UNAUTHORIZED_HRD;
            case 48:
                return IS_RESIGN;
            case 49:
                return IS_NOTSAMEPOSITION;
            case 5:
                return DUPLICATE;
        }
        return null;
    }

}
