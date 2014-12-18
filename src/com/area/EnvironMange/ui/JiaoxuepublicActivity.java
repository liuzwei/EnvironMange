package com.area.EnvironMange.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.area.EnvironMange.R;
import com.area.EnvironMange.adapter.TeachBuildingAreaAdapter;
import com.area.EnvironMange.base.BaseActivity;
import com.area.EnvironMange.base.Constants;
import com.area.EnvironMange.common.InternetURL;
import com.area.EnvironMange.model.Building;
import com.area.EnvironMange.model.SanitationArea;
import com.area.EnvironMange.widget.BeizhuDialog;
import net.tsz.afinal.http.AjaxCallBack;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * author: ${zhanghailong}
 * Date: 2014/12/12
 * Time: 15:17
 * 类的功能、说明写在此处.
 */
public class JiaoxuepublicActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = JiaoxuelouActivity.class.getSimpleName();

    private Spinner teachBuilding;//选择教学楼
    private List<Building> buildingList = new ArrayList<Building>();//取回来的建筑物数据存放在这
    private List<String> buildingNames = new ArrayList<String>();//存放建筑物的名字

    ArrayAdapter<String> teachBuildingAdapter ;

    private ListView areaListView;
    private TeachBuildingAreaAdapter areaAdapter;//展示某楼层的区域
    private List<SanitationArea> areaList = new ArrayList<SanitationArea>();
    private String buildingName;
    private String buildingID;
    private ImageView back;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiaoxuepublic);
        initView();
        getBuildings();
        registerBoradcastReceiver();
    }

    private void initView() {
        back = (ImageView) this.findViewById(R.id.back);
        back.setOnClickListener(this);
        teachBuildingAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, buildingNames);
        teachBuildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teachBuilding = (Spinner) findViewById(R.id.province);
        teachBuilding.setAdapter(teachBuildingAdapter);
        teachBuilding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, position + "");
                if (position>0) {
                    areaList.clear();
                    areaAdapter.notifyDataSetChanged();
                    Building building = buildingList.get(position-1);
                    try {
                        //获取选中宿舍楼的区域
                        getArea(building.getID());
                        buildingName = building.getMC();
                        buildingID = building.getID();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    areaList.clear();
                    areaAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        areaListView = (ListView) this.findViewById(R.id.jiaoxuelou_lsv);
        areaAdapter = new TeachBuildingAreaAdapter(areaList, mContext);
        areaListView.setAdapter(areaAdapter);

        areaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取检查项目
                SanitationArea area =  areaList.get(position);
                try {
                    checkIsScore(area);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.contentsliner:

                break;
            case R.id.titleitem:
                Intent score = new Intent(this, ScoreActivity.class);
                startActivity(score);
                break;
            case R.id.beizhu:
                BeizhuDialog dialog = new BeizhuDialog(this, R.style.dialog1);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
                break;
        }
    }

    //弹出顶部主菜单
    public void onTopMenuPopupButtonClick(View view){
        mainPopMenu.showAsDropDown(view);
    }

    /**
     * 获取建筑物信息
     */
    private void getBuildings(){
        getFinalHttp().post(
                InternetURL.GET_BUILDING_URL,
                new AjaxCallBack<Object>(){
                    @Override
                    public void onSuccess(Object o) {
                        try {
                            JSONArray array = new JSONArray(o.toString());
                            for (int i=0; i<array.length(); i++){
                                Building building = getGson().fromJson(String.valueOf(array.getJSONObject(i)), Building.class);
                                buildingList.add(building);
                                if (i==0){
                                    buildingNames.add("请选择教学楼楼");
                                }
                                buildingNames.add(building.getMC());
                            }
                            //取回来的宿舍楼信息, 根据宿舍楼数据封装要显示的信息
                            teachBuildingAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        super.onSuccess(o);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                    }
                });
    }

    /**
     *  获取某个教学楼的公共卫生区域
     * @param buildingID  建筑物ID
     */
    private void getArea(String buildingID) throws JSONException, UnsupportedEncodingException {
        JSONObject object = new JSONObject();
        object.put("buildingID", buildingID);
        StringEntity entity = new StringEntity(object.toString());

        getFinalHttp().post(
                InternetURL.GET_TEACH_PUBLIC_URL,
                entity,
                "application/json; charset=utf-8",
                new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        super.onSuccess(o);
                        try {
                            JSONArray array = new JSONArray(o.toString());
                            for (int i=0; i<array.length(); i++){
                                areaList.add(getGson().fromJson(array.getJSONObject(i).toString(), SanitationArea.class));
                            }
                            areaAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                    }
                }
        );
    }

    public void registerBoradcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constants.BROADCAST);
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Constants.BROADCAST)){
                boolean isSave = intent.getBooleanExtra("isSave", true);
                if (isSave) {
                    Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mContext, "提交成功", Toast.LENGTH_SHORT).show();
                }
            }
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    /**
     * 检查该区域是否可以打分
     * @param area
     * @throws UnsupportedEncodingException
     * @throws JSONException
     */
    private void  checkIsScore(final SanitationArea area) throws UnsupportedEncodingException, JSONException {
        JSONObject object = new JSONObject();
        object.put("areaid", area.getID());
        StringEntity entity = new StringEntity(object.toString());

        getFinalHttp().post(
                InternetURL.IS_CAN_DF,
                entity,
                "application/json; charset=utf-8",
                new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        super.onSuccess(o);
                        if ("true".equals(o.toString())){
                            Intent score = new Intent(JiaoxuepublicActivity.this, ScoreActivity.class);
                            score.putExtra("areaID", area.getID());
                            score.putExtra("pbid", area.getPbid());
                            score.putExtra("titleName", buildingName + " " + area.getMc() +"  卫生打分");
                            startActivity(score);
                        }else {
                            Toast.makeText(mContext, R.string.not_df, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                    }
                }
        );
    }
}

