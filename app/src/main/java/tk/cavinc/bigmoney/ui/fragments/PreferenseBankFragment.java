package tk.cavinc.bigmoney.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import tk.cavinc.bigmoney.R;
import tk.cavinc.bigmoney.data.managers.DataManager;

/**
 * Created by cav on 24.09.18.
 */

public class PreferenseBankFragment extends Fragment implements View.OnClickListener {

    private DataManager mDataManager;

    private Spinner mValute;
    private Spinner mBank;
    private EditText mSheet;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pref_bank_fragment, container, false);
        mDataManager = DataManager.getInstance();

        rootView.findViewById(R.id.pref_add_sheet).setOnClickListener(this);
        mValute = rootView.findViewById(R.id.pref_valute);
        mBank = rootView.findViewById(R.id.pref_bank);

        ArrayList<String> valueData = mDataManager.getDB().getValute();

        ArrayAdapter<String> adapterValute = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,valueData);
        adapterValute.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mValute.setAdapter(adapterValute);

        ArrayList<String> bankData = mDataManager.getDB().getBank();

        ArrayAdapter<String> adapterBanl = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,bankData);
        adapterBanl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBank.setAdapter(adapterBanl);


        return rootView;
    }

    @Override
    public void onClick(View view) {

    }
}
