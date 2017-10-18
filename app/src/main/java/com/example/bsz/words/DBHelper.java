package com.example.bsz.words;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    static String TABLE_NAME_1 = "allWords";//表名
    static String FILED_WORD = "word";//列名
    static String FILED_TRANS = "trans";//列名
    static String FILED_TAGS = "tags";//列名

    static String FILED_PHONE = "phone";//列名音标

    static String TABLE_NAME_2 = "myWords";
    static String FILED_NOTE = "note";//列名
    static String FILED_TIME  = "time";//列名


    DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.v(TAG,"onCreate");
        String sql = "CREATE TABLE " + TABLE_NAME_1 + "("+
                FILED_WORD +" char(20) primary key,"+
                FILED_PHONE +" text not null,"+
                FILED_TRANS + " text not null,"+
                FILED_TAGS + " char(20))";
        try {
            Log.v(TAG,sql);
            sqLiteDatabase.execSQL(sql);
        }catch (SQLException e){
            Log.v(TAG,sql);
            Log.e(TAG,"onCreate:"+ TABLE_NAME_1 +"Error"+e.toString());
        }
        sql = "CREATE TABLE " + TABLE_NAME_2 + "("+
                FILED_WORD +" char(20) primary key ,"+
                FILED_TIME + " char(20),"+
                FILED_NOTE + " text,"+
                FILED_TRANS + " text not null,"+
                FILED_TAGS + " char(20),"+
                FILED_PHONE + " char(30))";
        try {

            sqLiteDatabase.execSQL(sql);
        }catch (SQLException e){
            Log.e(TAG,sql);
            Log.e(TAG,"onCreate:"+ TABLE_NAME_2 +"Error"+e.toString());
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME_1;
        sqLiteDatabase.execSQL(sql);
        sql = "DROP TABLE IF EXISTS " + TABLE_NAME_2;
        sqLiteDatabase.execSQL(sql);
        this.onCreate(sqLiteDatabase);
    }
}
