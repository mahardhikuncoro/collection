package base.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import base.sqlite.SQLiteConfig;
import base.sqlite.SQLiteHelper;


public class LocationPoolData {

    public static final String TABLE                      ="LOCATION_POOL";
    public static final String _ID                        ="_ID";
    public static final String SURVEYOR_ID                ="SURVEYOR_ID";
    public static final String DATETIME                   ="DATETIME";
    public static final String LATITUDE                   ="LATITUDE";
    public static final String LONGITUDE                  ="LONGITUDE";
    public static final String FLAG                       ="FLAG";

    public static final String CREATE_TABLE =
            " CREATE TABLE "+TABLE+" ("
                    +_ID+" INTEGER PRIMARY KEY, "
                    +SURVEYOR_ID+" INTEGER, "
                    +LATITUDE+" TEXT, "
                    +LONGITUDE+" TEXT, "
                    +FLAG+" INTEGER); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE;

    private final Context context;
    private SQLiteHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public LocationPoolData(Context context) {
        this.context = context;
    }

    private LocationPoolData open() throws SQLException {
        SQLiteConfig config = new SQLiteConfig(context);
        databaseHelper = new SQLiteHelper(context, config.getSqliteName(), config.getSqliteVer());
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public LocationPool save(LocationPool model) {
        open();
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(SURVEYOR_ID, model.getSurveyorId());
        v.put(LATITUDE, model.getLatitude());
        v.put(LONGITUDE, model.getLongitude());
        v.put(FLAG, model.getFlag());

        if (model.getId() == null) {
            Long id = sqLiteDatabase.insert(TABLE, null, v);
            model.setId(id.intValue());
        } else {
            sqLiteDatabase.update(TABLE, v, _ID + "=?", new String[]{String.valueOf(model.getId())});
        }

        close();
        return model;
    }

    public LocationPool save(Long surveyorId, String latitude, String longitude, Integer flag) {
        LocationPool model = new LocationPool();
        model.setSurveyorId(surveyorId);
        model.setLatitude(latitude);
        model.setLongitude(longitude);
        model.setFlag(flag);
        return save(model);
    }

    public LocationPool save(String heading) {
        LocationPool model = new LocationPool();
        model.setHeading(heading);
        return save(model);
    }

    public void delete(Integer id) {
        open();
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.delete(TABLE, _ID + "=?", new String[]{String.valueOf(id)});
        close();
    }


    public void deleteBy(Long surveyorId) {
        open();
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.delete(TABLE, SURVEYOR_ID + "=?", new String[]{String.valueOf(surveyorId)});
        close();
    }

    public void deleteAll() {
        open();
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE);
        close();
    }

    public LocationPool select(Integer id) {
        if (id == null)
            return null;
        String q = "SELECT * FROM "+TABLE+" WHERE "+_ID+"="+id;

        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        LocationPool model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new LocationPool();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setSurveyorId(c.getLong(c.getColumnIndex(SURVEYOR_ID)));
                    model.setLatitude(c.getString(c.getColumnIndex(LATITUDE)));
                    model.setLongitude(c.getString(c.getColumnIndex(LONGITUDE)));
                    model.setFlag(c.getInt(c.getColumnIndex(FLAG)));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public LocationPool selectFirst() {
        String q = "SELECT * FROM "+TABLE+" ORDER BY "+_ID+" LIMIT 1 ";

        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        LocationPool model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new LocationPool();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setSurveyorId(c.getLong(c.getColumnIndex(SURVEYOR_ID)));
                    model.setLatitude(c.getString(c.getColumnIndex(LATITUDE)));
                    model.setLongitude(c.getString(c.getColumnIndex(LONGITUDE)));
                    model.setFlag(c.getInt(c.getColumnIndex(FLAG)));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public LocationPool selectLast() {
        String q = "SELECT * FROM "+TABLE+" ORDER BY "+_ID+" DESC LIMIT 1 ";

        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        LocationPool model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new LocationPool();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setSurveyorId(c.getLong(c.getColumnIndex(SURVEYOR_ID)));
                    model.setLatitude(c.getString(c.getColumnIndex(LATITUDE)));
                    model.setLongitude(c.getString(c.getColumnIndex(LONGITUDE)));
                    model.setFlag(c.getInt(c.getColumnIndex(FLAG)));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public List<LocationPool> selectList(Long surveyorId) {
        String q = "SELECT * FROM " + TABLE + " WHERE 1=1 ";
        if (surveyorId != null)
            q += " AND "+SURVEYOR_ID+"="+surveyorId;

        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);
        List<LocationPool> list = new ArrayList<>();
        LocationPool model;
        if (c.moveToFirst()) {
            do {
                model = new LocationPool();
                model.setId(c.getInt(c.getColumnIndex(_ID)));
                model.setSurveyorId(c.getLong(c.getColumnIndex(SURVEYOR_ID)));
                model.setLatitude(c.getString(c.getColumnIndex(LATITUDE)));
                model.setLongitude(c.getString(c.getColumnIndex(LONGITUDE)));
                model.setFlag(c.getInt(c.getColumnIndex(FLAG)));
                list.add(model);
            } while (c.moveToNext());
        }

        close();
        return list;
    }

    public int count(Long surveyorId) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (surveyorId != null)
            q += " AND "+SURVEYOR_ID+"="+surveyorId;

        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);
        int count = c.getCount();
        c.close();
        close();
        return count;
    }

    public int count(Integer flag) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (flag != null)
            q += " AND "+FLAG+"="+flag;

        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);
        int count = c.getCount();
        c.close();
        close();
        return count;
    }

}