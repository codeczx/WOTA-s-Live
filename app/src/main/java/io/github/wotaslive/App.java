package io.github.wotaslive;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

public class App extends Application {

	private static App mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		Utils.init(this);
		mInstance = this;
	}

	public static App getInstance() {
		return mInstance;
	}
}
