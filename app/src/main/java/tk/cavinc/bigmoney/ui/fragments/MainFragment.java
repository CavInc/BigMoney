package tk.cavinc.bigmoney.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tk.cavinc.bigmoney.R;
import tk.cavinc.bigmoney.data.managers.DataManager;
import tk.cavinc.bigmoney.data.models.MainBankModel;
import tk.cavinc.bigmoney.data.models.SheetModel;
import tk.cavinc.bigmoney.ui.adapters.BankListAdapter;
import tk.cavinc.bigmoney.ui.interfaces.SelectFragmentListener;
import tk.cavinc.bigmoney.utils.Bank;
import tk.cavinc.bigmoney.utils.ConstantManager;

/**
 * Created by cav on 24.09.18.
 */

public class MainFragment extends Fragment implements AdapterView.OnItemLongClickListener{

    private DataManager mDataManager;
    private ListView mListView;
    private TextView mSummBalanse;

    private BankListAdapter mAdapter;

    private SelectFragmentListener mSelectFragmentListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mSelectFragmentListener = (SelectFragmentListener) context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataManager = DataManager.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        mSummBalanse = rootView.findViewById(R.id.main_balanse_itogo);

        mListView = rootView.findViewById(R.id.main_lv);
        mListView.setOnItemLongClickListener(this);

        updateUI();

        return rootView;
    }

    private void updateUI(){

        ArrayList<MainBankModel> datax = mDataManager.getDB().getBankMain();

        if (mAdapter == null ){
            mAdapter = new BankListAdapter(getActivity(),R.layout.main_bank_item,datax);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.setData(datax);
            mAdapter.notifyDataSetChanged();
        }

        double balanseValute = mDataManager.getDB().getAllSheetSumValute(mDataManager.getPreManager().getConvValute());

        mSummBalanse.setText("Итого : "+String.format("%.2f",balanseValute)+" "+mDataManager.getPreManager().getConvValute());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        MainBankModel selModel = (MainBankModel) adapterView.getItemAtPosition(position);
        if (mSelectFragmentListener != null){
            Bank.getInstance().setBank(selModel);
            mSelectFragmentListener.onChangeFragment(ConstantManager.BANK);
        }

        return false;
    }

    public void refreshData(){
        updateUI();
    }
}
