package com.area.EnvironMange.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.area.EnvironMange.R;
import com.area.EnvironMange.adapter.MyBuildScoreAdapter;
import com.area.EnvironMange.adapter.OnClickContentItemListener;
import com.area.EnvironMange.base.BaseActivity;
import com.area.EnvironMange.common.InternetURL;
import com.area.EnvironMange.model.SanitationAreaAssessment;
import com.area.EnvironMange.util.DateUtil;
import com.area.EnvironMange.util.StringUtil;
import com.area.EnvironMange.widget.DateBackListener;
import com.area.EnvironMange.widget.DateDialog;
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
public class IndexActivity extends BaseActivity implements View.OnClickListener, OnClickContentItemListener, DateBackListener {
    private List<SanitationAreaAssessment> list = new ArrayList<SanitationAreaAssessment>();
    private ListView listView;
    private MyBuildScoreAdapter adapter;
    private ImageView back;
    private Button search;//搜索按钮
    private EditText beginET;
    private EditText endET;
    private String endTime;
    private String beginTime;
    private DateDialog dateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mybuildscore_layout);
        initView();

        String beginTime = DateUtil.getFormatDateTime(new Date(System.currentTimeMillis() - 14 * 24 * 60 * 60 * 1000), "yyyyMMdd");
        String endTime = DateUtil.getFormatDateTime(new Date(), "yyyyMMdd");

        try {
            getData(beginTime, endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        listView = (ListView) findViewById(R.id.building_lsv);
        adapter = new MyBuildScoreAdapter(mContext, list);
        adapter.setOnClickContentItemListener(this);
        listView.setAdapter(adapter);
        back = (ImageView) this.findViewById(R.id.back);
        back.setOnClickListener(this);

        search = (Button) this.findViewById(R.id.mybuildscore_search);
        search.setOnClickListener(this);

        beginET = (EditText) this.findViewById(R.id.begin_time);
        beginET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog = new DateDialog(IndexActivity.this, R.style.dialog, true);
                dateDialog.setDateBackListener(IndexActivity.this);
                dateDialog.show();
            }
        });
        endET = (EditText) this.findViewById(R.id.end_time);
        endET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog = new DateDialog(IndexActivity.this, R.style.dialog, false);
                dateDialog.setDateBackListener(IndexActivity.this);
                dateDialog.show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.mybuildscore_search://点击搜索按钮
                if (StringUtil.isNullOrEmpty(beginET.getText().toString())){
                    Toast.makeText(mContext, "请选择开始时间", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtil.isNullOrEmpty(endET.getText().toString())){
                    Toast.makeText(mContext, "请选择结束时间", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    list.clear();
                    getData(beginET.getText().toString(), endET.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
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
                Intent intent = new Intent(IndexActivity.this, ScoreDetailActivity.class );
                intent.putExtra("assessmentID", asm.getID());
                intent.putExtra("beizhu", asm.getBz());
                startActivity(intent);
                break;

        }
    }

    private void getData(String beginTime, String endTime) throws JSONException, UnsupportedEncodingException {
        String userid = getGson().fromJson(sp.getString("userid", ""), String.class);
        JSONObject object = new JSONObject();
        object.put("userid", userid);
        object.put("begineTime", beginTime);
        object.put("endtime", endTime);

        StringEntity entity = new StringEntity(object.toString(), "utf-8");

        getFinalHttp().post(
                InternetURL.GET_COMMIT_FS_URL,
                entity,
                "application/json; charset=utf-8",
                new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        super.onSuccess(o);
                        try {
                            JSONArray array = new JSONArray(o.toString());
                            if (array.length()==0){
                                Toast.makeText(mContext, "没有查询到数据", Toast.LENGTH_SHORT).show();
                            }else {
                                for (int i = 0; i < array.length(); i++) {
                                    SanitationAreaAssessment asm = getGson().fromJson(String.valueOf(array.getJSONObject(i)), SanitationAreaAssessment.class);
                                    list.add(asm);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }catch (Exception e){
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
    public void backTime(String date, boolean isStart) {

        if (isStart){
            beginTime = date;
        }else {
            endTime = date;
        }
        beginET.setText(beginTime);
        endET.setText(endTime);

        if (StringUtil.isNullOrEmpty(beginET.getText().toString())){
            Toast.makeText(mContext, "请先选择开始时间", Toast.LENGTH_SHORT).show();
            endET.setText("");
            return;
        }

        if (!StringUtil.isNullOrEmpty(endET.getText().toString())) {
            if (Integer.parseInt(beginET.getText().toString()) > Integer.parseInt(endET.getText().toString())) {
                Toast.makeText(mContext, "请选择正确结束时间", Toast.LENGTH_SHORT).show();
                endET.setText("");
                return;
            }
        }
    }
}
