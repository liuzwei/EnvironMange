package com.area.EnvironMange.ui;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.area.EnvironMange.R;
import com.area.EnvironMange.adapter.OnClickContentItemListener;
import com.area.EnvironMange.adapter.SelectUndoAdapter;
import com.area.EnvironMange.base.BaseActivity;
import com.area.EnvironMange.model.MyBuildScore;
import com.area.EnvironMange.util.SystemExitUtil;
import com.area.EnvironMange.widget.UpdateScoreDialog;
import com.area.EnvironMange.widget.SaveScoreDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * author: ${zhanghailong}
 * Date: 2014/12/10
 * Time: 14:29
 * 类的功能、说明写在此处.
 */
public class SelectUndoActivity extends BaseActivity implements View.OnClickListener, OnClickContentItemListener {
    private List<MyBuildScore> list = new ArrayList<MyBuildScore>();
    private ListView listView;
    private SelectUndoAdapter adapter;
    private ImageView back;
    private ImageView saveall;//一键提交
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectundo_layout);
        initView();

//        SelectTimeDialog dialog = new SelectTimeDialog( myBuildScore , this, R.style.dialog1);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.show();

        list.add(new MyBuildScore("办公楼 E301","100分","2014-12-10 10:20"));
        list.add(new MyBuildScore("办公楼 E302","80分","2014-12-10 10:20"));
        list.add(new MyBuildScore("办公楼 E303","90分","2014-12-10 10:20"));
        list.add(new MyBuildScore("办公楼 E304","100分","2014-12-10 10:20"));
        list.add(new MyBuildScore("办公楼 E305","90分","2014-12-10 10:20"));
        list.add(new MyBuildScore("办公楼 E306","70分","2014-12-10 10:20"));
        list.add(new MyBuildScore("办公楼 E307","100分","2014-12-10 10:20"));
        list.add(new MyBuildScore("办公楼 E308","10分","2014-12-10 10:20"));
        list.add(new MyBuildScore("办公楼 E309","100分","2014-12-10 10:20"));
        list.add(new MyBuildScore("办公楼 E3010","100分","2014-12-10 10:20"));
        list.add(new MyBuildScore("音乐楼 E3021","80分","2014-12-10 10:20"));
        list.add(new MyBuildScore("音乐楼 E3030","90分","2014-12-10 10:20"));
        list.add(new MyBuildScore("音乐楼 E3040","100分","2014-12-10 10:20"));
        list.add(new MyBuildScore("音乐楼 E3050","90分","2014-12-10 10:20"));
        list.add(new MyBuildScore("音乐楼 E3060","70分","2014-12-10 10:20"));
        list.add(new MyBuildScore("音乐楼 E3071","100分","2014-12-10 10:20"));
        list.add(new MyBuildScore("音乐楼 E3080","10分","2014-12-10 10:20"));
        list.add(new MyBuildScore("音乐楼 E3094","100分","2014-12-10 10:20"));
        adapter.notifyDataSetChanged();

        SystemExitUtil.getInstance().addActivity(this);
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.building_lsv);
        adapter = new SelectUndoAdapter(mContext, list);
        adapter.setOnClickContentItemListener(this);
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
        saveall = (ImageView) this.findViewById(R.id.saveall);
        saveall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.saveall:
                SaveScoreDialog dialog = new SaveScoreDialog( myBuildScore , this, R.style.dialog1);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
                break;
        }
    }
    //弹出顶部主菜单
    public void onTopMenuPopupButtonClick(View view){
        mainPopMenu.showAsDropDown(view);
    }
    MyBuildScore myBuildScore = null;
    @Override
    public void onClickContentItem(int position, int flag, final Object object) {
        myBuildScore = list.get(position);
        switch (flag){
            case 1:
//                SaveScoreDialog dialog = new SaveScoreDialog( myBuildScore , this, R.style.dialog1);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.show();

                UpdateScoreDialog dialog = new UpdateScoreDialog( myBuildScore , this, R.style.dialog1);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
                break;
            case 2:
                Toast.makeText(mContext, "提交成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
