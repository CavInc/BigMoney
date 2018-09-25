package tk.cavinc.bigmoney.data.dbase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cav on 24.09.18.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1 ;
    public static final String DATABASE_NAME = "bigmoney.db3";

    public static final String VALUTE = "valute";
    public static final String BANK = "banks";
    public static final String CURSE = "curse";
    public static final String SHEET = "sheets";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db,0,DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        updateDatabase(db,oldVersion,newVersion);
    }

    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion<1){
            db.execSQL("create table "+VALUTE+"(" +
                    "title text," +
                    "primary key(title))");

            db.execSQL("create table "+BANK+"(" +
                    "name_bank text," +
                    "primary key(name_bank))");

            db.execSQL("create table "+CURSE+"(" +
                    "in_name text," +
                    "out_name text," +
                    "param real default 0," +
                    "primary key (in_name,out_name))");

            db.execSQL("create table "+SHEET+"(" +
                    "bank text not null," +
                    "sheet text not null," +
                    "valute text not null," +
                    "balanse real default 0," +
                    "use_sheet integer default 0,"+ // используем счет или нет
                    "primary key (bank,sheet,valute))");

        }
    }

}
