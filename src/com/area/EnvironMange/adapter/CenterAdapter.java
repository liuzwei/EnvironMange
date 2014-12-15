package com.area.EnvironMange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.area.EnvironMange.R;
import com.area.EnvironMange.model.Building;
import com.area.EnvironMange.model.Center;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 2014/12/9.
 */
public class CenterAdapter extends BaseAdapter {
    private Context mContext;
    private List<HashMap<String,Center>> list;
    private ViewHolder holder;
    private OnClickContentItemListener onClickContentItemListener;
    public CenterAdapter(Context mContext, List<HashMap<String,Center>> list) {
        super();
        this.mContext = mContext;
        this.list = list;
    }
    public void setOnClickContentItemListener(OnClickContentItemListener onClickContentItemListener) {
        this.onClickContentItemListener = onClickContentItemListener;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.center_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Map<String, Center> building = list.get(position);

        if (building != null && building.size() != 0) {
            holder.imageView.setImageResource(Integer.parseInt(building.get("image").getId()));
            holder.name.setText(building.get("image").getName());
        }

        return convertView;
    }


    class ViewHolder{
        ImageView imageView;
        TextView name;

    }
}
