package tk.cavinc.bigmoney.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tk.cavinc.bigmoney.R;
import tk.cavinc.bigmoney.data.managers.DataManager;
import tk.cavinc.bigmoney.ui.adapters.CustomExpandListAdapter;
import tk.cavinc.bigmoney.ui.interfaces.ChangeValuteListener;
import tk.cavinc.bigmoney.ui.interfaces.DeleteSheetListener;

import static android.content.ContentValues.TAG;

/**
 * Created by cav on 24.09.18.
 */

public class PreferenseBankFragment extends Fragment implements View.OnClickListener,DeleteSheetListener {

    private ExpandableListView mListView;

    private DataManager mDataManager;

    private ChangeValuteListener mValuteListener;

    private Spinner mValute;
    private Spinner mBank;
    private EditText mSheet;
    private CustomExpandListAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mValuteListener = (ChangeValuteListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pref_bank_fragment, container, false);
        mDataManager = DataManager.getInstance();

        rootView.findViewById(R.id.pref_add_sheet).setOnClickListener(this);
        mValute = rootView.findViewById(R.id.pref_valute);
        mBank = rootView.findViewById(R.id.pref_bank);
        mListView = rootView.findViewById(R.id.pref_elv);
        mSheet = rootView.findViewById(R.id.pref_shett);

        rootView.findViewById(R.id.pref_add_sheet).setOnClickListener(this);

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

        mValute.setOnItemSelectedListener(mSelectedListener);

        int pos = adapterValute.getPosition(mDataManager.getPreManager().getConvValute());

        mValute.setSelection(pos);

        updateUI();

        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.pref_add_sheet) {
            String bank = mBank.getSelectedItem().toString();
            mDataManager.getDB().addSheetInBank(bank,mSheet.getText().toString());
            mSheet.setText("");
            updateUI();
        }
    }

    private void updateUI() {

        // заполняем коллекцию групп из массива с названиями групп
        ArrayList<Map<String, String>> groupData = new ArrayList<Map<String, String>>();

        // создаем коллекцию для коллекций элементов
        ArrayList<ArrayList<Map<String, String>>> childData = new ArrayList<ArrayList<Map<String, String>>>();

        // создаем  expand list view

        // список атрибутов групп для чтения
        String groupFrom[] = new String[]{"groupName"};
        // список ID view-элементов, в которые будет помещены атрибуты групп
        int groupTo[] = new int[]{R.id.elg_name};

        // список атрибутов элементов для чтения
        String childFrom[] = new String[]{"itemText", "itemValue"};
        // список ID view-элементов, в которые будет помещены атрибуты элементов
        int childTo[] = new int[]{R.id.expant_list_item_name};


        ArrayList<String> bankData = mDataManager.getDB().getBank();
        for (String lx : bankData) {
            HashMap<String, String> m = new HashMap<String, String>();
            m.put("groupName", lx);
            ArrayList<String> childSheet = mDataManager.getDB().getLinkedSheet(lx);

            ArrayList<Map<String, String>> childDataItem = new ArrayList<Map<String, String>>();
            HashMap<String, String> mx = new HashMap<>();
            for (String lf : childSheet) {
                mx.put("itemText", lf);
                childDataItem.add(mx);
            }
            childData.add(childDataItem);
            groupData.add(m);
        }

        if (adapter == null) {
            adapter = new CustomExpandListAdapter(
                getActivity(),
                groupData,
                R.layout.expant_list_group_item,
                groupFrom,
                groupTo,
                childData,
                R.layout.expand_list_item,
                childFrom,
                childTo,this);

            mListView.setAdapter(adapter);
            for (int i = 0; i < groupData.size(); i++) {
                mListView.expandGroup(i);
            }
        } else {
            adapter.setDate(groupData,childData);
        }
    }

    AdapterView.OnItemSelectedListener mSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Object data = parent.getItemAtPosition(position);
            mDataManager.getPreManager().setConvValute((String) data);
            if (mValuteListener != null) {
                mValuteListener.onChangeValute((String) data);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public void onDeleteSheet(String bank,String sheet) {
        mDataManager.getDB().deleteSheet(bank,sheet);
        updateUI();
    }
}
