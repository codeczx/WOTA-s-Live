package io.github.wotaslive.data;

import io.github.wotaslive.data.model.LiveInfo;
import io.github.wotaslive.data.model.LiveOneRequestBody;
import io.github.wotaslive.data.model.LiveRequestBody;
import io.github.wotaslive.data.model.ShowInfo;
import io.reactivex.Flowable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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

	public Flowable<ShowInfo> getOpenLiveInfo() {
		LiveRequestBody requestBody = new LiveRequestBody(
				0, 0, 0, 0, 20, System.currentTimeMillis());
		return getSNHApi().getOpenLive(requestBody);
	}

	public Flowable<ResponseBody> getLiveOneInfo(String liveId) {
		LiveOneRequestBody requestBody = new LiveOneRequestBody(0, 0, liveId);
		return getSNHApi().getLiveOne(requestBody);
	}
}
