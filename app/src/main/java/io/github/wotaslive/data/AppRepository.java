package io.github.wotaslive.data;

import io.github.wotaslive.data.model.LiveInfo;
import io.github.wotaslive.data.model.LiveOneRequestBody;
import io.github.wotaslive.data.model.LiveRequestBody;
import io.github.wotaslive.data.model.RecommendInfo;
import io.github.wotaslive.data.model.ShowInfo;
import io.github.wotaslive.data.model.ShowRequestBody;
import io.reactivex.Flowable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppRepository {

	public static final String IMG_BASE_URL = "https://source.48.cn/";
	private static final String LIVE_BASE_URL = "https://plive.48.cn/";
	private static final String OTHER_BASE_URL = "https://pother.48.cn/";
	private static final int DEFAULT_LITMIT = 20;
	private volatile static AppRepository mInstance;
	private ApiServices mLiveApi;
	private ApiServices mOtherApi;

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

	private ApiServices getLiveApi() {
		if (mInstance.mLiveApi == null) {
			Retrofit retrofit = new Retrofit.Builder()
					.client(getOkHttpClient())
					.baseUrl(LIVE_BASE_URL)
					.addConverterFactory(GsonConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build();
			mInstance.mLiveApi = retrofit.create(ApiServices.class);
		}
		return mInstance.mLiveApi;
	}

	private ApiServices getOtherApi() {
		if (mInstance.mOtherApi == null) {
			Retrofit retrofit = new Retrofit.Builder()
					.client(getOkHttpClient())
					.baseUrl(OTHER_BASE_URL)
					.addConverterFactory(GsonConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build();
			mInstance.mOtherApi = retrofit.create(ApiServices.class);
		}
		return mInstance.mOtherApi;
	}

	private static OkHttpClient getOkHttpClient() {
		return new OkHttpClient.Builder()
				.addInterceptor(new HeaderInterceptor())
				.build();
	}

	public Flowable<LiveInfo> getLiveInfo() {
		LiveRequestBody requestBody = new LiveRequestBody(
				0, 0, 0, 0, DEFAULT_LITMIT, System.currentTimeMillis());
		return getLiveApi().getMemberLive(requestBody);
	}

	public Flowable<ShowInfo> getOpenLiveInfo(int isReview, int groupId, int lastGroupId, long lastTime) {
		ShowRequestBody showRequestBody = new ShowRequestBody(
				isReview, groupId, lastGroupId, lastTime, DEFAULT_LITMIT, System.currentTimeMillis());
		return getLiveApi().getOpenLive(showRequestBody);
	}

	public Flowable<ResponseBody> getLiveOneInfo(String liveId) {
		LiveOneRequestBody requestBody = new LiveOneRequestBody(0, 0, liveId);
		return getLiveApi().getLiveOne(requestBody);
	}

	public Flowable<RecommendInfo> getRecommendList() {
		return getOtherApi().getRecommendList();
	}
}
