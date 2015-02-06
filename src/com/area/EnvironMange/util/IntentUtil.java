package com.area.EnvironMange.util;

import android.app.Activity;
import android.content.Intent;
import com.area.EnvironMange.R;
import org.apache.http.message.BasicNameValuePair;

public class IntentUtil {
    public static void start_activity(Activity activity, Class<?> cls, BasicNameValuePair... name) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        for (int i = 0; i < name.length; i++) {
            intent.putExtra(name[i].getName(), name[i].getValue());
        }
        activity.startActivity(intent);

        //默认启动动画
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}
