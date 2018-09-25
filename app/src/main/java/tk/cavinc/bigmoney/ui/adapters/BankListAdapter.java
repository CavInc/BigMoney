package tk.cavinc.bigmoney.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import tk.cavinc.bigmoney.R;
import tk.cavinc.bigmoney.data.models.MainBankModel;

/**
 * Created by cav on 24.09.18.
 */

public class BankListAdapter extends ArrayAdapter<MainBankModel> {
    private LayoutInflater mInflater;
    private int resLayout;


    public BankListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<MainBankModel> objects) {
        super(context, resource, objects);
        resLayout = resource;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        View row = convertView;
        if (row == null) {
            row = mInflater.inflate(resLayout, parent, false);
            holder = new ViewHolder();
            holder.mBank = row.findViewById(R.id.main_bank_name);
            holder.mBalanse = row.findViewById(R.id.main_bank_balanse);
            row.setTag(holder);
        } else {
            holder = (ViewHolder)row.getTag();
        }

        MainBankModel record = getItem(position);
        holder.mBank.setText(record.getName()+" "+String.valueOf(record.getCountShets())+" сч.");

        return row;
    }

    class ViewHolder {
        public TextView mBank;
        public TextView mBalanse;
    }
}
