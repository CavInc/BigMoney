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
import tk.cavinc.bigmoney.data.models.SheetModel;
import tk.cavinc.bigmoney.utils.Curse;

/**
 * Created by cav on 24.09.18.
 */

public class BankSteetAdapter extends ArrayAdapter<SheetModel> {
    private LayoutInflater mInflater;
    private int resLayout;

    public BankSteetAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<SheetModel> objects) {
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
            holder.mSheetTitle = row.findViewById(R.id.bank_item_sheet);
            holder.mSheetBalanse = row.findViewById(R.id.bank_item_balanse);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Curse curse = Curse.getInstance();

        SheetModel record = getItem(position);
        holder.mSheetTitle.setText(record.getSheet());
        holder.mSheetBalanse.setText(String.valueOf(record.getBalance())+" "+record.getValute());

        return row;
    }

    class ViewHolder {
        public TextView mSheetTitle;
        public TextView mSheetBalanse;
    }
}
