package base.utils.enm;

/**
 * Created by ADMINSMMF on 2/8/2018.
 */

public enum TaskStatus {
    UNPICK,
    ONGOING,
    DONE;

    public static TaskStatus byOrdinal(int ordinal) {
        switch(ordinal) {
            case 0:
                return UNPICK;
            case 1:
                return ONGOING;
            case 2:
                return DONE;

        }
        return null;
    }

    public static TaskStatus byName(String name) {
        if (UNPICK.name().equals(name))
            return UNPICK;
        if (ONGOING.name().equals(name))
            return ONGOING;
        if (DONE.name().equals(name))
            return DONE;

        return null;
    }



}
