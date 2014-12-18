package com.area.EnvironMange.ui;

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
import com.area.EnvironMange.model.SanitaionAreaProject;
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
 * author: ${zhanghailong}
 * Date: 2014/12/10
 * Time: 12:39
 * 类的功能、说明写在此处.
 */
public class ScoreActivity extends BaseActivity implements View.OnClickListener {
    private Button sub;
    private ImageView back;
    private Button save;
    private String areaID;
    private EditText beizhu;//备注
    private TextView title;
    private List<SanitaionAreaProject> list  =new ArrayList<SanitaionAreaProject>();
    private String titleName;
    private LinearLayout projectLayout;
    private String pbid;//排班ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_layout);
        areaID = getIntent().getStringExtra("areaID");
        titleName = getIntent().getStringExtra("titleName");
        pbid = getIntent().getStringExtra("pbid");

        initView();

        try {
            getAreaType(areaID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        sub = (Button) this.findViewById(R.id.sub);
        sub.setOnClickListener(this);
        back = (ImageView) this.findViewById(R.id.back);
        back.setOnClickListener(this);
        save = (Button) this.findViewById(R.id.save);
        save.setOnClickListener(this);

        beizhu = (EditText) this.findViewById(R.id.score_layout_beizhu);
        projectLayout = (LinearLayout) findViewById(R.id.score_project_layout);
        title = (TextView) this.findViewById(R.id.score_layout_title);
        title.setText(titleName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.sub:
                JSONArray array = new JSONArray();
                for (int i=0; i<list.size(); i++) {
                    EditText score = (EditText) this.findViewById(1000+i);
                    EditText reason = (EditText) this.findViewById(2000+i);
                    SanitaionAreaProject project = list.get(i);
                    if (StringUtil.isNullOrEmpty(score.getText().toString())){
                        Toast.makeText(mContext, project.getXmmc()+" 没有打分", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Double.parseDouble(score.getText().toString()) > Double.parseDouble((project.getZdfs()))){
                        Toast.makeText(mContext, project.getXmmc()+"不能超过"+project.getZdfs()+"分", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    SanitaionAreaAssementItem item = new SanitaionAreaAssementItem(project.getID(), Float.parseFloat(score.getText().toString()), reason.getText().toString());
                    array.put(SanitaionAreaAssementItem.fromObject2Json(item));
                }
                try {
                    //提交分数
                    saveProject(array, InternetURL.COMMIT_AREA_SCORE, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.save:
                JSONArray array2 = new JSONArray();
                for (int i=0; i<list.size(); i++) {
                    EditText score = (EditText) this.findViewById(1000+i);
                    EditText reason = (EditText) this.findViewById(2000+i);
                    SanitaionAreaProject project = list.get(i);
                    if (StringUtil.isNullOrEmpty(score.getText().toString())){
                        Toast.makeText(mContext, project.getXmmc()+" 没有打分", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Double.parseDouble(score.getText().toString()) > Double.parseDouble((project.getZdfs()))){
                        Toast.makeText(mContext, project.getXmmc()+"不能超过"+project.getZdfs()+"分", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    SanitaionAreaAssementItem item = new SanitaionAreaAssementItem(project.getID(), Float.parseFloat(score.getText().toString()), reason.getText().toString());
                    array2.put(SanitaionAreaAssementItem.fromObject2Json(item));
                }
                try {
                    //保存分数
                    saveProject(array2, InternetURL.SAVE_AREA_SCORE, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }
    //弹出顶部主菜单
    public void onTopMenuPopupButtonClick(View view){
        mainPopMenu.showAsDropDown(view);
    }

    private void getAreaType(String areaID) throws JSONException, UnsupportedEncodingException {
        JSONObject object = new JSONObject();
        object.put("areaID", areaID);
        StringEntity entity = new StringEntity(object.toString());

        getFinalHttp().post(
                InternetURL.GET_AREA_TYPES,
                entity,
                "application/json; charset=utf-8",
                new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        super.onSuccess(o);
                        try {
                            JSONArray array = new JSONArray(o.toString());
                            for (int i=0; i<array.length(); i++){
                                SanitaionAreaProject project = getGson().fromJson(array.getJSONObject(i).toString(), SanitaionAreaProject.class);
                                list.add(getGson().fromJson(array.getJSONObject(i).toString(), SanitaionAreaProject.class));
                                LinearLayout layout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.score_item, null);
                                TextView scoreType = (TextView) layout.findViewById(R.id.score_item_type);
                                EditText score = (EditText) layout.findViewById(R.id.score_item_score);
                                EditText scoreReason = (EditText) layout.findViewById(R.id.score_item_reason);
                                scoreType.setText(project.getXmmc());
                                score.setHint("最大分数："+project.getZdfs());
                                score.setId(1000+i);
                                scoreReason.setId(2000+i);
                                projectLayout.addView(layout);
                            }
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

    private void saveProject(JSONArray array, String url, final boolean isSave) throws JSONException, UnsupportedEncodingException {
        String userid = getGson().fromJson(sp.getString("userid", ""), String.class);
        JSONObject object = new JSONObject();
        object.put("item", array);
        object.put("userid",userid);
        object.put("areaID", areaID);
        object.put("pbid", pbid);
        object.put("bz", beizhu.getText().toString());

        StringEntity entity = new StringEntity(object.toString(), "utf-8");
        getFinalHttp().post(
                url,
                entity,
                "application/json; charset=utf-8",
                new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        super.onSuccess(o);
                        if (o.toString().equals("true")){
                            Intent intent = new Intent(Constants.BROADCAST);
                            intent.putExtra("isSave", isSave);
                            mContext.sendBroadcast(intent);
                          finish();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                        Toast.makeText(mContext, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }


}
