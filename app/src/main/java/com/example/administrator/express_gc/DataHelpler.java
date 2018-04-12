package com.example.administrator.express_gc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/12/26.
 */

public class DataHelpler extends SQLiteOpenHelper {

    public static final String users = "users";
    protected static final String cname = "cname";
    protected static final String names = "names";
    protected static final String num = "num";
    protected static final int version=1;


    public DataHelpler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DataHelpler(Context context){
        this(context,users,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + users + " (" + cname + " VARCHAR, " + names + " VARCHAR, " + num + " VARCHAR);");
    }

    public String getcname() {
        String cname = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT cname  FROM '" + users + "';", null);
        while (cursor.moveToNext()) {
            cname = cursor.getString(0).toString();
        }
        db.close();
        return cname;
    }


    public void insertdata(String cname1, String names1, String num1) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("insert into '" + users + "'('" + cname + "','" + names + "','" + num + "') values ('" + cname1 + "','" + names1 + "','" + num1 + "');");
        db.close();
    }

    public void deletedata() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM  '" + users + "';");
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
