package io.github.wotaslive;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class App extends Application {

	private static App mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		Utils.init(this);
		mInstance = this;
		Logger.addLogAdapter(new AndroidLogAdapter());
	}

	public static App getInstance() {
		return mInstance;
	}
}
