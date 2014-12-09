package com.area.EnvironMange.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.area.EnvironMange.R;
import com.area.EnvironMange.adapter.BuildingAdapter;
import com.area.EnvironMange.base.BaseActivity;
import com.area.EnvironMange.common.InternetURL;
import com.area.EnvironMange.model.Building;
import com.google.gson.Gson;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzwei on 2014/12/9.
 */
public class MainBuildingsActivity extends BaseActivity {
    private List<Building> list = new ArrayList<Building>();
    private ListView listView;
    private BuildingAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.building_layout);

        initView();
        getBuildings();
    }

    private void initView(){
        listView = (ListView) findViewById(R.id.building_lsv);
        adapter = new BuildingAdapter(mContext, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击某一项时触发
            }
        });
    }

    private void getBuildings(){
        getFinalHttp().get(
                InternetURL.GET_BUILDING_URL,
                new AjaxCallBack<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        try {
                            JSONArray array = new JSONArray(o.toString());
                            for (int i=0; i<array.length(); i++){
                                JSONObject object = array.getJSONObject(i);
                                list.add(Building.jsonObject2Object(object));
                            }
                            adapter.notifyDataSetChanged();
                            Log.i("Main", array.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        super.onSuccess(o);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                    }
                }
        );
    }
}
