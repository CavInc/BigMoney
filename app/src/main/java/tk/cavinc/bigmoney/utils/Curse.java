package tk.cavinc.bigmoney.utils;

import java.util.ArrayList;

import tk.cavinc.bigmoney.data.managers.DataManager;
import tk.cavinc.bigmoney.data.models.CurseModel;

public class Curse {
    private static Curse INSTANCE = null;

    private ArrayList<CurseModel> mCurses;
    private DataManager mDataManager;

    public static Curse getInstance(){
        if (INSTANCE == null){
            INSTANCE = new Curse();
        }
        return INSTANCE;
    }

    public Curse(){
        mDataManager = DataManager.getInstance();
    }

    // обновляем данные из базы
    public void refresh(){
        mCurses = mDataManager.getDB().getCurse();
    }

    public ArrayList<CurseModel> getCurses() {
        return mCurses;
    }

    public void setCurses(ArrayList<CurseModel> curses) {
        mCurses = curses;
    }

    // возвращаем коэфицент по паре валют
    public double getParam(String in_valute,String out_valute) {
        int id = mCurses.indexOf(new CurseModel(in_valute,out_valute,0));
        if (id !=-1) {
            return mCurses.get(id).getConvert();
        }
        return 1;
    }
}