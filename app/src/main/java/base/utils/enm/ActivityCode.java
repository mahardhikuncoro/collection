package base.utils.enm;

public enum  ActivityCode {
    DEFAULT,
    SIGN_IN,
    SIGN_OUT,
    SAVE,
    SUBMIT;

    public static ActivityCode byOrdinal(int ordinal) {
        switch(ordinal) {
            case 0:
                return DEFAULT;
            case 1:
                return SIGN_IN;
            case 2:
                return SIGN_OUT;
            case 3:
                return SAVE;
            case 4:
                return SUBMIT;
        }
        return null;
    }
}
