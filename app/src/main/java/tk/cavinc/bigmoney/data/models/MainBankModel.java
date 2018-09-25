package tk.cavinc.bigmoney.data.models;

import java.util.ArrayList;

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

    public double getSumm(){
        double rec = 0;
        for (SheetModel lx : mSheetModels){
            rec += lx.getBalance();
        }
        return rec;
    }

    public double getSummValute(String valute){
        double rec = 0;

        return rec;
    }


}
