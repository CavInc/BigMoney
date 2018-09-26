package tk.cavinc.bigmoney.data.models;

/**
 * Created by cav on 24.09.18.
 */

public class SheetModel {
    private String mSheet;
    private double mBalance;
    private String mValute;
    private double mParamConvet = 1;


    public SheetModel(String sheet, double balance) {
        mSheet = sheet;
        mBalance = balance;
    }

    public SheetModel(String sheet, double balance, String valute) {
        mSheet = sheet;
        mBalance = balance;
        mValute = valute;
    }

    public SheetModel(String sheet, double balance, String valute, double paramConvet) {
        mSheet = sheet;
        mBalance = balance;
        mValute = valute;
        mParamConvet = paramConvet;
    }

    public String getSheet() {
        return mSheet;
    }

    public double getBalance() {
        return mBalance;
    }

    public String getValute() {
        return mValute;
    }

    public void setParamConvet(double paramConvet) {
        mParamConvet = paramConvet;
    }

    public double getConvetBalance(){
        return mBalance/mParamConvet;
    }
}
