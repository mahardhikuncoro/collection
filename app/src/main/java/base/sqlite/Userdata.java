package base.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Userdata {
    public static final String TABLE                    ="DATA_USER";
    public static final String _ID                      ="_ID";
    public static final String USERID                   = "USERID";
    public static final String PHOTOPROFILE             = "PHOTOPROFILE";
    public static final String FULLNAME                 ="FULLNAME";
    public static final String GROUP_ID                 ="GROUP_ID";
    public static final String GROUP_NAME               ="GROUP_NAME";
    public static final String BRANCHID                 ="BRANCHID";
    public static final String BRANCHNAME               ="BRANCHNAME";
    public static final String ACCESSTOKEN              ="ACCESSTOKEN";
    public static final String TOKENTYPE                ="TOKENTYPE";
    public static final String EXPIREDIN                ="EXPIREDIN";

    public static final String CREATE_TABLE =
            " CREATE TABLE "+TABLE+" ("
                    +_ID+" INTEGER PRIMARY KEY, "
                    + USERID +" TEXT, "
                    + PHOTOPROFILE +" TEXT, "
                    + FULLNAME +" TEXT, "
                    + GROUP_ID +" TEXT, "
                    + GROUP_NAME +" TEXT, "
                    + BRANCHID +" TEXT, "
                    + BRANCHNAME +" TEXT, "
                    + ACCESSTOKEN +" TEXT, "
                    + TOKENTYPE +" TEXT, "
                    + EXPIREDIN +" TEXT); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE;

    private final Context context;
    private SQLiteHelperBexi sqLiteHelperBexi;
    private SQLiteDatabase sqLiteDatabase;

    public Userdata(Context context) {
        this.context = context;
    }

    private Userdata open() throws SQLException {
        SQLiteConfigKu config = new SQLiteConfigKu(context);
        sqLiteHelperBexi = new SQLiteHelperBexi(context, config.getDatabaseName(), config.getDatabaseVersion());
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public UserdataModel save(UserdataModel model) {
        open();
        sqLiteDatabase = sqLiteHelperBexi.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(USERID, model.getUserid());
        v.put(FULLNAME, model.getFullname());
        v.put(PHOTOPROFILE, model.getPhotoprofile());
        v.put(GROUP_ID, model.getGroupid());
        v.put(GROUP_NAME, model.getGroupname());
        v.put(BRANCHID, model.getBranchid());
        v.put(BRANCHNAME, model.getBranchname());
        v.put(ACCESSTOKEN, model.getAccesstoken());
        v.put(TOKENTYPE, model.getTokentype());
        v.put(EXPIREDIN, model.getExpiredin());


        if (model.getIdSqlite() == null) {
            Long id = sqLiteDatabase.insert(TABLE, null, v);
            model.setIdSqlite(id.intValue());
        } else {
            sqLiteDatabase.update(TABLE, v, _ID + "=?", new String[]{String.valueOf(model.getIdSqlite())});
        }

        close();
        return model;
    }

    public UserdataModel save(String userid, String nama , String propic , String groupid , String groupname , String branchid, String branchname, String token, String tokentype, String expired) {
        UserdataModel model = new UserdataModel();
        model.setUserid(userid);
        model.setFullname(nama);
        model.setPhotoprofile(propic);
        model.setGroupid(groupid);
        model.setGroupname(groupname);
        model.setBranchid(branchid);
        model.setBranchname(branchname);
        model.setAccesstoken(token);
        model.setTokentype(tokentype);
        model.setExpiredin(expired);

        return save(model);
    }

    public void delete(Integer id) {
        open();
        sqLiteDatabase = sqLiteHelperBexi.getWritableDatabase();
        sqLiteDatabase.delete(TABLE, _ID + "=?", new String[]{String.valueOf(id)});
        close();
    }

    public void deleteAll() {
        open();
        sqLiteDatabase = sqLiteHelperBexi.getWritableDatabase();
        sqLiteDatabase.delete(TABLE,null, null);
        close();
    }

    public UserdataModel select() {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        UserdataModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new UserdataModel();
                    model.setUserid(c.getString(c.getColumnIndex(USERID)));
                    model.setPhotoprofile(c.getString((c.getColumnIndex(PHOTOPROFILE))));
                    model.setFullname(c.getString((c.getColumnIndex(FULLNAME))));
                    model.setGroupid(c.getString((c.getColumnIndex(GROUP_ID))));
                    model.setGroupname(c.getString((c.getColumnIndex(GROUP_NAME))));
                    model.setBranchid(c.getString((c.getColumnIndex(BRANCHID))));
                    model.setBranchname(c.getString((c.getColumnIndex(BRANCHNAME))));
                    model.setAccesstoken(c.getString((c.getColumnIndex(ACCESSTOKEN))));
                    model.setTokentype(c.getString((c.getColumnIndex(TOKENTYPE))));
                    model.setExpiredin(c.getString((c.getColumnIndex(EXPIREDIN))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public int count() {
        String q = "SELECT * FROM "+TABLE+" ";
        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);
        int count = c.getCount();
        c.close();
        close();
        return count;
    }

    public void updatelinkProfile (String linkprofile, String userid){
        String sql = "UPDATE "+ TABLE+ " SET "+ PHOTOPROFILE + " = "+"'"+ linkprofile + "'"+  " WHERE " + USERID +" = " + "'"+ userid+"'";
        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        sqLiteDatabase.execSQL(sql);
        close();
    }
}

