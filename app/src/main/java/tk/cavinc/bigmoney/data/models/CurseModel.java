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

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj == null)
            return false;
                /* Удостоверимся, что ссылки имеют тот же самый тип */
        if(!(getClass() == obj.getClass())) {
            return false;
        }else {
            CurseModel tmp = (CurseModel) obj;
            if (tmp.mInValute.equals(this.mInValute) && tmp.mOutValute.equals(this.mOutValute)){
                return true;
            }
        }
        return false;
    }
}
