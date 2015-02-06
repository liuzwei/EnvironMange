package com.area.EnvironMange.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.area.EnvironMange.R;
import com.area.EnvironMange.base.BaseActivity;

/**
 * author: ${zhanghailong}
 * Date: 2014/12/10
 * Time: 12:14
 * 楼层选择
 */
public class FloorSelectActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout floorone;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floor_layout);
        floorone = (LinearLayout) this.findViewById(R.id.floorone);
        floorone.setOnClickListener(this);
        back = (ImageView) this.findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floorone:
                Intent area = new Intent(this, AreaActivity.class);
                startActivity(area);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    //弹出顶部主菜单
    public void onTopMenuPopupButtonClick(View view) {
        mainPopMenu.showAsDropDown(view);
    }
}
