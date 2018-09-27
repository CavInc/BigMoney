package tk.cavinc.bigmoney.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tk.cavinc.bigmoney.R;
import tk.cavinc.bigmoney.data.managers.DataManager;
import tk.cavinc.bigmoney.data.models.SheetModel;
import tk.cavinc.bigmoney.data.models.SummaValuteModel;
import tk.cavinc.bigmoney.ui.adapters.BankSteetAdapter;
import tk.cavinc.bigmoney.ui.interfaces.SelectFragmentListener;
import tk.cavinc.bigmoney.utils.Bank;
import tk.cavinc.bigmoney.utils.Curse;

/**
 * Created by cav on 24.09.18.
 */

public class BankFragment extends Fragment {

    private DataManager mDataManager;
    private TextView mBankTitle;
    private TextView mBankBalance;

    private BankSteetAdapter mAdapter;
    private ListView mListView;

    private Bank mBank;
    private Curse mCurse;

    public static BankFragment newInstance(){

        BankFragment fragment = new BankFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bank_fragment, container, false);
        mBank = Bank.getInstance();

        mBankTitle = rootView.findViewById(R.id.bank_title);
        mBankBalance = rootView.findViewById(R.id.bank_balance);

        mListView = rootView.findViewById(R.id.bank_lv);

        mBankTitle.setText(mBank.getBank().getName());

        SummaValuteModel dataSumm  = mBank.getBank().getSumm();

        mBankBalance.setText(String.valueOf(dataSumm.getBalanse()+" "+dataSumm.getValute()));

        // немного криво но пусть пока так
        mCurse = Curse.getInstance();
        mCurse.refresh();

        //ArrayList<SheetModel> sheet = mBank.getBank().getSheetModels();
        ArrayList<SheetModel> sheet = mBank.getBank().getSheetModels();

        mAdapter = new BankSteetAdapter(getActivity(),R.layout.bank_item,sheet);
        mListView.setAdapter(mAdapter);

        return rootView;
    }

    private void updateUI(){

    }


}
