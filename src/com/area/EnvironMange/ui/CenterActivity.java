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

        for(int i=0;i<7;i++){
            if(i == 0){
                HashMap<String,Center> map1 = new HashMap<String,Center>();
                map1.put("image", new Center("教学楼区域", String.valueOf(R.drawable.jiaoxuel)));
                imagelist.add(map1);
            }
            if(i == 1){
                HashMap<String,Center> map1 = new HashMap<String,Center>();
                map1.put("image", new Center("教学公共区域", String.valueOf(R.drawable.publicg)));
                imagelist.add(map1);
            }
            if(i == 2){
                HashMap<String,Center> map1 = new HashMap<String,Center>();
                map1.put("image", new Center("宿舍区域", String.valueOf(R.drawable.mine)));
                imagelist.add(map1);
            }
            if(i == 3){
                HashMap<String,Center> map1 = new HashMap<String,Center>();
                map1.put("image", new Center("户外区域\n", String.valueOf(R.drawable.outdoor)));
                imagelist.add(map1);
            }
            if(i == 4){
                HashMap<String,Center> map1 = new HashMap<String,Center>();
                map1.put("image", new Center("查询未提交成绩", String.valueOf(R.drawable.todo)));
                imagelist.add(map1);
            }
            if(i == 5){
                HashMap<String,Center> map1 = new HashMap<String,Center>();
                map1.put("image", new Center("查询历史成绩", String.valueOf(R.drawable.selecticon)));
                imagelist.add(map1);
            }
            if(i == 6){
                HashMap<String,Center> map1 = new HashMap<String,Center>();
                map1.put("image", new Center("修改个人信息", String.valueOf(R.drawable.seticon)));
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
                    Intent intent = new Intent( CenterActivity.this, JiaoxuelouActivity.class);
                    intent.putExtra("name","教学楼区域");
                    startActivity(intent);
                }
                if(position == 1){
                    Intent intent = new Intent( CenterActivity.this, JiaoxuepublicActivity.class);
                    intent.putExtra("name","教学公共区域");
                    startActivity(intent);
                }
                if(position == 2){
                    Intent intent = new Intent( CenterActivity.this, SusheActivity.class);
                    intent.putExtra("name","宿舍区域");
                    startActivity(intent);
                }
                if(position == 3){
                    Intent intent = new Intent( CenterActivity.this, OutdoorActivity.class);
                    intent.putExtra("name","户外区域");
                    startActivity(intent);
                }
                if(position == 4){
                    Intent intent = new Intent( CenterActivity.this, SelectUndoActivity.class);
                    startActivity(intent);
                }
                if(position == 5){
                    Intent intent = new Intent( CenterActivity.this, IndexActivity.class);
                    startActivity(intent);
                }
                if(position == 6){
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
