package base.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class ActiveData {

    private static final String TABLE           ="SMMF_V3_ACTIVE";
    private static final String _ID             ="USERID";
    private static final String ACTIVE_KEY      ="ACTIVE_KEY";
    private static final String ACTIVE_VALUE    ="ACTIVE_VALUE";

    public static final String CREATE_TABLE =
            " CREATE TABLE "+TABLE+" ("
                    +_ID+" INTEGER PRIMARY KEY, "
                    +ACTIVE_KEY+" TEXT, "
                    +ACTIVE_VALUE+" TEXT); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE;

    private final Context context;
    private SQLiteHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public ActiveData(Context context) {
        this.context = context;
    }

    private ActiveData open() throws SQLException {
        SQLiteConfig config = new SQLiteConfig(context);
        databaseHelper = new SQLiteHelper(context, config.getSqliteName(), config.getSqliteVer());
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public Active save(Active model) {
        open();
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(ACTIVE_KEY, model.getActiveKey());
        v.put(ACTIVE_VALUE, model.getActiveValue());

        if (model.getId() == null) {
            Long id = sqLiteDatabase.insert(TABLE, null, v);
            model.setId(id.intValue());
        } else {
            sqLiteDatabase.update(TABLE, v, _ID + "=?", new String[]{String.valueOf(model.getId())});
        }
        close();
        return model;
    }

    public void delete(Integer id) {
        open();
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.delete(TABLE, _ID + "=?", new String[]{String.valueOf(id)});
        close();
    }

    public Active select(String key) {
        if (key == null)
            return null;
        String q = "SELECT * FROM "+TABLE+" WHERE "+ACTIVE_KEY+"='"+key+"' ";

        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);
        Active model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new Active();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setActiveKey(c.getString(c.getColumnIndex(ACTIVE_KEY)));
                    model.setActiveValue(c.getString(c.getColumnIndex(ACTIVE_VALUE)));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public List<Active> selectList(String key) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (key != null)
            q += " AND "+ACTIVE_KEY+"='"+key+"' ";

        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);
        List<Active> list = new ArrayList<>();
        Active model;
        if (c.moveToFirst()) {
            do {
                model = new Active();
                model.setId(c.getInt(c.getColumnIndex(_ID)));
                model.setActiveKey(c.getString(c.getColumnIndex(ACTIVE_KEY)));
                model.setActiveValue(c.getString(c.getColumnIndex(ACTIVE_VALUE)));
                list.add(model);
            } while (c.moveToNext());
        }
        close();
        return list;
    }

    public int count(String key) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (key != null)
            q += " AND "+ACTIVE_KEY+"='"+key+"' ";

        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);
        int count = c.getCount();
        c.close();
        close();
        return count;
    }

    public String getString(String key) {
        String value = null;
        Active model = select(key);
        if (model != null)
            value = model.getActiveValue();
        return value;
    }

    public Integer getInteger(String key) {
        Integer value = null;
        Active model = select(key);
        if (model != null)
            value = Integer.parseInt(model.getActiveValue());
        return value;
    }

    public void setString(String key, String value) {
        Active model  = select(key);
        if (model == null) {
            model = new Active();
            model.setActiveKey(key);
        }
        model.setActiveValue(value);
        save(model);
    }

    public void setInteger(String key, Integer value) {
        Active model  = select(key);
        if (model == null) {
            model = new Active();
            model.setActiveKey(key);
        }
        model.setActiveValue(value + "");
        save(model);
    }
}