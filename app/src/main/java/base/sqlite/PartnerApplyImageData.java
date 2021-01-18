package base.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class PartnerApplyImageData {

    public static final String TABLE                      ="USER_IMAGE";
    public static final String _ID                        ="_ID";
    public static final String PATH                       ="PATH";
    public static final String HEIGHT                     ="HEIGHT";
    public static final String WIDTH                      ="WIDTH";
    public static final String SIZE                       ="SIZE";
    public static final String CATEGORY                   ="CATEGORY";
    public static final String APPLY_ID                   ="APPLY_ID";
    public static final String UPLOAD_STATUS              ="UPLOAD_STATUS";
    public static final String BACKEND_ID                 ="BACKEND_ID";
    public static final String STATUS                     = "STATUS";
    public static final String LATITUDE                   = "LATITUDE";
    public static final String LONGITUDE                  = "LONGITUDE";


    public static String CREATE_TABLE =
            " CREATE TABLE "+TABLE+" ("
                    +_ID+" INTEGER PRIMARY KEY, "
                    +PATH+" TEXT, "
                    +HEIGHT+" INTEGER, "
                    +WIDTH+" INTEGER, "
                    +SIZE+" INTEGER, "
                    +CATEGORY+" TEXT, "
                    +APPLY_ID+" INTEGER, "
                    +BACKEND_ID+" INTEGER, "
                    +STATUS+" INTEGER, "
                    +LATITUDE+" TEXT, "
                    +LONGITUDE+" TEXT, "
                    +UPLOAD_STATUS+" INTEGER); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE;


    public static final String ALTER_TABLE1 =
            "ALTER TABLE "+ TABLE + " ADD " +LATITUDE + " TEXT";

    public static final String ALTER_TABLE2 =
            "ALTER TABLE "+ TABLE + " ADD " +LONGITUDE + " TEXT";

    public static final String ALTER_TABLE3 =
            "ALTER TABLE "+ TABLE + " ADD " +STATUS + " TEXT";

    public static final String ALTER_TABLE4 =
            "ALTER TABLE "+ TABLE + " ADD " +BACKEND_ID + " TEXT";


    private final Context context;
    private SQLiteHelperBexi databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    private ActiveData activeData;

    public PartnerApplyImageData(Context context) {
        this.context = context;
    }

    private PartnerApplyImageData open() throws SQLException {
        SQLiteConfigKu config = new SQLiteConfigKu(context);
        databaseHelper = new SQLiteHelperBexi(context, config.getDatabaseName(), config.getDatabaseVersion());
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public SurveyImage save(SurveyImage model) {
        open();
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(PATH, model.getPath());
        v.put(HEIGHT, model.getHeight());
        v.put(WIDTH, model.getWidth());
        v.put(SIZE, model.getSize());
        v.put(CATEGORY, model.getCategory());
        v.put(APPLY_ID, model.getApplyId());
        v.put(UPLOAD_STATUS, model.getUploadStatus());
        v.put(BACKEND_ID,model.getBackendId());
        v.put(STATUS,model.getStatus());
        v.put(LATITUDE,model.getLatitude());
        v.put(LONGITUDE,model.getLongitude());

        if (model.getId() == null) {
            Long id = sqLiteDatabase.insert(TABLE, null, v);
            model.setId(id.intValue());
        } else {
            sqLiteDatabase.update(TABLE, v, _ID + "=?", new String[]{String.valueOf(model.getId())});
        }
        close();
        return model;
    }

    public SurveyImage save(String path, Integer height, Integer width, Integer size, String category, Integer backendId,
                           Integer status, String latitude, String longitude) {
//        if (partnerApplyData == null) partnerApplyData = new PartnerApplyData(context);
        SurveyImage model = getActiveApplyImage();
        if (model == null)
            model = new SurveyImage();
        model.setPath(path);
        model.setHeight(height);
        model.setWidth(width);
        model.setSize(size);
        model.setCategory(category);
        Log.e("ini adalah categorynya",category);
        model.setApplyId(1);
        model.setUploadStatus(1);
        model.setBackendId(backendId.longValue());
        model.setStatus(status);
        model.setLatitude(latitude);
        model.setLongitude(longitude);
        return save(model);
    }

    public SurveyImage getActiveApplyImage() {
        if (activeData == null) activeData = new ActiveData(context);
        String path = activeData.getString(ActiveKey.PATH);
        if (path != null)
            return select(path);
        return null;
    }

    public SurveyImage select(String path) {
        if (path == null)
            return null;
        String q = "SELECT * FROM "+TABLE+" WHERE "+PATH+"='"+path+"' ";

        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);
        SurveyImage model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new SurveyImage();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setPath(c.getString(c.getColumnIndex(PATH)));
                    model.setHeight(c.getInt(c.getColumnIndex(HEIGHT)));
                    model.setWidth(c.getInt(c.getColumnIndex(WIDTH)));
                    model.setSize(c.getInt(c.getColumnIndex(SIZE)));
                    model.setCategory(c.getString(c.getColumnIndex(CATEGORY)));
                    model.setApplyId(c.getInt(c.getColumnIndex(APPLY_ID)));
                    model.setUploadStatus(c.getInt(c.getColumnIndex(UPLOAD_STATUS)));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public SurveyImage select(Integer id) {
        if (id == null)
            return null;
        String q = "SELECT * FROM "+TABLE+" WHERE "+_ID+"="+id;

        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);
        SurveyImage model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new SurveyImage();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setPath(c.getString(c.getColumnIndex(PATH)));
                    model.setHeight(c.getInt(c.getColumnIndex(HEIGHT)));
                    model.setWidth(c.getInt(c.getColumnIndex(WIDTH)));
                    model.setSize(c.getInt(c.getColumnIndex(SIZE)));
                    model.setCategory(c.getString(c.getColumnIndex(CATEGORY)));
                    model.setApplyId(c.getInt(c.getColumnIndex(APPLY_ID)));
                    model.setUploadStatus(c.getInt(c.getColumnIndex(UPLOAD_STATUS)));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }
}