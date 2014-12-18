package com.area.EnvironMange.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.area.EnvironMange.menu.MainPopMenu;
import com.area.EnvironMange.ui.*;
import com.google.gson.Gson;
import net.tsz.afinal.FinalHttp;

/**
 * Created by liuzwei on 2014/11/11.
 */
public class BaseActivity extends Activity implements MainPopMenu.OnItemClickListener, View.OnClickListener{
    public Context mContext;
    public SharedPreferences sp;
    public LayoutInflater inflater;
    public static FinalHttp finalHttp = new FinalHttp();

    private ActivityTack tack= ActivityTack.getInstanse();

    private Gson gson = new Gson();
    public MainPopMenu mainPopMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        mainPopMenu = new MainPopMenu(this);
        mainPopMenu.setOnItemClickListener(this);
        sp = getSharedPreferences("environ_manage", Context.MODE_PRIVATE);
        inflater = LayoutInflater.from(mContext);
        tack.addActivity(this);
    }

    protected Context getContext() {
        return mContext;
    }

    //存储sharepreference
    public void save(String key, Object value){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, gson.toJson(value)).commit();
    }

    public ActivityTack getTack() {
        return tack;
    }

    public static FinalHttp getFinalHttp() {
        if (finalHttp == null){
            finalHttp = new FinalHttp();
        }
        return finalHttp;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(int index) {
        switch (index){
            case 0://主页
                Intent myindex = new Intent( this, CenterActivity.class);
                startActivity(myindex);
                break;
            case 1://教学楼区域
                Intent vs1 = new Intent(this, cityset.class );
                vs1.putExtra("name","教学楼区域");
                startActivity(vs1);
                break;
            case 2://教学公共区域
                Intent vs2 = new Intent(this, cityset.class);
                vs2.putExtra("name","教学公共区域");
                startActivity(vs2);
                break;
            case 3://宿舍区域
                Intent vs3 = new Intent(this, cityset.class);
                vs3.putExtra("name", "宿舍区域");
                startActivity(vs3);
                break;
            case 4://户外区域
                Intent vs4 = new Intent(this, cityset.class);
                vs4.putExtra("name","户外区域");
                startActivity(vs4);
                break;
            case 5://未提交成绩
                Intent chaxun1 = new Intent(this, SelectUndoActivity.class);
                startActivity(chaxun1);
                break;
            case 6://历史成绩
                Intent chaxun2 = new Intent(this, IndexActivity.class);
                startActivity(chaxun2);
                break;
            case 7://联系人
                Intent person = new Intent(this, PersonsActivity.class);
                startActivity(person);
                break;
            case 8://设置
                Intent set = new Intent(this, SettingActivity.class);
                startActivity(set);
                break;
        }
    }

    /**
     * 根据资源ID
     * @param resId
     */
    public void alert(int resId){
        ToastUtil.show(getApplicationContext(), resId);
    }

    public Gson getGson() {
        return gson;
    }
}
