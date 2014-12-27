package com.area.EnvironMange.adapter;

import android.content.Context;
import android.content.ServiceConnection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.area.EnvironMange.R;
import com.area.EnvironMange.model.SanitationArea;

import java.util.List;

/**
 * Created by liuzwei on 2014/12/15.
 */
public class TeachBuildingAreaAdapter extends BaseAdapter {
    private List<SanitationArea> list;
    private Context mContext;
    private ViewHolder holder;

    public TeachBuildingAreaAdapter(List<SanitationArea> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
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
        if (convertView ==  null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.jiaoxuelou_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.jiaoxuelou_item_title);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            SanitationArea area = list.get(position);
            holder.name.setText(area.getMc());
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }

    class ViewHolder{
        TextView name;
    }
}
