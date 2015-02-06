package com.area.EnvironMange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.area.EnvironMange.R;
import com.area.EnvironMange.model.Person;
import com.area.EnvironMange.model.SanitationAreaAssessment;
import com.area.EnvironMange.util.DateUtil;

import java.util.List;

/**
 * Created by liuzwei on 2014/12/9.
 */
public class PersonAdapter extends BaseAdapter {
    private Context mContext;
    private List<Person> list;
    private ViewHolder holder;

    public PersonAdapter(Context mContext, List<Person> list) {
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
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.person_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.tele = (TextView) convertView.findViewById(R.id.tele);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Person person = list.get(position);
        holder.name.setText(person.getXm());
        holder.tele.setText(person.getTel());
        return convertView;
    }

    class ViewHolder {
        TextView name;
        TextView tele;
    }
}
