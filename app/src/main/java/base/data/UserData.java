package base.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import base.sqlite.ActiveData;
import base.sqlite.ActiveKey;
import base.sqlite.SQLiteConfig;
import base.sqlite.SQLiteHelper;

public class UserData {

    public static final String TABLE                ="SMMF_V3_USER";
    public static final String _ID                  ="_ID";
    public static final String MOBILE_ID            ="MOBILE_ID";
    public static final String USER_ID              ="USER_ID";
    public static final String USERNAME             ="USERNAME";
    public static final String NAME                 ="NAME";
    public static final String ROLE_NAME            ="ROLE_NAME";
    public static final String ROLE_GROUP_ID        ="ROLE_GROUP_ID";
    public static final String BRANCH_ID            ="BRANCH_ID";
    public static final String BRANCH_TYPE          ="BRANCH_TYPE";
    public static final String BRANCH_NAME          ="BRANCH_NAME";
    public static final String PUSH_MESSAGING_ID    ="PUSH_MESSAGING_ID";
    public static final String RECAP_ID             ="RECAP_ID";
    public static final String APP_NAME             ="APP_NAME";
    public static final String IMEI                 ="IMEI";
    public static final String LOGIN_TIME           ="LOGIN_TIME";
    public static final String V                    ="V";
    private static final String PIN                  ="PIN";
    private static final String NOTIF_TOTAL          ="NOTIF_TOTAL";
    private static final String NOTIF_MESSAGE        ="NOTIF_MESSAGE";

    public static final String CREATE_TABLE =
            " CREATE TABLE "+TABLE+" ("
                    +_ID+" INTEGER PRIMARY KEY, "
                    +MOBILE_ID+" INTEGER, "
                    +USER_ID+" INTEGER, "
                    +USERNAME+" TEXT, "
                    +NAME+" TEXT, "
                    +ROLE_NAME+" TEXT, "
                    +ROLE_GROUP_ID+" INTEGER, "
                    +BRANCH_ID+" INTEGER, "
                    +LOGIN_TIME+" INTEGER, "
                    +RECAP_ID+" INTEGER, "
                    +V+" INTEGER, "
                    +BRANCH_TYPE+" TEXT, "
                    +BRANCH_NAME+" TEXT, "
                    +IMEI+" TEXT, "
                    +APP_NAME+" TEXT, "
                    +PUSH_MESSAGING_ID+" TEXT, "
                    +PIN+" TEXT, "
                    +NOTIF_TOTAL+" INTEGER, "
                    +NOTIF_MESSAGE+" TEXT); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE;

    public static final String ALTER_TABLE =
            " ALTER TABLE "+TABLE+" ADD COLUMN "+RECAP_ID+" INTEGER; ";

    public static final String ALTER_TABLE1=
            " ALTER TABLE "+TABLE+" ADD COLUMN "+PIN+" TEXT; ";
    public static final String ALTER_TABLE2 =
            " ALTER TABLE "+TABLE+" ADD COLUMN "+NOTIF_TOTAL+" INTEGER; ";
    public static final String ALTER_TABLE3 =
            " ALTER TABLE "+TABLE+" ADD COLUMN "+NOTIF_MESSAGE+" TEXT; ";


    public static final String ALTER_TABLE_V =
            " ALTER TABLE "+TABLE+" ADD COLUMN "+V+" INTEGER; ";

    private final Context context;
    private SQLiteHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    private ActiveData activeData;

    public UserData(Context context) {
        this.context = context;
    }

    private UserData open() throws SQLException {
        SQLiteConfig config = new SQLiteConfig(context);
        databaseHelper = new SQLiteHelper(context, config.getSqliteName(), config.getSqliteVer());
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public UserMetaData save(UserMetaData model) {
        open();
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(MOBILE_ID, model.getMobileId());
        v.put(USER_ID, model.getUserId());
        v.put(USERNAME, model.getUsername());
        v.put(NAME, model.getName());
        v.put(RECAP_ID, model.getRecapId());
        v.put(ROLE_NAME, model.getRoleName());
        v.put(ROLE_GROUP_ID, model.getRoleGroupId());
        v.put(BRANCH_ID, model.getBranchId());
        v.put(BRANCH_TYPE, model.getBranchType());
        v.put(BRANCH_NAME, model.getBranchName());
        v.put(PUSH_MESSAGING_ID, model.getPushMessagingId());
        v.put(APP_NAME, model.getAppName());
        v.put(IMEI, model.getImei());
        v.put(LOGIN_TIME, model.getLoginTime());
        v.put(V, model.getV());
        v.put(PIN, model.getPin());
        v.put(NOTIF_TOTAL, model.getNotifTotal());
        v.put(NOTIF_MESSAGE, model.getNotifMessage());

        if (model.getId() == null) {
            Long id = sqLiteDatabase.insert(TABLE, null, v);
            model.setId(id.intValue());
        } else {
            sqLiteDatabase.update(TABLE, v, _ID + "=?", new String[]{String.valueOf(model.getId())});
        }

        close();
        return model;
    }

    public UserMetaData save(Long mobileId, Long branchId, String branchType, String branchName,
                             Long userId, String username, String name, String roleName,
                             Long roleGroupId, String appName, Long LoginTime, String imei, Long recapId, Integer v) {
        UserMetaData model = selectBy(username);
        if (model == null) {
            model = new UserMetaData();
        }
        model.setMobileId(mobileId);
        model.setBranchId(branchId);
        model.setBranchType(branchType);
        model.setBranchName(branchName);
        model.setUserId(userId);
        model.setUsername(username);
        model.setName(name);
        model.setRoleName(roleName.toUpperCase());
        model.setRoleGroupId(roleGroupId);
        model.setAppName(appName);
        model.setImei(imei);
        model.setRecapId(recapId);
        model.setLoginTime(LoginTime);
        model.setV(v);
        return save(model);
    }

    public void delete(Integer id) {
        open();
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.delete(TABLE, _ID + "=?", new String[]{String.valueOf(id)});
        close();
    }

    public UserMetaData select(Integer id) {
        if (id == null)
            return null;
        String q = "SELECT * FROM "+TABLE+" WHERE "+_ID+"="+id;

        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        UserMetaData model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new UserMetaData();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setMobileId(c.getLong((c.getColumnIndex(MOBILE_ID))));
                    model.setUserId(c.getLong((c.getColumnIndex(USER_ID))));
                    model.setUsername((c.getString(c.getColumnIndex(USERNAME))));
                    model.setName(c.getString(c.getColumnIndex(NAME)));
                    model.setRoleName(c.getString(c.getColumnIndex(ROLE_NAME)));
                    model.setRoleGroupId(c.getLong(c.getColumnIndex(ROLE_GROUP_ID)));
                    model.setBranchId(c.getLong((c.getColumnIndex(BRANCH_ID))));
                    model.setBranchType(c.getString(c.getColumnIndex(BRANCH_TYPE)));
                    model.setBranchName(c.getString(c.getColumnIndex(BRANCH_NAME)));
                    model.setPushMessagingId(c.getString(c.getColumnIndex(PUSH_MESSAGING_ID)));
                    model.setAppName(c.getString(c.getColumnIndex(APP_NAME)));
                    model.setImei(c.getString(c.getColumnIndex(IMEI)));
                    model.setRecapId(c.getLong(c.getColumnIndex(RECAP_ID)));
                    model.setLoginTime(c.getLong((c.getColumnIndex(LOGIN_TIME))));
                    model.setV(c.getInt((c.getColumnIndex(V))));
                    model.setPin(c.getString(c.getColumnIndex(PIN)));
                    model.setNotifTotal(c.getInt(c.getColumnIndex(NOTIF_TOTAL)));
                    model.setNotifMessage(c.getString(c.getColumnIndex(NOTIF_MESSAGE)));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public UserMetaData select(String app) {
        String q = "SELECT * FROM "+TABLE+" WHERE "+APP_NAME+"='"+app+"' ";

        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        UserMetaData model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new UserMetaData();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setMobileId(c.getLong((c.getColumnIndex(MOBILE_ID))));
                    model.setUserId(c.getLong((c.getColumnIndex(USER_ID))));
                    model.setUsername((c.getString(c.getColumnIndex(USERNAME))));
                    model.setName(c.getString(c.getColumnIndex(NAME)));
                    model.setRoleName(c.getString(c.getColumnIndex(ROLE_NAME)));
                    model.setRoleGroupId(c.getLong(c.getColumnIndex(ROLE_GROUP_ID)));
                    model.setBranchId(c.getLong((c.getColumnIndex(BRANCH_ID))));
                    model.setBranchType(c.getString(c.getColumnIndex(BRANCH_TYPE)));
                    model.setBranchName(c.getString(c.getColumnIndex(BRANCH_NAME)));
                    model.setPushMessagingId(c.getString(c.getColumnIndex(PUSH_MESSAGING_ID)));
                    model.setAppName(c.getString(c.getColumnIndex(APP_NAME)));
                    model.setImei(c.getString(c.getColumnIndex(IMEI)));
                    model.setRecapId(c.getLong(c.getColumnIndex(RECAP_ID)));
                    model.setLoginTime(c.getLong((c.getColumnIndex(LOGIN_TIME))));
                    model.setV(c.getInt((c.getColumnIndex(V))));
                    model.setPin(c.getString(c.getColumnIndex(PIN)));
                    model.setNotifTotal(c.getInt(c.getColumnIndex(NOTIF_TOTAL)));
                    model.setNotifMessage(c.getString(c.getColumnIndex(NOTIF_MESSAGE)));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }
    public UserMetaData select(Long userId) {
        String q = "SELECT * FROM "+TABLE+" WHERE "+USER_ID+"='"+userId+"' ";

        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        UserMetaData model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new UserMetaData();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setMobileId(c.getLong((c.getColumnIndex(MOBILE_ID))));
                    model.setUserId(c.getLong((c.getColumnIndex(USER_ID))));
                    model.setUsername((c.getString(c.getColumnIndex(USERNAME))));
                    model.setName(c.getString(c.getColumnIndex(NAME)));
                    model.setRoleName(c.getString(c.getColumnIndex(ROLE_NAME)));
                    model.setRoleGroupId(c.getLong(c.getColumnIndex(ROLE_GROUP_ID)));
                    model.setBranchId(c.getLong((c.getColumnIndex(BRANCH_ID))));
                    model.setBranchType(c.getString(c.getColumnIndex(BRANCH_TYPE)));
                    model.setBranchName(c.getString(c.getColumnIndex(BRANCH_NAME)));
                    model.setPushMessagingId(c.getString(c.getColumnIndex(PUSH_MESSAGING_ID)));
                    model.setAppName(c.getString(c.getColumnIndex(APP_NAME)));
                    model.setImei(c.getString(c.getColumnIndex(IMEI)));
                    model.setRecapId(c.getLong(c.getColumnIndex(RECAP_ID)));
                    model.setLoginTime(c.getLong((c.getColumnIndex(LOGIN_TIME))));
                    model.setV(c.getInt((c.getColumnIndex(V))));
                    model.setPin(c.getString(c.getColumnIndex(PIN)));
                    model.setNotifTotal(c.getInt(c.getColumnIndex(NOTIF_TOTAL)));
                    model.setNotifMessage(c.getString(c.getColumnIndex(NOTIF_MESSAGE)));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public UserMetaData selectBy(String username) {
        String q = "SELECT * FROM "+TABLE+" WHERE "+USERNAME+"='"+username+"' ";

        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        UserMetaData model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new UserMetaData();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setMobileId(c.getLong((c.getColumnIndex(MOBILE_ID))));
                    model.setUserId(c.getLong((c.getColumnIndex(USER_ID))));
                    model.setUsername((c.getString(c.getColumnIndex(USERNAME))));
                    model.setName(c.getString(c.getColumnIndex(NAME)));
                    model.setRoleName(c.getString(c.getColumnIndex(ROLE_NAME)));
                    model.setRoleGroupId(c.getLong(c.getColumnIndex(ROLE_GROUP_ID)));
                    model.setBranchId(c.getLong((c.getColumnIndex(BRANCH_ID))));
                    model.setBranchType(c.getString(c.getColumnIndex(BRANCH_TYPE)));
                    model.setBranchName(c.getString(c.getColumnIndex(BRANCH_NAME)));
                    model.setPushMessagingId(c.getString(c.getColumnIndex(PUSH_MESSAGING_ID)));
                    model.setAppName(c.getString(c.getColumnIndex(APP_NAME)));
                    model.setImei(c.getString(c.getColumnIndex(IMEI)));
                    model.setRecapId(c.getLong(c.getColumnIndex(RECAP_ID)));
                    model.setLoginTime(c.getLong((c.getColumnIndex(LOGIN_TIME))));
                    model.setV(c.getInt((c.getColumnIndex(V))));
                    model.setPin(c.getString(c.getColumnIndex(PIN)));
                    model.setNotifTotal(c.getInt(c.getColumnIndex(NOTIF_TOTAL)));
                    model.setNotifMessage(c.getString(c.getColumnIndex(NOTIF_MESSAGE)));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public List<UserMetaData> selectList() {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1";
        List<UserMetaData> list = new ArrayList<>();

        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        UserMetaData model;
        if (c.moveToFirst()) {
            do {
                model = new UserMetaData();
                model.setId(c.getInt(c.getColumnIndex(_ID)));
                model.setMobileId(c.getLong((c.getColumnIndex(MOBILE_ID))));
                model.setUserId(c.getLong((c.getColumnIndex(USER_ID))));
                model.setUsername((c.getString(c.getColumnIndex(USERNAME))));
                model.setName(c.getString(c.getColumnIndex(NAME)));
                model.setRoleName(c.getString(c.getColumnIndex(ROLE_NAME)));
                model.setRoleGroupId(c.getLong(c.getColumnIndex(ROLE_GROUP_ID)));
                model.setBranchId(c.getLong((c.getColumnIndex(BRANCH_ID))));
                model.setBranchType(c.getString(c.getColumnIndex(BRANCH_TYPE)));
                model.setBranchName(c.getString(c.getColumnIndex(BRANCH_NAME)));
                model.setPushMessagingId(c.getString(c.getColumnIndex(PUSH_MESSAGING_ID)));
                model.setAppName(c.getString(c.getColumnIndex(APP_NAME)));
                model.setImei(c.getString(c.getColumnIndex(IMEI)));
                model.setRecapId(c.getLong(c.getColumnIndex(RECAP_ID)));
                model.setLoginTime(c.getLong((c.getColumnIndex(LOGIN_TIME))));
                model.setV(c.getInt((c.getColumnIndex(V))));
                model.setPin(c.getString(c.getColumnIndex(PIN)));
                model.setNotifTotal(c.getInt(c.getColumnIndex(NOTIF_TOTAL)));
                model.setNotifMessage(c.getString(c.getColumnIndex(NOTIF_MESSAGE)));
                list.add(model);
            } while (c.moveToNext());
        }

        close();
        return list;
    }
    public List<UserMetaData> selectList(String app) {
        String q = "SELECT * FROM "+TABLE+" WHERE "+APP_NAME+"='"+app+"' ";
        List<UserMetaData> list = new ArrayList<>();

        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        UserMetaData model;
        if (c.moveToFirst()) {
            do {
                model = new UserMetaData();
                model.setId(c.getInt(c.getColumnIndex(_ID)));
                model.setMobileId(c.getLong((c.getColumnIndex(MOBILE_ID))));
                model.setUserId(c.getLong((c.getColumnIndex(USER_ID))));
                model.setUsername((c.getString(c.getColumnIndex(USERNAME))));
                model.setName(c.getString(c.getColumnIndex(NAME)));
                model.setRoleName(c.getString(c.getColumnIndex(ROLE_NAME)));
                model.setRoleGroupId(c.getLong(c.getColumnIndex(ROLE_GROUP_ID)));
                model.setBranchId(c.getLong((c.getColumnIndex(BRANCH_ID))));
                model.setBranchType(c.getString(c.getColumnIndex(BRANCH_TYPE)));
                model.setBranchName(c.getString(c.getColumnIndex(BRANCH_NAME)));
                model.setPushMessagingId(c.getString(c.getColumnIndex(PUSH_MESSAGING_ID)));
                model.setAppName(c.getString(c.getColumnIndex(APP_NAME)));
                model.setImei(c.getString(c.getColumnIndex(IMEI)));
                model.setRecapId(c.getLong(c.getColumnIndex(RECAP_ID)));
                model.setLoginTime(c.getLong((c.getColumnIndex(LOGIN_TIME))));
                model.setV(c.getInt((c.getColumnIndex(V))));
                model.setPin(c.getString(c.getColumnIndex(PIN)));
                model.setNotifTotal(c.getInt(c.getColumnIndex(NOTIF_TOTAL)));
                model.setNotifMessage(c.getString(c.getColumnIndex(NOTIF_MESSAGE)));
                list.add(model);
            } while (c.moveToNext());
        }

        close();
        return list;
    }

    public int count() {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1";
        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);
        int count = c.getCount();
        c.close();
        close();
        return count;
    }

    public int count(String app) {
        String q = "SELECT * FROM "+TABLE+" WHERE "+APP_NAME+"='"+app+"' ";
        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);
        int count = c.getCount();
        c.close();
        close();
        return count;
    }

    public void setActiveUser(UserMetaData model) {
        if (activeData == null) activeData = new ActiveData(context);
        activeData.setInteger(ActiveKey.ACCOUNT_ID, model.getId());
    }

    public UserMetaData getActiveUser() {
        if (activeData == null) activeData = new ActiveData(context);
        return select(activeData.getInteger(ActiveKey.ACCOUNT_ID));
    }

    public void deleteActiveUser() {
        if (activeData == null) activeData = new ActiveData(context);
        activeData.setInteger(ActiveKey.ACCOUNT_ID, 0);
    }

    public List<UserMetaData> getListOtherUser(String activeUsername) {
        List<UserMetaData> list1 = selectList();
        List<UserMetaData> list2 = new ArrayList<>();
        for (UserMetaData model : list1) {
            if (! model.getUsername().equals(activeUsername))
                list2.add(model);
        }
        return list2;
    }

    public void unregister() {
        UserMetaData model = getActiveUser();
        if (model != null) {

            delete(model.getId());
            deleteActiveUser();
        }
    }

    public void deleteAll() {
        String q = "DELETE FROM "+TABLE+" WHERE 1=1 ";

        open();
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL(q);
        close();
    }

    public void deleteAll(String username) {
        String q = "DELETE FROM "+TABLE+" WHERE 1=1 ";
        if (username != null)
            q += " AND "+USERNAME+"='"+username+"' ";

        open();
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL(q);
        close();
    }


    public void changePin(String pin) {
        UserMetaData model = getActiveUser();
        if (pin != null)
            model.setPin(pin);
        save(model);
    }

    public boolean askPin(String pin) {
        UserMetaData model = getActiveUser();
        if (pin != null) {
            if (pin.equals(model.getPin()))
                return true;
        }
        return false;
    }

}
