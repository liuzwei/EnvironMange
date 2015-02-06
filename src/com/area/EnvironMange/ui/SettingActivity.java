package com.area.EnvironMange.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.area.EnvironMange.R;
import com.area.EnvironMange.base.ActivityTack;
import com.area.EnvironMange.base.BaseActivity;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

/**
 * author: ${zhanghailong}
 * Date: 2014/12/10
 * Time: 16:44
 * 设置
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView setting_exit;
    private TextView updatePass;
    private LinearLayout setting_check;//检查更新
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_layout);
        initView();

    }

    private void initView() {
        back = (ImageView) this.findViewById(R.id.back);
        back.setOnClickListener(this);
        setting_exit = (TextView) this.findViewById(R.id.setting_exit);
        setting_exit.setOnClickListener(this);

        updatePass = (TextView) this.findViewById(R.id.set_update_pass);
        updatePass.setOnClickListener(this);
        setting_check = (LinearLayout) this.findViewById(R.id.setting_check);
        setting_check.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.setting_exit:
                ActivityTack.getInstanse().popUntilActivity(LoginActivity.class);
                break;
            case R.id.set_update_pass:
                startActivity(new Intent(SettingActivity.this, UpdatePassActivity.class));
                break;
            case R.id.setting_check:
                //检查更新
                Resources res = getBaseContext().getResources();
                String message = res.getString(R.string.check_new_version).toString();
                progressDialog = new ProgressDialog(SettingActivity.this);
                progressDialog.setMessage(message);
                progressDialog.show();

                UmengUpdateAgent.forceUpdate(mContext);

                UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
                    @Override
                    public void onUpdateReturned(int i, UpdateResponse updateResponse) {
                        progressDialog.dismiss();
                        switch (i) {
                            case UpdateStatus.Yes:
                                Toast.makeText(mContext, "有新版本发现", Toast.LENGTH_SHORT).show();
                                break;
                            case UpdateStatus.No:
                                Toast.makeText(mContext, "已是最新版本", Toast.LENGTH_SHORT).show();
                                break;
                            case UpdateStatus.Timeout:
                                Toast.makeText(mContext, "连接超时", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
                break;
        }
    }

    //弹出顶部主菜单
    public void onTopMenuPopupButtonClick(View view) {
        mainPopMenu.showAsDropDown(view);
    }
}
