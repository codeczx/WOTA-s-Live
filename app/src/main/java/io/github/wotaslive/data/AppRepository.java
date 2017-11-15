package io.github.wotaslive.data;


import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.github.wotaslive.Constants;
import io.github.wotaslive.data.model.LiveInfo;
import io.github.wotaslive.data.model.LiveOneRequestBody;
import io.github.wotaslive.data.model.LiveRequestBody;
import io.github.wotaslive.data.model.LoginInfo;
import io.github.wotaslive.data.model.LoginRequestBody;
import io.github.wotaslive.data.model.RecommendInfo;
import io.github.wotaslive.data.model.RoomDetailInfo;
import io.github.wotaslive.data.model.RoomDetailRequestBody;
import io.github.wotaslive.data.model.RoomInfo;
import io.github.wotaslive.data.model.RoomListRequestBody;
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
	private static final String ROOM_BASE_URL = "https://pjuju.48.cn/";
	private static final String USER_BASE_URL = "https://puser.48.cn/";

	private static final int DEFAULT_LITMIT = 20;
	private volatile static AppRepository mInstance;
	private ApiServices mLiveApi;
	private ApiServices mOtherApi;
	private ApiServices mRoomApi;
	private ApiServices mUserApi;

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

	private ApiServices getRoomApi() {
		if (mInstance.mRoomApi == null) {
			Retrofit retrofit = new Retrofit.Builder()
					.client(getOkHttpClient())
					.baseUrl(ROOM_BASE_URL)
					.addConverterFactory(GsonConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build();
			mInstance.mRoomApi = retrofit.create(ApiServices.class);
		}
		return mInstance.mRoomApi;
	}

	private ApiServices getUserApi() {
		if (mInstance.mUserApi == null) {
			Retrofit retrofit = new Retrofit.Builder()
					.client(getOkHttpClient())
					.baseUrl(USER_BASE_URL)
					.addConverterFactory(GsonConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build();
			mInstance.mUserApi = retrofit.create(ApiServices.class);
		}
		return mInstance.mUserApi;
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

	public Flowable<RoomInfo> getRoomList() {
		String friendsStr = SPUtils.getInstance(Constants.SP_NAME).getString(Constants.SP_FRIENDS, "");
		List<Integer> friends;
		if (TextUtils.isEmpty(friendsStr)) {
			friends = new ArrayList<>();
		} else {
			Type listType = new TypeToken<ArrayList<Integer>>() {}.getType();
			friends = new Gson().fromJson(friendsStr, listType);
		}
		RoomListRequestBody roomListRequestBody = new RoomListRequestBody(friends);
		return getRoomApi().getRoomList(roomListRequestBody);
	}

	public Flowable<LoginInfo> login(String username, String password) {
		LoginRequestBody loginRequestBody = new LoginRequestBody(0, 0, password, username);
		return getUserApi().login(loginRequestBody);
	}

	public Flowable<RoomDetailInfo> getRoomDetailInfo(int roomId, int lastTime) {
		RoomDetailRequestBody roomDetailRequestBody = new RoomDetailRequestBody(roomId, 0, lastTime, 20);
		return getRoomApi().getRoomDetail(roomDetailRequestBody);
	}
}
