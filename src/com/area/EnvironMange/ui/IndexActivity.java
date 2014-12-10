package com.area.EnvironMange.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import com.area.EnvironMange.R;
import com.area.EnvironMange.adapter.BuildingAdapter;
import com.area.EnvironMange.adapter.MyBuildScoreAdapter;
import com.area.EnvironMange.base.BaseActivity;
import com.area.EnvironMange.model.Building;
import com.area.EnvironMange.model.MyBuildScore;

import java.util.ArrayList;
import java.util.List;

/**
 * author: ${zhanghailong}
 * Date: 2014/12/10
 * Time: 14:29
 * 类的功能、说明写在此处.
 */
public class IndexActivity extends BaseActivity implements View.OnClickListener {
    private List<MyBuildScore> list = new ArrayList<MyBuildScore>();
    private ListView listView;
    private MyBuildScoreAdapter adapter;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mybuildscore_layout);
        initView();
        list.add(new MyBuildScore("E301","100分","2014-12-10"));
        list.add(new MyBuildScore("E302","80分","2014-12-10"));
        list.add(new MyBuildScore("E303","90分","2014-12-10"));
        list.add(new MyBuildScore("E304","100分","2014-12-10"));
        list.add(new MyBuildScore("E305","90分","2014-12-10"));
        list.add(new MyBuildScore("E306","70分","2014-12-10"));
        list.add(new MyBuildScore("E307","100分","2014-12-10"));
        list.add(new MyBuildScore("E308","10分","2014-12-10"));
        list.add(new MyBuildScore("E309","100分","2014-12-10"));
        list.add(new MyBuildScore("E3010","100分","2014-12-10"));
        list.add(new MyBuildScore("E3021","80分","2014-12-10"));
        list.add(new MyBuildScore("E3030","90分","2014-12-10"));
        list.add(new MyBuildScore("E3040","100分","2014-12-10"));
        list.add(new MyBuildScore("E3050","90分","2014-12-10"));
        list.add(new MyBuildScore("E3060","70分","2014-12-10"));
        list.add(new MyBuildScore("E3071","100分","2014-12-10"));
        list.add(new MyBuildScore("E3080","10分","2014-12-10"));
        list.add(new MyBuildScore("E3094","100分","2014-12-10"));
        adapter.notifyDataSetChanged();

    }

    private void initView() {
        listView = (ListView) findViewById(R.id.building_lsv);
        adapter = new MyBuildScoreAdapter(mContext, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //点击某一项时触发
//                Intent floor = new Intent(IndexActivity.this,FloorSelectActivity.class);
//                startActivity(floor);
            }
        });
        back = (ImageView) this.findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
    //弹出顶部主菜单
    public void onTopMenuPopupButtonClick(View view){
        mainPopMenu.showAsDropDown(view);
    }
}
