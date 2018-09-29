package tk.cavinc.bigmoney.data.dbase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


import tk.cavinc.bigmoney.data.models.CurseModel;
import tk.cavinc.bigmoney.data.models.MainBankModel;
import tk.cavinc.bigmoney.data.models.RequestSheetModel;
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

    // записали валюту
    public void setValute(ArrayList<String> data) {
        open();
        ContentValues values = new ContentValues();
        for (String l: data){
            values.clear();
            values.put("title",l);
            database.insertWithOnConflict(DBHelper.VALUTE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        }
        close();
    }

    // записали список банков
    public void setBank (ArrayList<String> data) {
        open();
        ContentValues values = new ContentValues();
        for (String l : data){
            values.clear();
            values.put("name_bank",l);
            database.insertWithOnConflict(DBHelper.BANK,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        }
        close();
    }

    // записать список курсов валют
    public void setCurse(ArrayList<CurseModel> data){
        open();
        ContentValues values = new ContentValues();
        for (CurseModel lx : data){
            values.put("in_name",lx.getInValute());
            values.put("out_name",lx.getOutValute());
            values.put("param",lx.getConvert());
            database.insertWithOnConflict(DBHelper.CURSE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        }
        close();
    }
    // читаем список курсов валют
    public ArrayList<CurseModel> getCurse(){
        ArrayList<CurseModel> rec = new ArrayList<>();
        open();
        Cursor cursor = database.query(DBHelper.CURSE,new String[]{"in_name","out_name","param"},null,null,null,null,null);
        while (cursor.moveToNext()){
            rec.add(new CurseModel(
                    cursor.getString(cursor.getColumnIndex("in_name")),
                    cursor.getString(cursor.getColumnIndex("out_name")),
                    cursor.getDouble(cursor.getColumnIndex("param"))
            ));
        }
        close();
        return rec;
    }

    // сохранитм список счетов
    public void setSheet(ArrayList<RequestSheetModel> data){
        open();
        ContentValues values = new ContentValues();
        for (RequestSheetModel lx : data){
            values.put("bank",lx.getBank());
            values.put("sheet",lx.getSheet());
            values.put("valute",lx.getValute());
            values.put("balanse",lx.getBalanse());
            database.insertWithOnConflict(DBHelper.SHEET,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        }
        close();
    }


    // получим список банков с валютами для главной
    public ArrayList<MainBankModel> getBankMain(){
        ArrayList<MainBankModel> bankModels = new ArrayList<>();
        open();
        Cursor cursor = database.rawQuery("select distinct bank from sheets",null);
        while (cursor.moveToNext()){
            String sql = "select sheet,valute,balanse from sheets\n" +
                    "where bank='"+cursor.getString(0)+"'";
            Cursor cursor2 = database.rawQuery(sql,null);
            ArrayList<SheetModel> sheetModels = new ArrayList<>();
            while (cursor2.moveToNext()){
                sheetModels.add(new SheetModel(
                        cursor2.getString(cursor2.getColumnIndex("sheet")),
                        cursor2.getDouble(cursor2.getColumnIndex("balanse")),
                        cursor2.getString(cursor2.getColumnIndex("valute"))));
            }
            bankModels.add(new MainBankModel(cursor.getString(0),sheetModels));
        }
        close();
        return bankModels;
    }

    // получили значение конверсии
    public double getConverse(String inValute,String outValute){
        double res = 0;
        open();
        Cursor cursor = database.query(DBHelper.CURSE,new String[]{"param"},"in_name=? and out_name=?",new String[]{inValute,outValute},null,null,null);
        while (cursor.moveToNext()){
            res = cursor.getDouble(0);
        }
        close();
        return res;
    }

    // получили сумму по счетам
    public double getAllSheetSumValute(String valute) {
        double rec = 0.0;
        open();
        String sql = "select sum(st.balanse) as balanse,sum(st.balanse/coalesce(cr.param,1)) as convbalance from sheets st\n" +
                " left join curse cr on st.valute=in_name and cr.out_name='"+valute+"'";
        Cursor cursor = database.rawQuery(sql,null);
        while (cursor.moveToNext()){
            rec = cursor.getDouble(1);
        }
        close();
        return rec;
    }

    // получили добавлены сщета
    public ArrayList<String> getLinkedSheet(String bank) {
        ArrayList<String> rec = new ArrayList<>();
        String sql = "select sheet from "+DBHelper.SELECT_SHEET+"\n" +
                "where bank='"+bank+"'";
        open();
        Cursor cursor = database.rawQuery(sql,null);
        while (cursor.moveToNext()){
            rec.add(cursor.getString(0));
        }
        close();
        return rec;
    }

    // обновляем данные о привязанном счете
    public void addSheetInBank(String bank,String sheet) {
        open();
        /*
        ContentValues values = new ContentValues();
        values.put("use_sheet",1);
        database.update(DBHelper.SHEET,values,"bank=? and sheet=?",new String[]{bank,sheet});
        */
        ContentValues values = new ContentValues();
        values.put("bank",bank);
        values.put("sheet",sheet);
        int rec = (int) database.insertWithOnConflict(DBHelper.SELECT_SHEET,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        close();
    }
}
