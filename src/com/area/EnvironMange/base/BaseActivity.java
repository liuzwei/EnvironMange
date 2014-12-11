package com.area.EnvironMange.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.area.EnvironMange.menu.MainPopMenu;
import com.area.EnvironMange.ui.CenterActivity;
import com.area.EnvironMange.ui.IndexActivity;
import com.area.EnvironMange.ui.MainBuildingsActivity;
import com.area.EnvironMange.util.SystemExitUtil;
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

//    private Gson gson = new Gson();
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

//    public Gson getGson(){
//        return gson;
//    }

    protected Context getContext() {
        return mContext;
    }

    //存储sharepreference
    public void save(String key, Object value){
        SharedPreferences.Editor editor = sp.edit();
//        editor.putString(key, gson.toJson(value)).commit();
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
            case 1://打分
                Intent main = new Intent(this, MainBuildingsActivity.class );
                startActivity(main);
                break;
            case 2://查询
                Intent chaxun = new Intent(this, IndexActivity.class);
                startActivity(chaxun);
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

}
