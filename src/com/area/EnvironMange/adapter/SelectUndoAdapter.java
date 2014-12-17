package com.area.EnvironMange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.area.EnvironMange.R;
import com.area.EnvironMange.model.MyBuildScore;
import com.area.EnvironMange.model.SanitationAreaAssessment;
import com.area.EnvironMange.util.DateUtil;

import java.util.List;

/**
 * Created by liuzwei on 2014/12/9.
 */
public class SelectUndoAdapter extends BaseAdapter  {
    private Context mContext;
    private List<SanitationAreaAssessment> list;
    private ViewHolder holder;
    public SelectUndoAdapter(Context mContext, List<SanitationAreaAssessment> list) {
        this.mContext = mContext;
        this.list = list;
    }
    private OnClickContentItemListener onClickContentItemListener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.selectundo_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.score = (TextView) convertView.findViewById(R.id.score);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.update = (TextView) convertView.findViewById(R.id.update);
            holder.save = (TextView) convertView.findViewById(R.id.save);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        SanitationAreaAssessment assessment = list.get(position);
        holder.name.setText(assessment.getAreamc());
        holder.score.setText(assessment.getFs());
        holder.time.setText(DateUtil.getUndoDate(assessment.getJcsj()));
        holder.update.setOnClickListener(new View.OnClickListener() {
           //修改
            @Override
            public void onClick(View v) {
                onClickContentItemListener.onClickContentItem(position, 1, null);
            }
        });
        holder.save.setOnClickListener(new View.OnClickListener() {
            //提交
            @Override
            public void onClick(View v) {
                onClickContentItemListener.onClickContentItem(position, 2, null);
            }
        });
        return convertView;
    }

    class ViewHolder{
        TextView name;
        TextView score;
        TextView time;
        TextView update;
        TextView save;
    }
}
