package com.area.EnvironMange.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.area.EnvironMange.R;
import com.area.EnvironMange.model.MyBuildScore;
import com.area.EnvironMange.model.SanitationAreaAssessment;


/**
 * author: liuzwei
 * Date: 2014/8/19
 * Time: 15:39
 * 类的功能、说明写在此处.
 */
public class DetailDialog extends Dialog implements View.OnClickListener{
    private Context context;
    private Button sure;
    private TextView title;
    private SanitationAreaAssessment assessment;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){

            }
            super.handleMessage(msg);
        }
    };

    public DetailDialog(SanitationAreaAssessment assessment, Context context, int them) {
        super(context,them);
        this.context = context;
        this.assessment = assessment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_dialog);
        initView();

    }

    private void initView(){
//        number = (EditText) findViewById(R.id.number);
        title = (TextView) findViewById(R.id.title);
        sure = (Button) this.findViewById(R.id.sure);
        sure.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.sure:
                DetailDialog.this.dismiss();
                break;
            case R.id.cancle:
                DetailDialog.this.dismiss();
                break;
        }
    }


}