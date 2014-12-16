package com.area.EnvironMange.ui;

import android.app.Activity;
import android.content.Intent;
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
import com.area.EnvironMange.common.InternetURL;
import com.area.EnvironMange.model.Building;
import com.area.EnvironMange.model.MyBuildScore;
import com.area.EnvironMange.model.SanitationAreaAssessment;
import com.area.EnvironMange.util.DateUtil;
import com.area.EnvironMange.util.SystemExitUtil;
import com.area.EnvironMange.widget.UpdateScoreDialog;
import com.area.EnvironMange.widget.SaveScoreDialog;
import net.tsz.afinal.http.AjaxCallBack;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
    private ImageView saveall;//一键提交
    private static final int MODIFY_CODE = 102;
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
                //todo
                Toast.makeText(mContext, "未做", Toast.LENGTH_SHORT).show();
//                SaveScoreDialog dialog = new SaveScoreDialog( list , this, R.style.dialog1);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.show();
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
                intent.putExtra("assessmentID", asm.getID());
                intent.putExtra("beizhu", asm.getBz());
                startActivityForResult(intent, MODIFY_CODE);
                break;
            case 2:
                Toast.makeText(mContext, "提交成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

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
                            adapter.notifyDataSetChanged();
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
}
