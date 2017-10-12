package io.github.wotaslive;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by codeczx on 2017/10/9.
 */

public class App extends Application {

    private App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        mInstance = this;
    }

    public App getInstance() {
        return mInstance;
    }
}
