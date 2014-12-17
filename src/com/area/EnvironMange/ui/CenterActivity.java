package com.area.EnvironMange.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.area.EnvironMange.R;
import com.area.EnvironMange.adapter.CenterAdapter;
import com.area.EnvironMange.base.ActivityTack;
import com.area.EnvironMange.base.BaseActivity;
import com.area.EnvironMange.model.Center;
import com.area.EnvironMange.util.PhoneEnvUtil;

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

    private TextView name;
    private TextView set;
    String namestr;
    private ImageView setimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.center_layout);
        initView();
        namestr = getGson().fromJson(sp.getString("username", ""), String.class);
        for(int i=0;i<7;i++){
            if(i == 0){
                HashMap<String,Center> map1 = new HashMap<String,Center>();
                map1.put("image", new Center("教学楼区域", String.valueOf(R.drawable.center1)));
                imagelist.add(map1);
            }
            if(i == 1){
                HashMap<String,Center> map1 = new HashMap<String,Center>();
                map1.put("image", new Center("教学公共区域", String.valueOf(R.drawable.center4)));
                imagelist.add(map1);
            }
            if(i == 2){
                HashMap<String,Center> map1 = new HashMap<String,Center>();
                map1.put("image", new Center("宿舍区域", String.valueOf(R.drawable.center2)));
                imagelist.add(map1);
            }
            if(i == 3){
                HashMap<String,Center> map1 = new HashMap<String,Center>();
                map1.put("image", new Center("户外区域\n", String.valueOf(R.drawable.center3)));
                imagelist.add(map1);
            }
            if(i == 4){
                HashMap<String,Center> map1 = new HashMap<String,Center>();
                map1.put("image", new Center("查询未提交成绩", String.valueOf(R.drawable.center7)));
                imagelist.add(map1);
            }
            if(i == 5){
                HashMap<String,Center> map1 = new HashMap<String,Center>();
                map1.put("image", new Center("查询历史成绩", String.valueOf(R.drawable.center5)));
                imagelist.add(map1);
            }
            if(i == 6){
                HashMap<String,Center> map1 = new HashMap<String,Center>();
                map1.put("image", new Center("联系人", String.valueOf(R.drawable.center2)));
                imagelist.add(map1);
            }
        }


        gridView = (GridView) this.findViewById(R.id.detail_picture);
        adapter = new CenterAdapter(this ,imagelist);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!PhoneEnvUtil.isNetworkConnected(mContext)){
                    Toast.makeText(mContext, R.string.check_network, Toast.LENGTH_SHORT).show();
                    return;
                }
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
                if(position == 6){//联系人
                    Intent intent = new Intent( CenterActivity.this, PersonsActivity.class);
                    startActivity(intent);
                }
            }
        });

        name.setText("上午好!"+namestr+"老师");

    }

    private void initView() {
        set = (TextView) this.findViewById(R.id.set);
        set.setOnClickListener(this);
        name = (TextView) this.findViewById(R.id.name);
        setimg = (ImageView) this.findViewById(R.id.setimg);
        setimg.setOnClickListener(this);
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
                ActivityTack.getInstanse().exit(mContext);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.set:
                Intent intent = new Intent( CenterActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.setimg:
                Intent set = new Intent( CenterActivity.this, SettingActivity.class);
                startActivity(set);
                break;
        }
    }
}
