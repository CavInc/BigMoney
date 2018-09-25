package tk.cavinc.bigmoney.data.models;

/**
 * Created by cav on 25.09.18.
 */

public class CurseModel {
    private String mInValute;
    private String mOutValute;
    private double mConvert;

    public CurseModel(String inValute, String outValute, double convert) {
        mInValute = inValute;
        mOutValute = outValute;
        mConvert = convert;
    }

    public String getInValute() {
        return mInValute;
    }

    public String getOutValute() {
        return mOutValute;
    }

    public double getConvert() {
        return mConvert;
    }
}
