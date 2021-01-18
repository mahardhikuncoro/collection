package base.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;

import base.network.Slider;

/**
 * Created by benyamin on 5/7/18.
 */

public class SliderSQL {
    public static final String TABLE                ="SLIDER";
    public static final String _ID                  ="ID";
    public static final String BACKEND_ID           ="BACKEND_ID";
    public static final String NAME                 ="NAME";
    public static final String IMAGE                ="IMAGE";
    public static final String LINK                 ="LINK";
    public static final String PUBLISH              ="PUBLISH";
    public static final String PACKAGE              ="PACKAGE";

    public static final String CREATE_TABLE =
            " CREATE TABLE "+TABLE+" ("
                    +_ID+" INTEGER PRIMARY KEY, "
                    +BACKEND_ID+" INTEGER, "
                    +NAME+" TEXT, "
                    +IMAGE+" TEXT, "
                    +LINK+" TEXT, "
                    +PUBLISH+" TEXT, "
                    +PACKAGE+" TEXT); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE;

    public static final String ALTER_TABLE_SLIDER =
            "ALTER TABLE "+TABLE+" ADD COLUMN "+PACKAGE+" TEXT";

    private final Context context;
    private SQLiteHelperBexi sqLiteHelperBexi;
    private SQLiteDatabase sqLiteDatabase;

    public SliderSQL(Context context) {
        this.context = context;
    }

    private SliderSQL open() throws SQLException {
        SQLiteConfigKu config = new SQLiteConfigKu(context);
        sqLiteHelperBexi = new SQLiteHelperBexi(context, config.getDatabaseName(), config.getDatabaseVersion());
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }


    public Slider save(Long id, String name, String image, String link, String publish, String package_name) {
        Slider model = new Slider();
        model.setId(id);
        model.setName(name);
        model.setImage(image);
        model.setLink(link);
        model.setPublish(publish);
        model.setPackage_name(package_name);
        return save(model);
    }

    public Slider save(Slider model) {
        open();
        sqLiteDatabase = sqLiteHelperBexi.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(BACKEND_ID, model.getId());
        v.put(NAME, model.getName());
        v.put(IMAGE, model.getImage());
        v.put(LINK, model.getLink());
        v.put(PUBLISH, model.getPublish());
        v.put(PACKAGE, model.getPackage_name());

        if (model.getIdSqlite() == null) {
            Long id = sqLiteDatabase.insert(TABLE, null, v);
            model.setIdSqlite(id.intValue());
        } else {
            sqLiteDatabase.update(TABLE, v, _ID + "=?", new String[]{String.valueOf(model.getId())});
        }
        close();

        return model;
    }

    public HashMap<String, String> selecthashmap() {
        String q = "SELECT * FROM "+TABLE+" WHERE "+PUBLISH+"='Y' ORDER BY "+BACKEND_ID+" ASC";
        HashMap<String, String> list = new  HashMap<String, String>();

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    list.put(c.getString(c.getColumnIndex(NAME)), c.getString(c.getColumnIndex(IMAGE)));
                } while (c.moveToNext());
            }
        }

        close();
        return list;
    }

    public Slider select(Integer id) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (id != null)
            q += " AND "+_ID+"="+id;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        Slider model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new Slider();
                    model.setId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setName(c.getString(c.getColumnIndex(NAME)));
                    model.setImage(c.getString((c.getColumnIndex(IMAGE))));
                    model.setLink(c.getString((c.getColumnIndex(LINK))));
                    model.setPublish(c.getString((c.getColumnIndex(PUBLISH))));
                    model.setPackage_name(c.getString((c.getColumnIndex(PACKAGE))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
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

    public int count() {
        String q = "SELECT * FROM "+TABLE+" WHERE "+PUBLISH+" = 'Y'";
        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);
        int count = c.getCount();
        c.close();
        close();
        return count;
    }


}
