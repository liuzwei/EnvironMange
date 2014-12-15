package com.area.EnvironMange.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.area.EnvironMange.R;
import com.area.EnvironMange.adapter.AreaTypeAdapter;
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
import java.util.HashMap;
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
    private ListView listView;
    private AreaTypeAdapter areaTypeAdapter;
    private TextView title;
    private List<SanitaionAreaProject> list  =new ArrayList<SanitaionAreaProject>();
    private String titleName;

    //定义一个HashMap，用来存放EditText的值，Key是position
    HashMap<Integer, String> scoreMap = new HashMap<Integer, String>();
    HashMap<Integer, String> reasonMap = new HashMap<Integer, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_layout);
        areaID = getIntent().getStringExtra("areaID");
        titleName = getIntent().getStringExtra("titleName");
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
        listView = (ListView) findViewById(R.id.score_layout_lsv);
        areaTypeAdapter = new AreaTypeAdapter(list, mContext, scoreMap, reasonMap);
        listView.setAdapter(areaTypeAdapter);
        title = (TextView) this.findViewById(R.id.score_layout_title);
        title.setText(titleName);
        listView.requestFocus();
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                listView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.sub:
                JSONArray array = new JSONArray();
                for (int i=0; i<list.size(); i++) {
                    SanitaionAreaProject project = list.get(i);
//                    EditText score = (EditText) this.findViewById(1000+i);
                    if (StringUtil.isNullOrEmpty(scoreMap.get(i))){
                        Toast.makeText(mContext, project.getXmmc()+" 没有打分", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Double.parseDouble(scoreMap.get(i)) > Integer.parseInt(project.getZdfs())){
                        Toast.makeText(mContext, project.getXmmc()+"不能超过"+project.getZdfs()+"分", Toast.LENGTH_SHORT).show();
                        return;
                    }
//                    EditText reason = (EditText) this.findViewById(2000+i);
                    SanitaionAreaAssementItem item = new SanitaionAreaAssementItem(project.getID(), Float.parseFloat(scoreMap.get(i)), reasonMap.get(i));
                    array.put(SanitaionAreaAssementItem.fromObject2Json(item));
                }
                try {
                    //提交分数
                    saveProject(array, InternetURL.COMMIT_AREA_SCORE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.save:
                JSONArray array2 = new JSONArray();
                for (int i=0; i<list.size(); i++) {
                    SanitaionAreaProject project = list.get(i);
                    EditText score = (EditText) this.findViewById(1000+i);
                    if (StringUtil.isNullOrEmpty(score.getText().toString())){
                        Toast.makeText(mContext, project.getXmmc()+" 没有打分", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Double.parseDouble(score.getText().toString()) > Integer.parseInt(project.getZdfs())){
                        Toast.makeText(mContext, project.getXmmc()+"不能超过"+project.getZdfs()+"分", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    EditText reason = (EditText) this.findViewById(2000+i);
                    SanitaionAreaAssementItem item = new SanitaionAreaAssementItem(project.getID(), Float.parseFloat(score.getText().toString()), reason.getText().toString());
                    array2.put(SanitaionAreaAssementItem.fromObject2Json(item));
                }
                try {
                    //提交分数
                    saveProject(array2, InternetURL.SAVE_AREA_SCORE);
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
                                list.add(getGson().fromJson(array.getJSONObject(i).toString(), SanitaionAreaProject.class));
                            }
                            areaTypeAdapter.notifyDataSetChanged();
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

    private void saveProject(JSONArray array, String url) throws JSONException, UnsupportedEncodingException {
        String userid = getGson().fromJson(sp.getString("username", ""), String.class);
        JSONObject object = new JSONObject();
        object.put("item", array);
        object.put("userid",userid);
        object.put("areaID", areaID);
        object.put("bz", beizhu.getText().toString());

        StringEntity entity = new StringEntity(object.toString());
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
                            mContext.sendBroadcast(intent);
                          finish();
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
