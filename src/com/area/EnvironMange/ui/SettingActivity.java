package com.area.EnvironMange.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.area.EnvironMange.R;
import com.area.EnvironMange.base.BaseActivity;

/**
 * author: ${zhanghailong}
 * Date: 2014/12/10
 * Time: 16:44
 * 类的功能、说明写在此处.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_layout);
        initView();
    }

    private void initView() {
        back = (ImageView) this.findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back:
                finish();
                break;
        }
    }
    //弹出顶部主菜单
    public void onTopMenuPopupButtonClick(View view){
        mainPopMenu.showAsDropDown(view);
    }
}
