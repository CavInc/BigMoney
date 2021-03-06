package tk.cavinc.bigmoney.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tk.cavinc.bigmoney.R;
import tk.cavinc.bigmoney.ui.interfaces.DeleteSheetListener;

public class CustomExpandListAdapter extends BaseExpandableListAdapter implements View.OnClickListener{

    private Context mContext;
    private List<? extends Map> mGroupData;
    private int mGroupLayout;
    private String[] mGroupFrom;
    private int[] mGroupTo;

    private List<? extends List> mChildData;
    private int mChildLayout;
    private String[] mChildFrom;
    private int[] mChildTo;

    private LayoutInflater mInflater;
    private DeleteSheetListener mDeleteSheetListener;

    public CustomExpandListAdapter(Context context, List<? extends Map> groupData, int groupLayout,
                                   String[] groupFrom, int[] groupTo, List<? extends List> childData,
                                   int childLayout, String[] childFrom, int[] childTo,
                                   DeleteSheetListener listener) {
        mContext = context;
        mGroupData = groupData;
        mGroupLayout = groupLayout;
        mGroupFrom = groupFrom;
        mGroupTo = groupTo;
        mChildData = childData;
        mChildLayout = childLayout;
        mChildFrom = childFrom;
        mChildTo = childTo;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDeleteSheetListener = listener;
    }


    @Override
    public int getGroupCount() {
        return mGroupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildData.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = mInflater.inflate(mGroupLayout,null);
        } else {
            v = convertView;
        }

        bindView(v, mGroupData.get(groupPosition), mGroupFrom, mGroupTo);
        return v;
    }

    private void bindView(View view, Map<String, ?> data, String[] from, int[] to) {
        int len = to.length;

        for (int i = 0; i < len; i++) {
            TextView v = (TextView)view.findViewById(to[i]);
            if (v != null) {
                v.setText((String)data.get(from[i]));
            }
        }
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v;
        v = mInflater.inflate(mChildLayout,null);

        HashMap l = (HashMap) mChildData.get(groupPosition).get(childPosition);
        TextView tv = v.findViewById(mChildTo[0]);
        String dt = (String) l.get(mChildFrom[0]);
        tv.setText(dt);

        String gr = (String) ((HashMap) mGroupData.get(groupPosition)).get("groupName");
        String[] tg = new String[]{gr,dt};

        ImageView img = v.findViewById(R.id.delete_item);
        img.setTag(tg);
        img.setOnClickListener(this);

        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setDate(List<? extends Map> groupData,List<? extends List> childData){
        mGroupData.clear();
        mGroupData = (groupData);

        mChildData.clear();
        mChildData = childData;
        this.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (mDeleteSheetListener != null){
            String [] tg = (String[]) v.getTag();
            mDeleteSheetListener.onDeleteSheet(tg[0],tg[1]);
        }
    }
}