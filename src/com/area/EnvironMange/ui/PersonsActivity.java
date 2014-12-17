package com.area.EnvironMange.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.area.EnvironMange.R;
import com.area.EnvironMange.adapter.PersonAdapter;
import com.area.EnvironMange.adapter.TeachBuildingAreaAdapter;
import com.area.EnvironMange.base.BaseActivity;
import com.area.EnvironMange.base.Constants;
import com.area.EnvironMange.common.InternetURL;
import com.area.EnvironMange.model.Person;
import com.area.EnvironMange.model.SanitationArea;
import com.area.EnvironMange.util.PhoneEnvUtil;
import com.area.EnvironMange.util.StringUtil;
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
public class PersonsActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = PersonsActivity.class.getSimpleName();

    private ListView persons;
    private PersonAdapter adapter;
    private List<Person> personList = new ArrayList<Person>();
    private ImageView back;
    private EditText content;//搜索框
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person);
        initView();
        try {
            if (PhoneEnvUtil.isNetworkConnected(mContext)) {
                getPerson();
            }else {
                Toast.makeText(mContext, R.string.check_network, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        back = (ImageView) this.findViewById(R.id.back);
        back.setOnClickListener(this);
        content = (EditText) this.findViewById(R.id.content);
        persons = (ListView) this.findViewById(R.id.persons);
        adapter = new PersonAdapter(PersonsActivity.this, personList);
        persons.setAdapter(adapter);

        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                personList.clear();
                if (!PhoneEnvUtil.isNetworkConnected(mContext)){
                    Toast.makeText(mContext, R.string.check_network, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtil.isNullOrEmpty(s.toString())){
                    try {
                        //为空的话查询所有人
                        getPerson();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    //不为空的话先清空再查找人
                    try {
                        searchByName();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

        }
    }

    //弹出顶部主菜单
    public void onTopMenuPopupButtonClick(View view){
        mainPopMenu.showAsDropDown(view);
    }
    /**
     *
     */
    private void getPerson() throws JSONException, UnsupportedEncodingException {
        getFinalHttp().post(
                InternetURL.GET_ALL_LXR_URL,
                new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        super.onSuccess(o);
                        try {
                            JSONArray array = new JSONArray(o.toString());
                            for (int i=0; i<array.length(); i++){
                                personList.add(getGson().fromJson(array.getJSONObject(i).toString(), Person.class));
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
    protected void onDestroy() {
        super.onDestroy();
    }

    private void searchByName() throws UnsupportedEncodingException, JSONException {
        JSONObject object = new JSONObject();
        object.put("xm", content.getText().toString());
        StringEntity entity = new StringEntity(object.toString(), "utf-8");
        getFinalHttp().post(
                InternetURL.GET_LXR_BY_NAME,
                entity,
                "application/json; charset=utf-8",
                new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        super.onSuccess(o);
                        try {
                            JSONArray array = new JSONArray(o.toString());
                            for (int i=0; i<array.length(); i++){
                                personList.add(getGson().fromJson(array.getJSONObject(i).toString(), Person.class));
                            }
                            adapter.notifyDataSetChanged();
                            if (array.length() == 0){
                                Toast.makeText(mContext, R.string.no_person_data, Toast.LENGTH_SHORT).show();
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
}

