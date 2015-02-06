package com.area.EnvironMange.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.area.EnvironMange.R;
import com.area.EnvironMange.base.BaseActivity;

/**
 * author: ${zhanghailong}
 * Date: 2014/12/10
 * Time: 12:23
 * 卫生区域
 */
public class AreaActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout areaone;
    private LinearLayout areatwo;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.area_layout);
        initView();
    }

    private void initView() {
        areaone = (LinearLayout) this.findViewById(R.id.areaone);
        areaone.setOnClickListener(this);
        back = (ImageView) this.findViewById(R.id.back);
        back.setOnClickListener(this);
        areatwo = (LinearLayout) this.findViewById(R.id.areatwo);
        areatwo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.areaone:
                Toast.makeText(mContext, "已经打分，请选择下一项", Toast.LENGTH_SHORT).show();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.areatwo:
                Intent score = new Intent(this, ScoreActivity.class);
                startActivity(score);
                break;
        }
    }

    //弹出顶部主菜单
    public void onTopMenuPopupButtonClick(View view) {
        mainPopMenu.showAsDropDown(view);
    }
}
