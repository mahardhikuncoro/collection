package base.utils.enm;

public enum StringEnum {
    TOPASSIGNED,
    INVALID_TOKEN;
//    COLLECTION,
//    TEAM_SUPPORT_WO;

//    public static StringEnum byString(int ordinal) {
//        switch(ordinal) {
//            case 0:
//                return TOPASSIGNED;
//            case 1:
//                return INVALID_TOKEN;
////            case 2:
////                return COLLECTION;
////            case 3:
////                return TEAM_SUPPORT_WO;
//        }
//        return null;
//    }

    public static String getString(int ordinal) {
        switch(ordinal) {
            case 0:
                return "topassigned";
            case 1:
                return "Invalid Token";	//return "TEAM__SUPPORT";
//            case 2:
//                return "ROLE__COLLECTION";
//            case 3:
//                return "ROLE__COLLECTION";
        }
        return null;
    }


}
