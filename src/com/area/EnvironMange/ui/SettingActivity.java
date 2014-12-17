package com.area.EnvironMange.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.area.EnvironMange.R;
import com.area.EnvironMange.base.ActivityTack;
import com.area.EnvironMange.base.BaseActivity;

/**
 * author: ${zhanghailong}
 * Date: 2014/12/10
 * Time: 16:44
 * 类的功能、说明写在此处.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView setting_exit;
    private TextView updatePass;
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back:
                finish();
                break;
            case R.id.setting_exit:
                ActivityTack.getInstanse().popUntilActivity(LoginActivity.class);
                break;
            case R.id.set_update_pass:
                startActivity(new Intent(SettingActivity.this, UpdatePassActivity.class));
                break;
        }
    }
    //弹出顶部主菜单
    public void onTopMenuPopupButtonClick(View view){
        mainPopMenu.showAsDropDown(view);
    }
}
