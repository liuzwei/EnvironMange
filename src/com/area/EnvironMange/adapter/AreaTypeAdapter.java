package com.area.EnvironMange.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import com.area.EnvironMange.R;
import com.area.EnvironMange.model.SanitaionAreaProject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by liuzwei on 2014/12/15.
 */
public class AreaTypeAdapter extends BaseAdapter {
    private List<SanitaionAreaProject> list;
    private Context mContext;
    private ViewHolder holder;
    private static boolean isGo = true;
    private HashMap<Integer, String> scoreMap;
    private HashMap<Integer, String> reasonMap;


    public AreaTypeAdapter(List<SanitaionAreaProject> list, Context mContext, HashMap<Integer, String> scoreMap, HashMap<Integer, String> reasonMap) {
        this.list = list;
        this.mContext = mContext;
        this.scoreMap = scoreMap;
        this.reasonMap = reasonMap;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        holder = new ViewHolder();
        convertView = LayoutInflater.from(mContext).inflate(R.layout.score_item, null);
        holder.aretype = (TextView) convertView.findViewById(R.id.score_item_type);
        holder.maxScore = (EditText) convertView.findViewById(R.id.score_item_score);
        holder.reduceReason = (EditText) convertView.findViewById(R.id.score_item_reason);
//        if (convertView == null){
//            holder = new ViewHolder();
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.score_item, null);
//            holder.aretype = (TextView) convertView.findViewById(R.id.score_item_type);
//            holder.maxScore = (EditText) convertView.findViewById(R.id.score_item_score);
//            holder.reduceReason = (EditText) convertView.findViewById(R.id.score_item_reason);
//
//            convertView.setTag(holder);
//        }else {
//            holder = (ViewHolder) convertView.getTag();
//        }
        SanitaionAreaProject project = list.get(position);
        holder.aretype.setText(project.getXmmc());
        holder.maxScore.setHint("最大分数:"+project.getZdfs());

        holder.maxScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                scoreMap.put(position, s.toString());
            }
        });
        holder.reduceReason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                reasonMap.put(position, s.toString());
            }
        });

        return convertView;
    }
    class ViewHolder
    {
        TextView aretype;//检查项目名称
        EditText maxScore;//最大分数
        EditText reduceReason;//扣分原因
    }
}
