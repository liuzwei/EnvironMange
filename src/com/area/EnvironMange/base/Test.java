package com.area.EnvironMange.base;

import com.area.EnvironMange.model.SanitaionAreaAssementItem;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by liuzwei on 2014/12/9.
 */
public class Test {
    public void test() {
        String url = "http://222.135.111.86:9009/Service1.svc/SaveProjectFs";
        StringEntity entity = null;
        JSONArray array = new JSONArray();
        array.put(SanitaionAreaAssementItem.fromObject2Json(new SanitaionAreaAssementItem("1d2831b6-2e1b-49a0-b58f-48135b72fb60", 14.0f, "")));
        array.put(SanitaionAreaAssementItem.fromObject2Json(new SanitaionAreaAssementItem("1d2831b6-2e1b-49a0-b58f-48135b72fb62", 15.0f, "")));
        JSONObject object = new JSONObject();
        try {
            object.put("item", array);
            object.put("userid", "sy001");
            object.put("jcsj", "20141202");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            entity = new StringEntity(object.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


//        switch (v.getId()){
//            case R.id.login_btn:
//                FinalHttp finalHttp = new FinalHttp();
//                finalHttp.post(
//                        url,
//                        entity,
//                        "application/json; charset=utf-8",
//                        new AjaxCallBack<Object>() {
//                            @Override
//                            public void onSuccess(Object o) {
//                                super.onSuccess(o);
//                            }
//
//                            @Override
//                            public void onFailure(Throwable t, int errorNo, String strMsg) {
//                                super.onFailure(t, errorNo, strMsg);
//                            }
//                        }
//                );
//                break;
//        }
    }
}
