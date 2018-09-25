package tk.cavinc.bigmoney.data.managers;

import android.content.Context;

import tk.cavinc.bigmoney.data.dbase.DBConnect;
import tk.cavinc.bigmoney.utils.App;

/**
 * Created by cav on 24.09.18.
 */

public class DataManager {
    private static DataManager INSTANCE = null;

    private Context mContext;
    private DBConnect mDB;
   // private PreManager mPreManager;

    public static DataManager getInstance() {
        if (INSTANCE==null){
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    public DataManager(){
        mContext = App.getContext();
        mDB = new DBConnect(mContext);
    }

    public Context getContext() {
        return mContext;
    }

    public DBConnect getDB() {
        return mDB;
    }
}
