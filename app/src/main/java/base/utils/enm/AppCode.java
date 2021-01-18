package base.utils.enm;

public enum AppCode {
    UBER,
    TEAM_SUPPORT,
    COLLECTION,
    TEAM_SUPPORT_WO;

    public static AppCode byOrdinal(int ordinal) {
        switch(ordinal) {
            case 0:
                return UBER;
            case 1:
                return TEAM_SUPPORT;
            case 2:
                return COLLECTION;
            case 3:
                return TEAM_SUPPORT_WO;
        }
        return null;
    }

    public static String getRoleByOrdinal(int ordinal) {
        switch(ordinal) {
            case 0:
                return "UBER";
            case 1:
                return "ROLE__COLLECTION";	//return "TEAM__SUPPORT";
            case 2:
                return "ROLE__COLLECTION";
            case 3:
                return "ROLE__COLLECTION";
        }
        return null;
    }

    public static String getMobileAppNameByOrdinal(int ordinal) {
        switch(ordinal) {
            case 0:
                return "MOBILE_APP_U";
            case 1:
                return "MOBILE_APP_TS";
            case 2:
                return "MOBILE_APP_COL";
            case 3:
                return "MOBILE_APP_TSWO";
        }
        return null;
    }


}
