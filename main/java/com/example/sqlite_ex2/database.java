package com.example.sqlite_ex2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class database extends SQLiteOpenHelper {

    public static final String DB_NAME = "product.sqlite";

    public static final int DB_VERSION = 1;

    public static final String TBL_NAME = "Product";

    public static final String COL_CODE = "ProductCode";

    public static final String COL_NAME = "ProductName";
    public static final String COL_PRICE = "ProducPrice";

    public database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TBL_NAME + " (" +COL_CODE + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ COL_NAME + " VARCHAR(50), "+ COL_PRICE+ " REAL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TBL_NAME);
        onCreate(db);

    }
    //Select ....
    public Cursor queryData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql,null);
    }
    //insert update , delete

    public void execSql(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }
    public int getNumbOfRow(){
        Cursor cursor = queryData("SELECT * FROM "+ TBL_NAME);
        int numbOfRows = cursor.getCount();
        cursor.close();
        return numbOfRows;

    }
    public void createSampleData(){
        if(getNumbOfRow() == 0){
            try{
                execSql("INSERT INTO "+ TBL_NAME + " VALUES(null,'Heineken1',1900)");
                execSql("INSERT INTO "+ TBL_NAME + " VALUES(null,'Heineken2',1800)");
                execSql("INSERT INTO "+ TBL_NAME + " VALUES(null,'Heineken3',1700)");
            }catch (Exception e){
                Log.e("Error",e.getMessage().toString());

            }

        }
    }
}
