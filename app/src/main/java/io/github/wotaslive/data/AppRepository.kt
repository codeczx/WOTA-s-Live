package io.github.wotaslive.data


import android.text.TextUtils
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.wotaslive.Constants
import io.github.wotaslive.data.model.*
import io.reactivex.Flowable
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class AppRepository private constructor() {
    private val liveApi: ApiServices by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(LIVE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        retrofit.create(ApiServices::class.java)
    }

    private val otherApi: ApiServices by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(OTHER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        retrofit.create(ApiServices::class.java)
    }

    private val roomApi: ApiServices by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ROOM_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        retrofit.create(ApiServices::class.java)
    }

    private val userApi: ApiServices by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(USER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        retrofit.create(ApiServices::class.java)
    }

    private val jujuApi: ApiServices by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(JUJU_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        retrofit.create(ApiServices::class.java)
    }

    val recommendList: Flowable<RecommendInfo>
        get() = otherApi.recommendList

    val roomList: Flowable<RoomInfo>
        get() {
            val friendsStr = SPUtils.getInstance(Constants.SP_NAME).getString(Constants.SP_FRIENDS, "")
            val friends: List<Int>
            if (TextUtils.isEmpty(friendsStr)) {
                friends = ArrayList()
            } else {
                val listType = object : TypeToken<ArrayList<Int>>() {

                }.type
                friends = Gson().fromJson(friendsStr, listType)
            }
            val roomListRequestBody = RoomListRequestBody(friends)
            return roomApi.getRoomList(roomListRequestBody)
        }

    fun getLiveInfo(lastTime: Long): Flowable<LiveInfo> {
        val requestBody = LiveRequestBody(
                lastTime, 0, 0, 0, DEFAULT_LITMIT, System.currentTimeMillis())
        return liveApi.getMemberLive(requestBody)
    }

    fun getOpenLiveInfo(isReview: Int, groupId: Int, lastGroupId: Int, lastTime: Long): Flowable<ShowInfo> {
        val showRequestBody = ShowRequestBody(
                isReview, groupId, lastGroupId, lastTime, DEFAULT_LITMIT, System.currentTimeMillis())
        return liveApi.getOpenLive(showRequestBody)
    }

    fun getLiveOneInfo(liveId: String): Flowable<ResponseBody> {
        val requestBody = LiveOneRequestBody(0, 0, liveId)
        return liveApi.getLiveOne(requestBody)
    }

    fun login(username: String, password: String): Flowable<LoginInfo> {
        val loginRequestBody = LoginRequestBody(0, 0, password, username)
        return userApi.login(loginRequestBody)
    }

    fun getRoomDetailInfo(roomId: Int, lastTime: Long): Flowable<RoomDetailInfo> {
        val roomDetailRequestBody = RoomDetailRequestBody(roomId, 0, lastTime, DEFAULT_LITMIT)
        return roomApi.getRoomDetail(roomDetailRequestBody)
    }

    fun getRoomBoard(roomId: Int, lastTime: Long): Flowable<BoardPageInfo> {
        val boardPageRequestBody = BoardPageRequestBody(roomId, lastTime, DEFAULT_LITMIT, false)
        return jujuApi.getRoomBoard(boardPageRequestBody)
    }

    companion object {

        const val IMG_BASE_URL = "https://source.48.cn/"
        private const val LIVE_BASE_URL = "https://plive.48.cn/"
        private const val OTHER_BASE_URL = "https://pother.48.cn/"
        private const val ROOM_BASE_URL = "https://pjuju.48.cn/"
        private const val USER_BASE_URL = "https://puser.48.cn/"
        private const val JUJU_BASE_URL = "https://pjuju.48.cn/"

        private const val DEFAULT_LITMIT = 10

        val instance: AppRepository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AppRepository()
        }

        private val okHttpClient: OkHttpClient
            get() = OkHttpClient.Builder()
                    .addInterceptor(HeaderInterceptor())
                    .build()
    }
}
