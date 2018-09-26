package tk.cavinc.bigmoney.data.models;

import android.database.Cursor;

import java.util.ArrayList;

import tk.cavinc.bigmoney.data.managers.DataManager;
import tk.cavinc.bigmoney.utils.Curse;

/**
 * Created by cav on 24.09.18.
 */

public class MainBankModel {
    private int mId;
    private int mCountShets = 0;
    private String mName;
    private ArrayList<SheetModel> mSheetModels;


    public MainBankModel(int countShets, String name) {
        mCountShets = countShets;
        mName = name;
    }

    public MainBankModel(int id, int countShets, String name) {
        mId = id;
        mCountShets = countShets;
        mName = name;
    }

    public MainBankModel( String name, ArrayList<SheetModel> sheetModels) {
        mCountShets = sheetModels.size();
        mName = name;
        mSheetModels = sheetModels;
    }

    public int getId() {
        return mId;
    }

    public int getCountShets() {
        return mCountShets;
    }

    public String getName() {
        return mName;
    }

    public ArrayList<SheetModel> getSheetModels() {
        return mSheetModels;
    }

    public ArrayList<SheetModel> getSheetModelsInConvert(String valute,double param) {
        ArrayList<SheetModel> rec = new ArrayList<>();
        for (SheetModel lx : mSheetModels) {
            rec.add(new SheetModel(lx.getSheet(),lx.getBalance(),lx.getValute(),param));
        }
        return rec;
    }

    public double getSumm(){
        double rec = 0;
        for (SheetModel lx : mSheetModels){
            rec += lx.getBalance();
        }
        return rec;
    }


    /*
    select st.bank,st.sheet,st.valute,st.balanse,(st.balanse/coalesce(cr.param,1)) as convbalance,cr.param,coalesce(cr.param,1) from sheets st
  left join curse cr on st.valute=in_name and cr.out_name='USD'
     */

    public double getSummValute(String valute){
        double rec = 0;
        for (SheetModel lx :mSheetModels) {

        }
        return rec;
    }

    // возврат данных сконвертирванных по счету
    public double getConvertValute(String sheet,String valute) {
        double rec = 0;
        // так делать не надо это жесткое свызваение модулей но фиг с ним
        DataManager dm = DataManager.getInstance();


        return rec;
    }

}
