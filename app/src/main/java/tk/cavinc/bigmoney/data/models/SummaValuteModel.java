package tk.cavinc.bigmoney.data.models;

/**
 * Created by cav on 27.09.18.
 */

public class SummaValuteModel {
    private double mBalanse;
    private String mValute;

    public SummaValuteModel(double balanse, String valute) {
        mBalanse = balanse;
        mValute = valute;
    }

    public double getBalanse() {
        return mBalanse;
    }

    public String getValute() {
        return mValute;
    }
}
