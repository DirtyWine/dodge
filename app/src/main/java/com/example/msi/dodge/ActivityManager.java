package com.example.msi.dodge;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/6/18
 * @Modified by
 */
public class ActivityManager {
    private static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if( !activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
