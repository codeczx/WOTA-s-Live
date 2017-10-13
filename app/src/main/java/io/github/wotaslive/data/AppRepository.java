package io.github.wotaslive.data;

import io.github.wotaslive.data.model.LiveInfo;
import io.github.wotaslive.data.model.LiveRequestBody;
import io.reactivex.Flowable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by codeczx on 2017/10/9.
 */

public class AppRepository {
	
	private static final String LIVE_BASE_URL = "https://plive.48.cn/";
	private volatile static AppRepository mInstance;
	private ApiServices snhApi;
	
	private AppRepository() {
	}
	
	public static AppRepository getInstance() {
		if (mInstance == null) {
			synchronized (AppRepository.class) {
				if (mInstance == null) {
					mInstance = new AppRepository();
				}
			}
		}
		return mInstance;
	}
	
	private ApiServices getSNHApi() {
		if (mInstance.snhApi == null) {
			Retrofit retrofit = new Retrofit.Builder()
					.client(getOkHttpClient())
					.baseUrl(LIVE_BASE_URL)
					.addConverterFactory(GsonConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build();
			mInstance.snhApi = retrofit.create(ApiServices.class);
		}
		return mInstance.snhApi;
	}
	
	private static OkHttpClient getOkHttpClient() {
		return new OkHttpClient.Builder()
				.addInterceptor(new HeaderInterceptor())
				.build();
	}
	
	public Flowable<LiveInfo> getLiveInfo() {
		LiveRequestBody requestBody = new LiveRequestBody(
				0, 0, 0, 0, 20, System.currentTimeMillis());
		return getSNHApi().getMemberLive(requestBody);
	}
}
