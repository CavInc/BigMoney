package tk.cavinc.bigmoney.data.models;

/**
 * Created by cav on 25.09.18.
 */

public class RequestSheetModel {
    private String mBank;
    private String mValute;
    private String mSheet;
    private double mBalanse;

    public RequestSheetModel(String bank, String valute, String sheet, double balanse) {
        mBank = bank;
        mValute = valute;
        mSheet = sheet;
        mBalanse = balanse;
    }

    public String getBank() {
        return mBank;
    }

    public String getValute() {
        return mValute;
    }

    public String getSheet() {
        return mSheet;
    }

    public double getBalanse() {
        return mBalanse;
    }
}
