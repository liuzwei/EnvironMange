package com.area.EnvironMange.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.area.EnvironMange.R;
import com.area.EnvironMange.base.BaseActivity;
import com.area.EnvironMange.util.SystemExitUtil;

/**
 * author: ${zhanghailong}
 * Date: 2014/12/10
 * Time: 16:44
 * 类的功能、说明写在此处.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView setting_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_layout);
        initView();

        SystemExitUtil.getInstance().addActivity(this);
    }

    private void initView() {
        back = (ImageView) this.findViewById(R.id.back);
        back.setOnClickListener(this);
        setting_exit = (TextView) this.findViewById(R.id.setting_exit);
        setting_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back:
                finish();
                break;
            case R.id.setting_exit:
                SystemExitUtil.getInstance().exit();
                break;
        }
    }
    //弹出顶部主菜单
    public void onTopMenuPopupButtonClick(View view){
        mainPopMenu.showAsDropDown(view);
    }
}