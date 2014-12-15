package com.area.EnvironMange.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import com.area.EnvironMange.R;
import com.area.EnvironMange.model.SanitaionAreaProject;

import java.util.List;

/**
 * Created by liuzwei on 2014/12/15.
 */
public class AreaTypeAdapter extends BaseAdapter {
    private List<SanitaionAreaProject> list;
    private Context mContext;
    private ViewHolder holder;
    private static boolean isGo = true;

    public AreaTypeAdapter(List<SanitaionAreaProject> list, Context mContext) {
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
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.score_item, null);
            holder.aretype = (TextView) convertView.findViewById(R.id.score_item_type);
            holder.maxScore = (EditText) convertView.findViewById(R.id.score_item_score);
            holder.reduceReason = (EditText) convertView.findViewById(R.id.score_item_reason);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        SanitaionAreaProject project = list.get(position);
        holder.aretype.setText(project.getXmmc());
        holder.maxScore.setHint("最大分数:"+project.getZdfs());
        if (isGo) {
            holder.maxScore.setId(1000 + position);
            holder.reduceReason.setId(2000 + position);
        }
        if (position == list.size()-1){
            isGo = false;
        }

        return convertView;
    }
    class ViewHolder
    {
        TextView aretype;//检查项目名称
        EditText maxScore;//最大分数
        EditText reduceReason;//扣分原因
    }
}
