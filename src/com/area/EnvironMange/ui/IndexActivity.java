package com.area.EnvironMange.ui;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.area.EnvironMange.R;
import com.area.EnvironMange.adapter.MyBuildScoreAdapter;
import com.area.EnvironMange.adapter.OnClickContentItemListener;
import com.area.EnvironMange.base.BaseActivity;
import com.area.EnvironMange.common.InternetURL;
import com.area.EnvironMange.model.MyBuildScore;
import com.area.EnvironMange.model.SanitationAreaAssessment;
import com.area.EnvironMange.util.DateUtil;
import com.area.EnvironMange.util.SystemExitUtil;
import com.area.EnvironMange.widget.DetailDialog;
import com.area.EnvironMange.widget.SaveScoreDialog;
import com.area.EnvironMange.widget.UpdateScoreDialog;
import com.area.EnvironMange.widget.SelectTimeDialog;
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
public class IndexActivity extends BaseActivity implements View.OnClickListener, OnClickContentItemListener {
    private List<SanitationAreaAssessment> list = new ArrayList<SanitationAreaAssessment>();
    private ListView listView;
    private MyBuildScoreAdapter adapter;
    private ImageView back;
    private Button search;//搜索按钮

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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.mybuildscore_search://点击搜索按钮
//                getData();
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
                DetailDialog dialog = new DetailDialog( assessment , this, R.style.dialog1);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
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
                            for (int i=0; i<array.length(); i++){
                                SanitationAreaAssessment asm = getGson().fromJson(String.valueOf(array.getJSONObject(i)), SanitationAreaAssessment.class);
                                list.add(asm);
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

}
