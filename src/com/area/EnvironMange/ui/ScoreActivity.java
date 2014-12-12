package com.area.EnvironMange.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.area.EnvironMange.R;
import com.area.EnvironMange.base.BaseActivity;
import com.area.EnvironMange.util.StringUtil;
import com.area.EnvironMange.util.SystemExitUtil;

/**
 * author: ${zhanghailong}
 * Date: 2014/12/10
 * Time: 12:39
 * 类的功能、说明写在此处.
 */
public class ScoreActivity extends BaseActivity implements View.OnClickListener {
    private EditText score;
    private Button sub;
    private ImageView back;
    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_layout);
        initView();

        SystemExitUtil.getInstance().addActivity(this);
    }

    private void initView() {
        score = (EditText) this.findViewById(R.id.score);
        sub = (Button) this.findViewById(R.id.sub);
        sub.setOnClickListener(this);
        back = (ImageView) this.findViewById(R.id.back);
        back.setOnClickListener(this);
        save = (Button) this.findViewById(R.id.save);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.sub:
                Toast.makeText(mContext, "已提交打分，得分100", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.save:
                Toast.makeText(mContext, "以保存打分，得分100", Toast.LENGTH_SHORT).show();
                finish();
                break;
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
