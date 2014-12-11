package com.area.EnvironMange.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.area.EnvironMange.MainActivity;
import com.area.EnvironMange.R;
import com.area.EnvironMange.adapter.CenterAdapter;
import com.area.EnvironMange.base.BaseActivity;
import com.area.EnvironMange.model.Center;
import com.area.EnvironMange.util.SystemExitUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * author: ${zhanghailong}
 * Date: 2014/12/10
 * Time: 15:25
 * 类的功能、说明写在此处.
 */
public class CenterActivity extends BaseActivity implements View.OnClickListener {
    private GridView gridView; //展示图片
    CenterAdapter adapter;
    private long touchTime = 0;
    private long waitTime = 2000;
    List<HashMap<String,Center>> imagelist = new ArrayList<HashMap<String,Center>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.center_layout);

        for(int i=0;i<3;i++){
            if(i == 0){
                HashMap<String,Center> map1 = new HashMap<String,Center>();
                map1.put("image", new Center("打分", String.valueOf(R.drawable.indexicon)));
                imagelist.add(map1);
            }
            if(i == 1){
                HashMap<String,Center> map1 = new HashMap<String,Center>();
                map1.put("image", new Center("查询", String.valueOf(R.drawable.selecticon)));
                imagelist.add(map1);
            }
            if(i == 2){
                HashMap<String,Center> map1 = new HashMap<String,Center>();
                map1.put("image", new Center("设置", String.valueOf(R.drawable.seticon)));
                imagelist.add(map1);
            }
        }


        gridView = (GridView) this.findViewById(R.id.detail_picture);
        adapter = new CenterAdapter(this ,imagelist);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent intent = new Intent( CenterActivity.this, MainBuildingsActivity.class);
                    startActivity(intent);
                }
                if(position == 1){
                    Intent intent = new Intent( CenterActivity.this, IndexActivity.class);
                    startActivity(intent);
                }
                if(position == 2){
                    Intent intent = new Intent( CenterActivity.this, SettingActivity.class);
                    startActivity(intent);
                }
            }
        });

        SystemExitUtil.getInstance().addActivity(this);
    }
    //再摁退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode){
            long currentTime = System.currentTimeMillis();
            if ((currentTime - touchTime) >= waitTime){
                alert(R.string.exit_str);
                touchTime = currentTime;
            }else {
                SystemExitUtil.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
