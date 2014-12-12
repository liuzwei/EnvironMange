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
public class BeizhuDialog extends Dialog implements View.OnClickListener{
    private Context context;

    private Button sure;


    public BeizhuDialog(Context context, int them) {
        super(context,them);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beizhu_dialog);
        initView();
    }

    private void initView(){

        sure = (Button) this.findViewById(R.id.sure);
        sure.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sure:
                BeizhuDialog.this.dismiss();
                break;

        }
    }


}