package com.area.EnvironMange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.area.EnvironMange.R;
import com.area.EnvironMange.model.Building;
import com.area.EnvironMange.model.MyBuildScore;

import java.util.List;

/**
 * Created by liuzwei on 2014/12/9.
 */
public class MyBuildScoreAdapter extends BaseAdapter  {
    private Context mContext;
    private List<MyBuildScore> list;
    private ViewHolder holder;
    private OnClickContentItemListener onClickContentItemListener;
    public MyBuildScoreAdapter(Context mContext, List<MyBuildScore> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.index_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.score = (TextView) convertView.findViewById(R.id.score);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        MyBuildScore building = list.get(position);
        holder.name.setText(building.getName());
        holder.score.setText(building.getScore());
        holder.time.setText(building.getDatetime());
        return convertView;
    }

    class ViewHolder{
        TextView name;
        TextView score;
        TextView time;

    }
}
