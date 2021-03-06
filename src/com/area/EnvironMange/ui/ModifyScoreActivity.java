package com.area.EnvironMange.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.area.EnvironMange.R;
import com.area.EnvironMange.base.BaseActivity;
import com.area.EnvironMange.base.Constants;
import com.area.EnvironMange.common.InternetURL;
import com.area.EnvironMange.model.SanitaionAreaAssementItem;
import com.area.EnvironMange.model.SanitaionAreaAssementItemView;
import com.area.EnvironMange.util.StringUtil;
import net.tsz.afinal.http.AjaxCallBack;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 修改分数
 * Created by liuzwei on 2014/12/16.
 */
public class ModifyScoreActivity extends BaseActivity {
    private LinearLayout projectLayout;
    private EditText beizhu;
    private Button commit;
    private Button save;
    private ImageView back;
    private List<SanitaionAreaAssementItemView> list = new ArrayList<SanitaionAreaAssementItemView>();
    private String assessmentID;
    private String dafenID;
    private String beizhuStr;//备注
    private String SanitaionreaCleanID;//排班ID


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_score_layout);
        initView();
        assessmentID = getIntent().getStringExtra("assessmentID");
        dafenID = getIntent().getStringExtra("dafenID");
        beizhuStr = getIntent().getStringExtra("beizhu");
        SanitaionreaCleanID = getIntent().getStringExtra("SanitaionreaCleanID");
        try {
            getData(dafenID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        projectLayout = (LinearLayout) this.findViewById(R.id.modify_project_layout);
        beizhu = (EditText) this.findViewById(R.id.modify_layout_beizhu);
        commit = (Button) this.findViewById(R.id.modify_sub);
        back = (ImageView) this.findViewById(R.id.modify_back);

        back.setOnClickListener(this);
        commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.modify_back:
                finish();
                break;
            case R.id.modify_sub:
                JSONArray array = new JSONArray();
                for (int i = 0; i < list.size(); i++) {
                    EditText score = (EditText) this.findViewById(1000 + i);
                    EditText reason = (EditText) this.findViewById(2000 + i);
                    SanitaionAreaAssementItemView itemView = list.get(i);
//                    if (StringUtil.isNullOrEmpty(score.getText().toString())){
//                        Toast.makeText(mContext, project.getProjectMC()+" 没有打分", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    if (Double.parseDouble(score.getText().toString()) > Double.parseDouble((project.getZdfs()))){
//                        Toast.makeText(mContext, project.getProjectMC()+"不能超过"+"分", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
                    SanitaionAreaAssementItem item = new SanitaionAreaAssementItem(itemView.getAsItemID(), itemView.getProjectID(), Float.parseFloat(score.getText().toString()), reason.getText().toString());
                    array.put(SanitaionAreaAssementItem.fromObject2Json(item));
                }
                try {
                    //提交分数
                    saveProject(array, InternetURL.UPDATE_SAVE_FS_URL, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void getData(String assessmentID) throws JSONException, UnsupportedEncodingException {
        JSONObject object = new JSONObject();
        object.put("assmentid", assessmentID);
        StringEntity entity = new StringEntity(object.toString());

        getFinalHttp().post(
                InternetURL.GET_SAVE_DETAIL_URL,
                entity,
                "application/json; charset=utf-8",
                new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        super.onSuccess(o);
                        try {
                            JSONArray array = new JSONArray(o.toString());
                            for (int i = 0; i < array.length(); i++) {
                                SanitaionAreaAssementItemView itemView = getGson().fromJson(array.getJSONObject(i).toString(), SanitaionAreaAssementItemView.class);
                                list.add(itemView);
                                LinearLayout layout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.score_item, null);
                                TextView scoreType = (TextView) layout.findViewById(R.id.score_item_type);
                                EditText score = (EditText) layout.findViewById(R.id.score_item_score);
                                EditText scoreReason = (EditText) layout.findViewById(R.id.score_item_reason);
                                scoreType.setText(itemView.getProjectMC());
                                score.setHint("最大分数：");
                                score.setText(itemView.getProjectfs());
                                score.setId(1000 + i);
                                scoreReason.setId(2000 + i);
                                scoreReason.setText(itemView.getKfyy());
                                projectLayout.addView(layout);
                            }
                            beizhu.setText(beizhuStr);
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

    private void saveProject(JSONArray array, String url, final boolean isSave) throws JSONException, UnsupportedEncodingException {
        String userid = getGson().fromJson(sp.getString("userid", ""), String.class);
        JSONObject object = new JSONObject();
        object.put("item", array);
        object.put("userid", userid);
        object.put("areaID", assessmentID);
        object.put("pbid", SanitaionreaCleanID);
        object.put("bz", beizhu.getText().toString());
        object.put("asmentID", list.get(0).getAreaAssementID());
        if(StringUtil.isNullOrEmpty(SanitaionreaCleanID)){
            Toast.makeText(mContext, R.string.cannotdf, Toast.LENGTH_SHORT).show();
            return;
        }
        StringEntity entity = new StringEntity(object.toString(), "utf-8");
        getFinalHttp().post(
                url,
                entity,
                "application/json; charset=utf-8",
                new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        super.onSuccess(o);
                        if (o.toString().equals("true")) {
                            Intent intent = new Intent(Constants.BROADCAST);
                            intent.putExtra("isUpdate", true);
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        } else {
                            Toast.makeText(mContext, "修改失败,请稍后重试", Toast.LENGTH_SHORT).show();
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
