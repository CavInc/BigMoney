package tk.cavinc.bigmoney.data.dbase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


import tk.cavinc.bigmoney.data.models.SheetModel;

/**
 * Created by cav on 24.09.18.
 */

public class DBConnect {

    private final DBHelper mDBHelper;
    private SQLiteDatabase database;

    public DBConnect(Context context){
        mDBHelper = new DBHelper(context,DBHelper.DATABASE_NAME,null,DBHelper.DATABASE_VERSION);
    }

    public void open(){
        database = mDBHelper.getWritableDatabase();
    }
    public  void close(){
        if (database!=null) {
            database.close();
        }
    }

    // получили валюту
    public ArrayList<String> getValute(){
        ArrayList<String> rec = new ArrayList<>();
        open();
        Cursor cursor = database.query(DBHelper.VALUTE,new String[]{"title"},null,null,null,null,"title");
        while (cursor.moveToNext()){
            rec.add(cursor.getString(0));
        }
        close();
        return rec;
    }

    // получили банки
    public ArrayList<String> getBank(){
        ArrayList<String> rec = new ArrayList<>();
        open();
        Cursor cursor = database.query(DBHelper.BANK,new String[]{"name_bank"},null,null,null,null,"name_bank");
        while (cursor.moveToNext()){
            rec.add(cursor.getString(0));
        }
        close();
        return rec;
    }


}
