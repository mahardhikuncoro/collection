package base.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class FormData {

    public static final String TABLE = "DYN_FORM";
    public static final String _ID = "_ID";
    public static final String VERSION = "VERSION";
    public static final String OLD_VERSION = "OLD_VERSION";
    public static final String FORM_NAME = "FORM_NAME";
    public static final String KEY = "KEY";
    public static final String VALUE = "VALUE";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE + " ("
                    + _ID + " INTEGER PRIMARY KEY, "
                    + VERSION + " TEXT, "
                    + OLD_VERSION + " TEXT, "
                    + FORM_NAME + " TEXT, "
                    + KEY + " TEXT, "
                    + VALUE + " TEXT); ";


    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE;

    private final Context context;
    private SQLiteHelperBexi databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public FormData(Context context) {
        this.context = context;
    }


    private FormData open() throws SQLException {
        SQLiteConfigKu config = new SQLiteConfigKu(context);
        databaseHelper = new SQLiteHelperBexi(context, config.getDatabaseName(), config.getDatabaseVersion());
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }


    public void save(String version, String formName, String key, String value) {

        open();
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(VERSION, version);
        v.put(OLD_VERSION, version);
        v.put(FORM_NAME, formName);
        v.put(KEY, key.toUpperCase().replace("_", " "));
        v.put(VALUE, value);

        sqLiteDatabase.insert(TABLE, null, v);

        close();
    }

    public void update(String version, String key, String value, String msg) {
        String q = "UPDATE "+TABLE+" SET "+VALUE+"='"+value+"', " +
                ""+VERSION+"='"+version+"', " +
                "WHERE 1=1 ";
        if (key != null)
            q += " AND "+KEY+"='"+key.toUpperCase().replace("_"," ")+"' ";

        open();
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL(q);
        close();
    }

    public void updateVer(String key) {
        String q = "UPDATE "+TABLE+" SET "+OLD_VERSION+"='"+checkFormVer(key)+"'"+
                "WHERE 1=1 ";
        if (key != null)
            q += " AND "+FORM_NAME+"='"+key+"' ";

        open();
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL(q);
        close();
    }

    public Boolean checkUpdate(String key){
        String q = "SELECT * FROM " + TABLE + " WHERE 1=1";
        if (key != null)
            q += " AND "+FORM_NAME+"='"+key+"' ";

        open();

        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        String version = "";
        String oldVersion = "";
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    version = c.getString(c.getColumnIndex(VERSION));
                    oldVersion = c.getString(c.getColumnIndex(OLD_VERSION));
                }while (c.moveToNext());
            }
        }
        close();

        return !version.equalsIgnoreCase(oldVersion);
    }


    public String checkVer(String key){
        String q = "SELECT * FROM " + TABLE + " WHERE 1=1";
        if (key != null)
            q += " AND "+KEY+"='"+key.toUpperCase().replace("_", " ")+"' ";

        open();

        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        String version = "";
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    version = c.getString(c.getColumnIndex(VERSION));
                }while (c.moveToNext());
            }
        }
        close();

        return version;
    }public String checkFormVer(String key){
        String q = "SELECT * FROM " + TABLE + " WHERE 1=1";
        if (key != null)
            q += " AND "+FORM_NAME+"='"+key+"' ";

        open();

        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        String version = "";
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    version = c.getString(c.getColumnIndex(VERSION));
                }while (c.moveToNext());
            }
        }
        close();

        return version;
    }

    public String select(String key){
        String q = "SELECT * FROM " + TABLE + " WHERE 1=1";
        if (key != null)
            q += " AND "+KEY+"='"+key.toUpperCase().replace("_", " ")+"' ";

        open();

        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        String version = "";
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    version = c.getString(c.getColumnIndex(VALUE));
                }while (c.moveToNext());
            }
        }
        close();

        return version;
    }


   /* public String selectSection(String formname){
        String q = "SELECT " + KEY + " FROM " + TABLE + " WHERE 1=1";
        open();

        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        String version = "";
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    version = c.getString(c.getColumnIndex(KEY));
                }while (c.moveToNext());
            }
        }
        close();

        return version;
    }
*/

    public void deleteAll(String key) {
        String q = "DELETE FROM "+TABLE+" WHERE 1=1 ";
        if (key != null)
            q += " AND "+KEY+"='"+key.toUpperCase().replace("_", " ")+"' ";

        open();
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL(q);
        close();
    }

    public void deleteAll() {
        open();
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.delete(TABLE,null, null);
        close();
    }


    public int count(String key) {
        String q = "SELECT * FROM " + TABLE + " WHERE 1=1 ";
        if (key != null)
            q += " AND "+KEY+"='"+key.toUpperCase().replace("_"," ")+"' ";

        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);
        int count = c.getCount();
        c.close();
        close();
        return count;
    }

    public int countByNameForm(String key) {
        String q = "SELECT * FROM " + TABLE + " WHERE 1=1 ";
        if (key != null)
            q += " AND "+FORM_NAME+"='"+key.toUpperCase()+"' ";

        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);
        int count = c.getCount();
        c.close();
        close();
        return count;
    }



}
