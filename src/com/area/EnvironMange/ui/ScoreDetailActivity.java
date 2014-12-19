package com.area.EnvironMange.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.area.EnvironMange.R;
import com.area.EnvironMange.base.BaseActivity;
import com.area.EnvironMange.common.InternetURL;
import com.area.EnvironMange.model.SanitaionAreaAssementItemView;
import net.tsz.afinal.http.AjaxCallBack;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by liuzwei on 2014/12/16.
 */
public class ScoreDetailActivity extends BaseActivity {
    private LinearLayout projectLayout;
    private ImageView back;
    private String beizhuStr;
    private String assessmentID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_detail_layout);

        assessmentID = getIntent().getStringExtra("assessmentID");
        beizhuStr = getIntent().getStringExtra("beizhu");

        initView();

        try {
            getData(assessmentID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView(){
        projectLayout = (LinearLayout) this.findViewById(R.id.detail_project);
        back = (ImageView) this.findViewById(R.id.detail_back);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.detail_back:
                finish();
                break;
        }
    }

    private void getData(String assessmentID) throws JSONException, UnsupportedEncodingException {
        JSONObject object = new JSONObject();
        object.put("assmentid", assessmentID);
        StringEntity entity = new StringEntity(object.toString());

        getFinalHttp().post(
                InternetURL.GET_COMMIT_DETAIL_URL,
                entity,
                "application/json; charset=utf-8",
                new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        super.onSuccess(o);
                        try {
                            JSONArray array = new JSONArray(o.toString());
                            for (int i=0; i<array.length(); i++){
                                SanitaionAreaAssementItemView itemView = getGson().fromJson(array.getJSONObject(i).toString(), SanitaionAreaAssementItemView.class);
                                LinearLayout layout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.detail_item, null);
                                TextView scoreType = (TextView) layout.findViewById(R.id.detail_item_type);
                                TextView score = (TextView) layout.findViewById(R.id.detail_item_score);
                                TextView scoreReason = (TextView) layout.findViewById(R.id.detail_item_reason);
                                scoreType.setText(itemView.getProjectMC());
                                score.setText(itemView.getProjectfs());
                                scoreReason.setText(itemView.getKfyy());
                                projectLayout.addView(layout);
                            }
                            TextView bz = new TextView(mContext);
                            bz.setTextSize(16);
                            bz.setText("备注："+beizhuStr);
                            bz.setTextColor(Color.BLACK);
                            projectLayout.addView(bz);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                        Toast.makeText(mContext, "获取数据失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
