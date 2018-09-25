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

    private BankListAdapter mAdapter;

    private SelectFragmentListener mSelectFragmentListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mSelectFragmentListener = (SelectFragmentListener) context;
    }

    public MainFragment(){
        mDataManager = DataManager.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        mListView = rootView.findViewById(R.id.main_lv);
        mListView.setOnItemLongClickListener(this);

        updateUI();

        return rootView;
    }

    private void updateUI(){
        ArrayList<MainBankModel> data = new ArrayList<>();

        ArrayList<SheetModel> sheet1 = new ArrayList<>();
        sheet1.add(new SheetModel("320405434534",434.54));
        sheet1.add(new SheetModel("4023412412412",64342));

        data.add(new MainBankModel("Сбребанк",sheet1));

        ArrayList<SheetModel> sheet2 = new ArrayList<>();
        sheet2.add(new SheetModel("324234234",345232.0));

        data.add(new MainBankModel("ВТБ 24",sheet2));

        if (mAdapter == null ){
            mAdapter = new BankListAdapter(getActivity(),R.layout.main_bank_item,data);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
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
}
