package tk.cavinc.bigmoney.utils;

import tk.cavinc.bigmoney.data.models.MainBankModel;

/**
 * Created by cav on 24.09.18.
 */

public class Bank {
    private static Bank INSTANCE = null;

    private MainBankModel bank;

    public static Bank getInstance(){
        if (INSTANCE==null){
            INSTANCE = new Bank();
        }
        return INSTANCE;
    }

    public MainBankModel getBank() {
        return bank;
    }

    public void setBank(MainBankModel bank) {
        this.bank = bank;
    }
}
