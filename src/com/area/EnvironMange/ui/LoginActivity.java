package com.area.EnvironMange.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.area.EnvironMange.R;
import com.area.EnvironMange.base.BaseActivity;
import com.area.EnvironMange.common.InternetURL;
import com.area.EnvironMange.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

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
    }

    private void initView(){
        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                login();
                break;
        }
    }

    private void login(){
        if (checkParams()){
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    InternetURL.LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Log.i("LoginActivity", s);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.i("LoginActivity", "asdasd");
                        }
                    }
            ){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("ID",username.getText().toString());
                    params.put("pwd", password.getText().toString());
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/x-www-form-urlencoded");
                    return params;
                }
            };
            getRequestQueue().add(request);
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
}
