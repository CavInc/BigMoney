package tk.cavinc.bigmoney.ui.activites;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import tk.cavinc.bigmoney.R;
import tk.cavinc.bigmoney.ui.fragments.BankFragment;
import tk.cavinc.bigmoney.ui.fragments.MainFragment;
import tk.cavinc.bigmoney.ui.fragments.PreferenseBankFragment;
import tk.cavinc.bigmoney.ui.interfaces.SelectFragmentListener;
import tk.cavinc.bigmoney.utils.ConstantManager;

public class MainActivity extends AppCompatActivity implements SelectFragmentListener {
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        viewFragment(new MainFragment(), "MAIN");

        mActionBar = getSupportActionBar();
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
}
