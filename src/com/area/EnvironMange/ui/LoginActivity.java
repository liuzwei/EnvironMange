package com.area.EnvironMange.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.area.EnvironMange.R;
import com.area.EnvironMange.base.BaseActivity;
import com.area.EnvironMange.common.InternetURL;
import com.area.EnvironMange.util.IntentUtil;
import com.area.EnvironMange.util.StringUtil;
import com.area.EnvironMange.util.SystemExitUtil;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by liuzwei on 2014/12/8.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private EditText username;
    private EditText password;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initView();

        SystemExitUtil.getInstance().addActivity(this);
    }

    private void initView(){
        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);

        username.setText(getGson().fromJson(sp.getString("username", ""), String.class));
        password.setText(getGson().fromJson(sp.getString("password", ""), String.class));

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                if (checkParams()){
                    try {
                        login();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private boolean checkParams(){
        if (StringUtil.isNullOrEmpty(username.getText().toString())){
            Toast.makeText(mContext, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtil.isNullOrEmpty(password.getText().toString())){
            Toast.makeText(mContext, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void login() throws JSONException, UnsupportedEncodingException {
        JSONObject object = new JSONObject();
        object.put("ID", username.getText().toString());
        object.put("pwd", password.getText().toString());
        StringEntity entity = new StringEntity(object.toString(), "utf-8");
        getFinalHttp().post(
                InternetURL.LOGIN_URL,
                entity,
                "application/json; charset=utf-8",
                new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        super.onSuccess(o);
                        if ("true".equals(o.toString())) {
                            save("username", username.getText().toString());
                            save("password", password.getText().toString());
                            startActivity(new Intent(LoginActivity.this, CenterActivity.class));
                        } else {
                            Toast.makeText(mContext, "用户名或密码错误", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(LoginActivity.this, CenterActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                        Toast.makeText(mContext, "网络请求失败", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(LoginActivity.this, CenterActivity.class));
                    }
                }
        );
    }
}
