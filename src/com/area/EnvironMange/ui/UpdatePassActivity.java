package com.area.EnvironMange.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.area.EnvironMange.R;
import com.area.EnvironMange.base.BaseActivity;
import com.area.EnvironMange.common.InternetURL;
import com.area.EnvironMange.util.StringUtil;
import net.tsz.afinal.http.AjaxCallBack;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * 跟新密码
 */
public class UpdatePassActivity extends BaseActivity {
    private EditText password;
    private EditText surepass;
    private TextView commit;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_pass_layout);
        initView();
    }

    private void initView() {
        password = (EditText) this.findViewById(R.id.update_pass_pwd);
        commit = (TextView) this.findViewById(R.id.update_pass_btn);
        commit.setOnClickListener(this);
        back = (ImageView) this.findViewById(R.id.update_back);
        back.setOnClickListener(this);
        surepass = (EditText) this.findViewById(R.id.sure_pass_pwd);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.update_pass_btn:
                String pass = password.getText().toString();
                String sure = surepass.getText().toString();
                if (StringUtil.isNullOrEmpty(pass)) {
                    Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtil.isNullOrEmpty(sure)) {
                    Toast.makeText(mContext, "请输入确认密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!pass.equals(sure)) {
                    Toast.makeText(mContext, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    updatePass();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.update_back:
                finish();
                break;
        }
    }

    private void updatePass() throws JSONException, UnsupportedEncodingException {
        String userid = getGson().fromJson(sp.getString("userid", ""), String.class);
        JSONObject object = new JSONObject();
        object.put("userid", userid);
        object.put("pwd", password.getText().toString());
        StringEntity entity = new StringEntity(object.toString(), "utf-8");
        getFinalHttp().post(
                InternetURL.UPDATE_PASSWORD_URL,
                entity,
                "application/json; charset=utf-8",
                new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        super.onSuccess(o);
                        if (StringUtil.isNullOrEmpty(o.toString())) {
                            Toast.makeText(mContext, "密码设置成功", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                        Toast.makeText(mContext, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
