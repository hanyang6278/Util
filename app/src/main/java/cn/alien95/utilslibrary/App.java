package cn.alien95.utilslibrary;

import android.app.Application;

import cn.alien95.util.Utils;

/**
 * Created by linlongxin on 2016/1/21.
 */
public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.initialize(this);
        if (BuildConfig.DEBUG) {
            Utils.setDebug(true, "Debug");
        }
    }
}
