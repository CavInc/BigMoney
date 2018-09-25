package tk.cavinc.bigmoney.ui.activites;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import tk.cavinc.bigmoney.R;
import tk.cavinc.bigmoney.data.managers.DataManager;
import tk.cavinc.bigmoney.data.models.CurseModel;
import tk.cavinc.bigmoney.data.models.RequestSheetModel;
import tk.cavinc.bigmoney.data.network.RequestNetwork;
import tk.cavinc.bigmoney.ui.fragments.BankFragment;
import tk.cavinc.bigmoney.ui.fragments.MainFragment;
import tk.cavinc.bigmoney.ui.fragments.PreferenseBankFragment;
import tk.cavinc.bigmoney.ui.interfaces.SelectFragmentListener;
import tk.cavinc.bigmoney.utils.ConstantManager;

public class MainActivity extends AppCompatActivity implements SelectFragmentListener {
    private ActionBar mActionBar;
    private DataManager mDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataManager = DataManager.getInstance();
        //setContentView(R.layout.activity_main);

        viewFragment(new MainFragment(), "MAIN");

        mActionBar = getSupportActionBar();

        getServerData();
    }

    private void getServerData() {
        new RequestAllData().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_setting) {
            changeReturnMode(true);
            viewFragment(new PreferenseBankFragment(), "PREF");
        }
        if (item.getItemId() == android.R.id.home) {
            backFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    // возвращаем главное окно
    private void backFragment() {
        changeReturnMode(false);
        viewFragment(new MainFragment(), "MAIN");
    }

    // переключаем режим возврата
    private void changeReturnMode(boolean mode) {
        if(mActionBar !=null){
            mActionBar.setDisplayHomeAsUpEnabled(mode);
        }
    }

    // устанавливаем фрагмент в контейнер
    private void viewFragment(Fragment fragment, String tag){
        FragmentTransaction trz = getSupportFragmentManager().beginTransaction();
        trz.replace(android.R.id.content,fragment,tag);
        trz.commit();
    }

    @Override
    public void onChangeFragment(Fragment fragment) {

    }

    @Override
    public void onChangeFragment(int fragment) {
        switch (fragment){
            case ConstantManager.BANK:
                viewFragment(BankFragment.newInstance(),"BANK");
                changeReturnMode(true);
                break;
        }

    }

    // заправшиваем данные с сети
    private class RequestAllData extends AsyncTask<Void,Void,Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            RequestNetwork requestNetwork = new RequestNetwork();
            ArrayList<String> bank = requestNetwork.getBank();
            mDataManager.getDB().setBank(bank);

            ArrayList<String> valute = requestNetwork.getValute();
            mDataManager.getDB().setValute(valute);

            ArrayList<CurseModel> curse = requestNetwork.getCurse();
            mDataManager.getDB().setCurse(curse);

            ArrayList<RequestSheetModel> sheets = requestNetwork.getSheet();
            mDataManager.getDB().setSheet(sheets);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Fragment fg = getSupportFragmentManager().findFragmentByTag("main");
            if (fg != null && fg.isVisible()) {
                ((MainFragment) fg).refreshData();
            }
        }
    }
}