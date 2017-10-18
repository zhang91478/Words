package com.example.bsz.words;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;



class DBManger extends DBHelper {

    DBManger(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    List<WordOfAll> getWordOfAllFromDateBase(String sql){
        Log.v("DBManger","sql="+sql);
        List<WordOfAll> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(sql,null);
            while (cursor.moveToNext()){
                WordOfAll wordOfAll = new WordOfAll();
                wordOfAll.setWord(cursor.getString(0));
                wordOfAll.setPhone(cursor.getString(1));
                wordOfAll.setTrans(cursor.getString(2));
                wordOfAll.setTag(cursor.getString(3));
                list.add(wordOfAll);
            }

            cursor.close();
        }catch (Exception e){
            Log.e("DBManger","sql="+sql);
            Log.e("DBManger",e.toString());
        }
        return list;
    }

    List<WordOfMe> getWordOfMeFromDateBase(String sql){
        List<WordOfMe> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(sql,null);
            for (int i = 0; i < cursor.getCount(); i++) {
                WordOfMe wordOfMe = new WordOfMe();
                cursor.moveToNext();
                wordOfMe.setWord(cursor.getString(0));
                wordOfMe.setTime(cursor.getString(1));
                wordOfMe.setNote(cursor.getString(2));
                wordOfMe.setTrans(cursor.getString(3));
                wordOfMe.setTags(cursor.getString(4));
                wordOfMe.setPhone(cursor.getString(5));
                list.add(wordOfMe);
            }
            cursor.close();
        }catch (Exception e){
            Log.e("DBManger","sql="+sql);
            Log.e("DBManger",e.toString());
        }
        return list;
    }
    void deleteByWord(String word){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        String sql;
        sql="DELETE FROM "+TABLE_NAME_2+" WHERE "+FILED_WORD+" = '"+word+"'";
        try{
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("DBManger","sql="+sql);
            Log.e("DBManger",e.toString());
        }finally {
            db.endTransaction();
        }
    }
    void Insert(WordOfAll word){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        String sql;
        sql = "INSERT INTO "+TABLE_NAME_1+"("+
                FILED_WORD+","+
                FILED_PHONE+","+
                FILED_TRANS+","+
                FILED_TAGS+")"+" VALUES('"+
                word.getWord()+ "','"
                +word.getPhone()+"','"
                +word.getTrans()+"','"
                +word.getTag()+"')";
        try{
            db.execSQL(sql);
            Log.v("DBManger","插入成功 sql="+sql);
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("DBManger","插入失败 sql="+sql);
            Log.e("DBManger",e.toString());
        }finally {
            db.endTransaction();
        }
    }
    void Insert(WordOfMe word){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        String sql ;
        sql = "INSERT INTO "+TABLE_NAME_2+"("+
                FILED_WORD+","+
                FILED_TIME+","+
                FILED_NOTE+","+
                FILED_TRANS+","+
                FILED_TAGS+","+
                FILED_PHONE+")"+" VALUES('"+
                word.getWord()+ "','"
                +word.getTime()+"','"
                +word.getNote()+"','"
                +word.getTrans()+"','"
                +word.getTags()+"','"
                +word.getPhone()+"')";

        try{
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("DBManger","sql="+sql);
            Log.e("DBManger",e.toString());
        }finally {
            db.endTransaction();
        }
    }



}
