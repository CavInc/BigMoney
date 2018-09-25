package tk.cavinc.bigmoney.data.models;

/**
 * Created by cav on 24.09.18.
 */

public class SheetModel {
    private String mSheet;
    private double mBalance;

    public SheetModel(String sheet, double balance) {
        mSheet = sheet;
        mBalance = balance;
    }

    public String getSheet() {
        return mSheet;
    }

    public double getBalance() {
        return mBalance;
    }
}
