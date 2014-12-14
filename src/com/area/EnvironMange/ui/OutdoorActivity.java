package com.area.EnvironMange.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.area.EnvironMange.R;
import com.area.EnvironMange.adapter.BuildingAdapter;
import com.area.EnvironMange.base.BaseActivity;
import com.area.EnvironMange.common.InternetURL;
import com.area.EnvironMange.model.Building;
import com.area.EnvironMange.util.SystemExitUtil;
import net.tsz.afinal.http.AjaxCallBack;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * author: ${zhanghailong}
 * Date: 2014/12/12
 * Time: 15:17
 * 类的功能、说明写在此处.
 */
public class OutdoorActivity extends BaseActivity implements View.OnClickListener{
    private List<Building> list = new ArrayList<Building>();
    private ListView listView;
    private BuildingAdapter adapter;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.building_layout);
        initView();
        getBuildings();
        SystemExitUtil.getInstance().addActivity(this);
    }

    private void initView(){
        listView = (ListView) findViewById(R.id.building_lsv);
        adapter = new BuildingAdapter(mContext, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击某一项时触发
//                Intent floor = new Intent(OutdoorActivity.this,FloorSelectActivity.class);
//                startActivity(floor);
                Intent score = new Intent(OutdoorActivity.this, ScoreActivity.class);
                startActivity(score);
            }
        });
        back = (ImageView) this.findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    private void getBuildings(){
//        getFinalHttp().get(
//                InternetURL.GET_BUILDING_URL,
//                new AjaxCallBack<Object>() {
//                    @Override
//                    public void onSuccess(Object o) {
//                        try {
//                            JSONArray array = new JSONArray(o.toString());
//                            for (int i=0; i<array.length(); i++){
//                                JSONObject object = array.getJSONObject(i);
//                                list.add(Building.jsonObject2Object(object));
//                            }
//                            adapter.notifyDataSetChanged();
//                            Log.i("Main", array.toString());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        super.onSuccess(o);
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t, int errorNo, String strMsg) {
//                        super.onFailure(t, errorNo, strMsg);
//                    }
//                }
//        );
        list.add(new Building("1H", "宿舍1号楼周边及其绿化带"));
        list.add(new Building("1H", "1号楼北侧树林"));
        list.add(new Building("1H", "东操场周边及其绿化带"));

        adapter.notifyDataSetChanged();
    }
    //弹出顶部主菜单
    public void onTopMenuPopupButtonClick(View view){
        mainPopMenu.showAsDropDown(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back:
                finish();
                break;
        }
    }
}

