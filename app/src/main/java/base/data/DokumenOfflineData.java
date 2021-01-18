package base.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import base.sqlite.SQLiteConfigKu;
import base.sqlite.SQLiteHelperBexi;

public class DokumenOfflineData {

    public static final String TABLE                    ="PENGAJUAN_DOKUMEN_OFFLINE";
    public static final String _ID                      ="_ID";
    public static final String ID_TYPE_DOKUMEN          ="ID_TYPE_DOKUMEN";
    public static final String TYPE_DOKUMEN             ="TYPE_DOKUMEN";
    public static final String NAMA_DOKUMEN             ="NAMA_DOKUMEN";
    public static final String DOC_ID                   ="DOC_ID";
    public static final String IMAGE_URL                ="IMAGE_URL";


    public static final String CREATE_TABLE_PENGAJUAN_DOKUMEN =
            " CREATE TABLE "+TABLE+" ("
                    +_ID+" INTEGER PRIMARY KEY, "
                    +ID_TYPE_DOKUMEN+" TEXT, "
                    +TYPE_DOKUMEN+" TEXT, "
                    +NAMA_DOKUMEN+" TEXT, "
                    +DOC_ID+" TEXT, "
                    +IMAGE_URL+" TEXT); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE;

    private final Context context;
    private SQLiteHelperBexi sqLiteHelperBexi;
    private SQLiteDatabase sqLiteDatabase;

    public DokumenOfflineData(Context context) {
        this.context = context;
    }

    private DokumenOfflineData open() throws SQLException {
        SQLiteConfigKu config = new SQLiteConfigKu(context);
        sqLiteHelperBexi = new SQLiteHelperBexi(context, config.getDatabaseName(), config.getDatabaseVersion());
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }


    public DokumenOfflineModel save(Integer id,
                                    String idtypedokumen ,
                                    String typedokumen ,
                                    String namadokumen ,
                                    String docid,
                                    String imageUrl
    ) {
        DokumenOfflineModel model = new DokumenOfflineModel();
        model.setId(id);
        model.setIdtypedokumen(idtypedokumen);
        model.setTypedokumen(typedokumen);
        model.setNamadokumen(namadokumen);
        model.setDocid(docid);
        model.setImageUrl(imageUrl);
        return save(model);
    }

    public DokumenOfflineModel save(DokumenOfflineModel model) {
        open();
        sqLiteDatabase = sqLiteHelperBexi.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(ID_TYPE_DOKUMEN, model.getIdtypedokumen());
        v.put(TYPE_DOKUMEN, model.getTypedokumen());
        v.put(NAMA_DOKUMEN, model.getNamadokumen());
        v.put(DOC_ID, model.getDocid());
        v.put(IMAGE_URL, model.getImageUrl());

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
        sqLiteDatabase = sqLiteHelperBexi.getWritableDatabase();
        sqLiteDatabase.delete(TABLE, _ID + "=?", new String[]{String.valueOf(id)});
        close();
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

    public int countByIdType(String idType) {
        String q = "SELECT * FROM "+TABLE+" WHERE "+ID_TYPE_DOKUMEN+"="+"'"+idType+"'" ;
        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);
        int count = c.getCount();
        c.close();
        close();
        return count;
    }

    public DokumenOfflineModel select(Integer id) {
        if (id == null)
            return null;
        String q = "SELECT * FROM "+TABLE+" WHERE "+_ID+"="+id;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        DokumenOfflineModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new DokumenOfflineModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setIdtypedokumen(c.getString((c.getColumnIndex(ID_TYPE_DOKUMEN))));
                    model.setTypedokumen(c.getString((c.getColumnIndex(TYPE_DOKUMEN))));
                    model.setNamadokumen((c.getString(c.getColumnIndex(NAMA_DOKUMEN))));
                    model.setDocid(c.getString(c.getColumnIndex(DOC_ID)));
                    model.setImageUrl(c.getString(c.getColumnIndex(IMAGE_URL)));
                    } while (c.moveToNext());
            }
        }
        close();
        return model;
    }


    public List<DokumenOfflineModel> selectList(String app) {
        String q = "SELECT * FROM "+TABLE+" GROUP BY "+ ID_TYPE_DOKUMEN+ "";
        List<DokumenOfflineModel> list = new ArrayList<>();
//
//        String q = "SELECT DISTINCT "+ ID_TYPE_DOKUMEN +
//                "," + _ID +" " +
//                "," + TYPE_DOKUMEN +" " +
//                "," + NAMA_DOKUMEN +" " +
//                "," + DOC_ID +" " +
//                "," + IMAGE_URL +" " +
//                "FROM "+TABLE+ " ORDER BY " + NAMA_DOKUMEN;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        DokumenOfflineModel model;
        if (c.moveToFirst()) {
            do {
                model = new DokumenOfflineModel();
                model.setId(c.getInt(c.getColumnIndex(_ID)));
                model.setIdtypedokumen(c.getString((c.getColumnIndex(ID_TYPE_DOKUMEN))));
                model.setTypedokumen(c.getString((c.getColumnIndex(TYPE_DOKUMEN))));
                model.setNamadokumen((c.getString(c.getColumnIndex(NAMA_DOKUMEN))));
                model.setDocid(c.getString(c.getColumnIndex(DOC_ID)));
                model.setImageUrl(c.getString(c.getColumnIndex(IMAGE_URL)));
                list.add(model);
            } while (c.moveToNext());
        }

        close();
        return list;
    }

    public List<DokumenOfflineModel> selectListbyIdtype(String idType) {
        String q = "SELECT * FROM "+TABLE+" WHERE "+ID_TYPE_DOKUMEN+"="+"'"+idType+"'";
        List<DokumenOfflineModel> list = new ArrayList<>();

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        DokumenOfflineModel model;
        if (c.moveToFirst()) {
            do {
                model = new DokumenOfflineModel();
                model.setId(c.getInt(c.getColumnIndex(_ID)));
                model.setIdtypedokumen(c.getString((c.getColumnIndex(ID_TYPE_DOKUMEN))));
                model.setTypedokumen(c.getString((c.getColumnIndex(TYPE_DOKUMEN))));
                model.setNamadokumen((c.getString(c.getColumnIndex(NAMA_DOKUMEN))));
                model.setDocid(c.getString(c.getColumnIndex(DOC_ID)));
                model.setImageUrl(c.getString(c.getColumnIndex(IMAGE_URL)));
                list.add(model);
            } while (c.moveToNext());
        }

        close();
        return list;
    }
}
