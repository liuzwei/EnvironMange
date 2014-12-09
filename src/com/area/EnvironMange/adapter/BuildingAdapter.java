package com.area.EnvironMange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.area.EnvironMange.R;
import com.area.EnvironMange.model.Building;

import java.util.List;

/**
 * Created by liuzwei on 2014/12/9.
 */
public class BuildingAdapter extends BaseAdapter {
    private Context mContext;
    private List<Building> list;
    private ViewHolder holder;

    public BuildingAdapter(Context mContext, List<Building> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.building_item, null);
            holder.content = (TextView) convertView.findViewById(R.id.building_item_text);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Building building = list.get(position);
        holder.content.setText(building.getMC());

        return convertView;
    }

    class ViewHolder{
        TextView content;

    }
}
