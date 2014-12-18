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
import com.area.EnvironMange.adapter.BuildingAdapter;
import com.area.EnvironMange.adapter.TeachBuildingAreaAdapter;
import com.area.EnvironMange.base.BaseActivity;
import com.area.EnvironMange.base.Constants;
import com.area.EnvironMange.common.InternetURL;
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
public class OutdoorActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = JiaoxuelouActivity.class.getSimpleName();

    private ListView areaListView;
    private TeachBuildingAreaAdapter areaAdapter;//展示某楼层的区域
    private List<SanitationArea> areaList = new ArrayList<SanitationArea>();
    private ImageView back;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.building_layout);
        initView();
        registerBoradcastReceiver();
        try {
            getArea();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        back = (ImageView) this.findViewById(R.id.back);
        back.setOnClickListener(this);



        areaListView = (ListView) this.findViewById(R.id.building_lsv);
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
     *  获取户外区域
     */
    private void getArea() throws JSONException, UnsupportedEncodingException {
        JSONObject object = new JSONObject();
        StringEntity entity = new StringEntity(object.toString());

        getFinalHttp().post(
                InternetURL.GET_OUTDOOR_AREA_URL,
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
                            Intent score = new Intent(OutdoorActivity.this, ScoreActivity.class);
                            score.putExtra("areaID", area.getID());
                            score.putExtra("titleName",   area.getMc() +"  卫生打分");
                            score.putExtra("pbid", area.getPbid());
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

