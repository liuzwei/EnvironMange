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
import com.area.EnvironMange.model.SanitationAreaAssessment;

import java.util.List;


/**
 * author: liuzwei
 * Date: 2014/8/19
 * Time: 15:39
 * 类的功能、说明写在此处.
 */
public class SaveScoreDialog extends Dialog implements View.OnClickListener{
    private Context context;
    private EditText number;
    private Button sure;
    private Button cancle;
    private TextView title;
    List<SanitationAreaAssessment> list;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){

            }
            super.handleMessage(msg);
        }
    };

    public SaveScoreDialog(List<SanitationAreaAssessment> list, Context context, int them) {
        super(context,them);
        this.context = context;
        this.list = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savescore_dialog);
        initView();
    }

    private void initView(){
        sure = (Button) this.findViewById(R.id.sure);
        sure.setOnClickListener(this);
        cancle = (Button) this.findViewById(R.id.cancle);
        cancle.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sure:
                Toast.makeText(context, "提交打分成功", Toast.LENGTH_SHORT).show();
                SaveScoreDialog.this.dismiss();
                break;
            case R.id.cancle:
                SaveScoreDialog.this.dismiss();
                break;
        }
    }


}