package com.area.EnvironMange.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.area.EnvironMange.R;
import com.area.EnvironMange.base.BaseActivity;
import com.area.EnvironMange.common.InternetURL;
import com.area.EnvironMange.model.SanitaionAreaAssementItem;
import com.area.EnvironMange.util.StringUtil;
import com.google.gson.Gson;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzwei on 2014/12/8.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private EditText username;
    private EditText password;
    private Button loginBtn;

    private final static String SERVICE_URI = "http://222.135.111.86:9009/Service1.svc";
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

//                List<SanitaionAreaAssementItem> list = new ArrayList<SanitaionAreaAssementItem>();
//                list.add(new SanitaionAreaAssementItem("1d2831b6-2e1b-49a0-b58f-48135b72fb60", 10));
//                list.add(new SanitaionAreaAssementItem("6fb0b7a1-3989-473d-ae09-70ddf85fece2", 6));
//
//                Gson gson = new Gson();
//                AjaxParams params = new AjaxParams();
////                params.put("item", gson.toJson(list));
//                params.put("userid", "sy001");
//                params.put("jcsj", "20141207");
//
//                FinalHttp finalHttp = new FinalHttp();
//                finalHttp.post(SERVICE_URI + "/SaveProjectFs",params,
//                        new AjaxCallBack<Object>() {
//                            @Override
//                            public void onSuccess(Object o) {
//                                super.onSuccess(o);
//                            }
//
//                            @Override
//                            public void onFailure(Throwable t, String strMsg) {
//                                super.onFailure(t, strMsg);
//                            }
//                        });
                loginWcf();
                break;
        }
    }

    private void login(){
        if (checkParams()){
            String url = String.format(InternetURL.LOGIN_URL+"?ID=%s&pwd=%s", username.getText().toString(), password.getText().toString() );
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    "http://222.135.111.86:9009/Service1.svc/Login",
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
            );
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

    private void loginWcf() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getWcf();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void getWcf()throws JSONException, IOException {
        // POST request to <service>/SaveVehicle
//        HttpPost request = new HttpPost(SERVICE_URI + "/Login");
        HttpPost request = new HttpPost(SERVICE_URI + "/SaveProjectFs");
//        HttpPost request = new HttpPost(SERVICE_URI + "/GetBuildings");
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");


        List<SanitaionAreaAssementItem> list = new ArrayList<SanitaionAreaAssementItem>();
        list.add(new SanitaionAreaAssementItem("1d2831b6-2e1b-49a0-b58f-48135b72fb60", 10));
        list.add(new SanitaionAreaAssementItem("6fb0b7a1-3989-473d-ae09-70ddf85fece2", 6));
        SanitaionAreaAssementItem[] areaAssementItems = {new SanitaionAreaAssementItem("1d2831b6-2e1b-49a0-b58f-48135b72fb60", 10),
                new SanitaionAreaAssementItem("6fb0b7a1-3989-473d-ae09-70ddf85fece2", 6)};
        JSONObject object = new JSONObject();
//        object.put("ID", "sy001");
//        object.put("pwd", "123456");
//        object.put("item",list);
        object.put("userid", "sy001");
        object.put("jcsj", "20141207");

//        // Build JSON string
//        JSONStringer vehicle = new JSONStringer()
//                .object()
//               .key("item").value(list)
//                .object()
//                .key("userid").value("sy001")
//               .key("jcsj").value("20141207")
////                .key("ID").value("sy001")
////                .key("pwd").value("123456")
//                .endObject()
//                .endObject();

        StringEntity entity = new StringEntity(object.toString());

        request.setEntity(entity);

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(request);

        HttpEntity responseEntity = response.getEntity();

        // Read response data into buffer
        char[] buffer = new char[(int)responseEntity.getContentLength()];
        InputStream stream = responseEntity.getContent();
        InputStreamReader reader = new InputStreamReader(stream);
        reader.read(buffer);
        stream.close();

        String str = String.valueOf(buffer);
//        JSONObject objecta = new JSONObject(new String(buffer));
//        System.out.print(buffer);
    }

    private void refreshVehicles() {
        try {

            // Send GET request to <service>/GetPlates
            HttpGet request = new HttpGet(SERVICE_URI + "/Login");
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(request);

            HttpEntity responseEntity = response.getEntity();

            // Read response data into buffer
            char[] buffer = new char[(int)responseEntity.getContentLength()];
            InputStream stream = responseEntity.getContent();
            InputStreamReader reader = new InputStreamReader(stream);
            reader.read(buffer);
            stream.close();

            JSONObject object = new JSONObject(new String(buffer));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
