package com.area.EnvironMange.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.area.EnvironMange.R;
import com.area.EnvironMange.adapter.OnClickContentItemListener;
import com.area.EnvironMange.adapter.SelectUndoAdapter;
import com.area.EnvironMange.base.BaseActivity;
import com.area.EnvironMange.common.InternetURL;
import com.area.EnvironMange.model.SanitaionAreaAssementItem;
import com.area.EnvironMange.model.SanitaionAreaAssementItemView;
import com.area.EnvironMange.model.SanitationAreaAssessment;
import com.area.EnvironMange.util.DateUtil;
import com.area.EnvironMange.util.XCRoundImageView;
import net.tsz.afinal.http.AjaxCallBack;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * author: ${zhanghailong}
 * Date: 2014/12/10
 * Time: 14:29
 * 类的功能、说明写在此处.
 */
public class SelectUndoActivity extends BaseActivity implements View.OnClickListener, OnClickContentItemListener {
    private List<SanitationAreaAssessment> list = new ArrayList<SanitationAreaAssessment>();
    private ListView listView;
    private SelectUndoAdapter adapter;
    private ImageView back;
    private XCRoundImageView saveall;//一键提交
    private static final int MODIFY_CODE = 102;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectundo_layout);
        initView();

        try {
            getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.building_lsv);
        adapter = new SelectUndoAdapter(mContext, list);
        adapter.setOnClickContentItemListener(this);
        listView.setAdapter(adapter);
        back = (ImageView) this.findViewById(R.id.back);
        back.setOnClickListener(this);
        saveall = (XCRoundImageView) this.findViewById(R.id.saveall);
        saveall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.saveall:
                //todo
//                Toast.makeText(mContext, "未做", Toast.LENGTH_SHORT).show();
                progressDialog = new ProgressDialog(SelectUndoActivity.this);
                progressDialog.setMessage("正在提交");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                for (int i=0; i<list.size(); i++){
                    SanitationAreaAssessment asment = list.get(i);
                    try {
                        getData(asment);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

        }
    }
    //弹出顶部主菜单
    public void onTopMenuPopupButtonClick(View view){
        mainPopMenu.showAsDropDown(view);
    }

    SanitationAreaAssessment assessment = null;
    @Override
    public void onClickContentItem(int position, int flag, final Object object) {
        assessment = list.get(position);
        switch (flag){
            case 1:
                SanitationAreaAssessment asm = list.get(position);
                Intent intent = new Intent(SelectUndoActivity.this, ModifyScoreActivity.class );
                intent.putExtra("assessmentID", asm.getAreaid());
                intent.putExtra("dafenID", asm.getID());
                intent.putExtra("beizhu", asm.getBz());
                startActivityForResult(intent, MODIFY_CODE);
                break;
            case 2://单个提交数据
                SanitationAreaAssessment asment = list.get(position);
                try {
                    progressDialog = new ProgressDialog(SelectUndoActivity.this);
                    progressDialog.setMessage("正在提交");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    getData(asment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 查询保存但没有提交的数据
     * @throws JSONException
     * @throws UnsupportedEncodingException
     */
    private void getData() throws JSONException, UnsupportedEncodingException {
        String userid = getGson().fromJson(sp.getString("userid", ""), String.class);
        JSONObject object = new JSONObject();
        object.put("userid", userid);
        String beginTime = DateUtil.getFormatDateTime(new Date(System.currentTimeMillis()-14*24*60*60*1000), "yyyyMMdd");
        String endTime = DateUtil.getFormatDateTime(new Date(), "yyyyMMdd");
        object.put("begineTime", beginTime);
        object.put("endtime", endTime);

        StringEntity entity = new StringEntity(object.toString(), "utf-8");

        getFinalHttp().post(
                InternetURL.GET_SAVE_FS_URL,
                entity,
                "application/json; charset=utf-8",
                new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        super.onSuccess(o);
                        try {
                            JSONArray array = new JSONArray(o.toString());
                            for (int i=0; i<array.length(); i++){
                                SanitationAreaAssessment asm = getGson().fromJson(String.valueOf(array.getJSONObject(i)), SanitationAreaAssessment.class);
                                list.add(asm);
                            }
                            Collections.sort(list);
                            adapter.notifyDataSetChanged();
                            if (array.length() == 0){
                                Toast.makeText(mContext, R.string.no_data_save, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode== Activity.RESULT_OK && requestCode == MODIFY_CODE){
            boolean isUpdate = data.getBooleanExtra("isUpdate", false);
            if (isUpdate){
                Toast.makeText(mContext, "更新成功", Toast.LENGTH_SHORT).show();
                try {
                    list.clear();
                    getData();//更新数据
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(mContext, "更新失败", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void getData(final SanitationAreaAssessment assessment) throws JSONException, UnsupportedEncodingException {
        JSONObject object = new JSONObject();
        object.put("assmentid", assessment.getID());
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
                            JSONArray commitAry = new JSONArray();
                            for (int i=0; i<array.length(); i++){
                                SanitaionAreaAssementItemView itemView = getGson().fromJson(array.getJSONObject(i).toString(), SanitaionAreaAssementItemView.class);

                                SanitaionAreaAssementItem item = new SanitaionAreaAssementItem(
                                        itemView.getAsItemID(),itemView.getProjectID(), Float.parseFloat(itemView.getProjectfs()), itemView.getKfyy()
                                );
                                commitAry.put(SanitaionAreaAssementItem.fromObject2Json(item));
                            }
                            commitData(assessment, commitAry);

                        } catch (Exception e) {
                            e.printStackTrace();
                            if (progressDialog != null){
                                progressDialog.dismiss();
                                Toast.makeText(mContext, R.string.data_error, Toast.LENGTH_SHORT).show();
                            }
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

    //保存数据
    private void commitData(SanitationAreaAssessment assessment, JSONArray array) throws JSONException, UnsupportedEncodingException {
        String userid = getGson().fromJson(sp.getString("userid", ""), String.class);
        JSONObject object = new JSONObject();
        object.put("item", array);
        object.put("userid",userid);
        object.put("areaID", assessment.getAreaid());
        object.put("bz", assessment.getBz());
        object.put("oldAssementID", assessment.getID());

        StringEntity entity = new StringEntity(object.toString(), "utf-8");
        getFinalHttp().post(
                InternetURL.SAVE_NOT_COMMIT,
                entity,
                "application/json; charset=utf-8",
                new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        super.onSuccess(o);
                        if (o.toString().equals("true")){
                            if (progressDialog != null){
                                progressDialog.dismiss();
                                Toast.makeText(mContext, R.string.commit_over, Toast.LENGTH_SHORT).show();
                            }
                            try {
                                getData();//提交完毕后更新数据
                                list.clear();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }else {
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
