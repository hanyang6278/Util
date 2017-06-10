package cn.lemon.utilsdemo;

import android.app.Application;

import cn.lemon.util.Utils;

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
