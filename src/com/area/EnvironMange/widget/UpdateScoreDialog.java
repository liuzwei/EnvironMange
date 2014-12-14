package com.area.EnvironMange.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.area.EnvironMange.R;
import com.area.EnvironMange.model.MyBuildScore;


/**
 * author: liuzwei
 * Date: 2014/8/19
 * Time: 15:39
 * 类的功能、说明写在此处.
 */
public class UpdateScoreDialog extends Dialog implements View.OnClickListener{
    private Context context;
    private EditText number;
    private Button sure;
    private Button cancle;
    private TextView title;
    private RequestQueue mRequestQueue;
    private MyBuildScore myBuildScore;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){

            }
            super.handleMessage(msg);
        }
    };

    public UpdateScoreDialog(MyBuildScore myBuildScore, Context context, int them) {
        super(context,them);
        this.context = context;
        this.myBuildScore = myBuildScore;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_dialog);
        initView();
        title.setText("请给区域："+myBuildScore.getName()+" 重新打分");

    }

    private void initView(){
        mRequestQueue = Volley.newRequestQueue(context);
//        number = (EditText) findViewById(R.id.number);
        title = (TextView) findViewById(R.id.title);
        sure = (Button) this.findViewById(R.id.sure);
        sure.setOnClickListener(this);
        cancle = (Button) this.findViewById(R.id.cancle);
        cancle.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.sure:
                Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                UpdateScoreDialog.this.dismiss();
                break;
            case R.id.cancle:
                UpdateScoreDialog.this.dismiss();
                break;
        }
    }


}