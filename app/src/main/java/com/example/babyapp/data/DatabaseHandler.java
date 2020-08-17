package com.example.babyapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.babyapp.R;
import com.example.babyapp.model.Things;
import com.example.babyapp.util.Util;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler( Context context ) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE="CREATE TABLE "+Util.TABLE_NAME+"("
                +Util.OBJ_ID+" INTEGER PRIMARY KEY,"+Util.OBJ_NAME+" TEXT,"
                +Util.OBJ_COLOR+" TEXT,"+Util.OBJ_SIZE+" TEXT,"+Util.OBJ_QTY+" TEXT"+")";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_TABLE=String.valueOf(R.string.drop_table);

        db.execSQL(DROP_TABLE,new String[]{Util.DATABASE_NAME});
    }

    public void addObjects(Things things)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(Util.OBJ_NAME,things.getName());
        contentValues.put(Util.OBJ_COLOR,things.getColor());
        contentValues.put(Util.OBJ_SIZE,things.getSize());
        contentValues.put(Util.OBJ_QTY,things.getQuantity());
//        contentValues.put(Util.DATe,java.lang.System.currentTimeMillis());
        db.insert(Util.TABLE_NAME,null,contentValues);
        Log.d("TAG", "addObjects: "+"item Inserted");
        db.close();

    }

    public Things getObject(int id){
        Things obj=new Things();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(Util.TABLE_NAME,new String[]{Util.OBJ_ID,Util.OBJ_NAME,Util.OBJ_COLOR,Util.OBJ_SIZE,Util.OBJ_QTY},
                Util.OBJ_ID+"=?",new String[]{String.valueOf(id)},null,null,null);


        return obj;
    }
    public List<Things> getAllThings(){

        SQLiteDatabase db=this.getReadableDatabase();
        List<Things> objectList=new ArrayList<>();

        String getAllContacts="SELECT * FROM "+Util.TABLE_NAME;
        Cursor cursor=db.rawQuery(getAllContacts,null);

        if(cursor.moveToFirst()){
            do{
                Things things=new Things();

                things.setId(Integer.parseInt(cursor.getString(0)));
                things.setName(cursor.getString(1));
                things.setColor(cursor.getString(2));
                things.setSize(cursor.getString(3));
                things.setQuantity(cursor.getString(4));
//                DateFormat dateFormat=DateFormat.getDateInstance();
//                String formattedDate =dateFormat.format(new Date(cursor.getLong(5)).getTime());
//                things.setDate(formattedDate);


                objectList.add(things);
            }while(cursor.moveToNext());
        }

    return objectList;
    }

    public int getItemCount(){
        String countQuery="SELECT * FROM "+Util.TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.rawQuery(countQuery,null);

        return cursor.getCount();
    }
    public int updateThings(Things things)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Util.OBJ_NAME,things.getName());
        contentValues.put(Util.OBJ_COLOR,things.getColor());
        contentValues.put(Util.OBJ_SIZE,things.getSize());
        contentValues.put(Util.OBJ_QTY,things.getQuantity());

        return db.update(Util.TABLE_NAME,contentValues,Util.OBJ_ID+"=?",new String[]{String.valueOf(things.getId())});

    }
    public int deleteThings(int id)
    {
    SQLiteDatabase db=this.getWritableDatabase();

    return db.delete(Util.TABLE_NAME,Util.OBJ_ID+"=?",new String[]{String.valueOf(id)});
    }

}
