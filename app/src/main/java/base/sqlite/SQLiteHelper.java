package base.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import base.data.LocationPoolData;
import base.data.UserData;

public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context, String name, Integer ver) {
        super(context, name, null, ver);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ActiveData.CREATE_TABLE);
        db.execSQL(UserData.CREATE_TABLE);
        db.execSQL(LocationPoolData.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        //onCreate(db);
    }
}