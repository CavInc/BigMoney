package tk.cavinc.bigmoney.data.managers;

import android.content.SharedPreferences;

import tk.cavinc.bigmoney.utils.App;

public class PrefManager {
    private static final String VALUTE = "VALUTE";
    private SharedPreferences mSharedPreferences;

    public PrefManager(){
        mSharedPreferences = App.getSharedPreferences();
    }

    public String getConvValute(){
        return mSharedPreferences.getString(VALUTE,null);
    }

    public void setConvValute(String valute){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(VALUTE,valute);
        editor.apply();
    }
}

