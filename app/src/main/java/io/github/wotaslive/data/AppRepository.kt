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
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

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

    private val dynamicApi: ApiServices by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(DYNAMIC_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        retrofit.create(ApiServices::class.java)
    }

    private val syncApi: ApiServices by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(SYNC_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        retrofit.create(ApiServices::class.java)
    }

    val recommendList: Flowable<RecommendInfo>
        get() = otherApi.recommendList

    val roomList: Flowable<RoomInfo>
        get() {
            return roomApi.getRoomList(RoomListRequestBody(friends))
        }

    fun getLiveInfo(lastTime: Long): Flowable<LiveInfo> {
        val requestBody = LiveRequestBody(
                lastTime, 0, 0, 0, DEFAULT_LIMIT, System.currentTimeMillis())
        return liveApi.getMemberLive(requestBody)
    }

    fun getOpenLiveInfo(isReview: Int, groupId: Int, lastGroupId: Int, lastTime: Long): Flowable<ShowInfo> {
        val showRequestBody = ShowRequestBody(
                isReview, groupId, lastGroupId, lastTime, DEFAULT_LIMIT, System.currentTimeMillis())
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
        val roomDetailRequestBody = RoomDetailRequestBody(roomId, 0, lastTime, DEFAULT_LIMIT)
        return roomApi.getRoomDetail(roomDetailRequestBody)
    }

    fun getRoomBoard(roomId: Int, lastTime: Long): Flowable<BoardPageInfo> {
        val boardPageRequestBody = BoardPageRequestBody(roomId, lastTime, DEFAULT_LIMIT, false)
        return jujuApi.getRoomBoard(boardPageRequestBody)
    }

    fun getDynamicPictures(roomId: Int, lastTime: Long): Flowable<DynamicPictureInfo> {
        val dynamicPictureRequestBody = DynamicPictureRequestBody(lastTime, 21)
        return dynamicApi.getDynamicPictures(roomId, dynamicPictureRequestBody)
    }

    fun getDynamicList(lastTime: Long): Flowable<DynamicInfo> =
            dynamicApi.getDynamicList(DynamicListRequestBody(lastTime, DEFAULT_LIMIT, userId, friends))

    fun getMemberDynamic(memberId: Int, lastTime: Long): Flowable<DynamicInfo> {
        return dynamicApi.getDynamicOfMember(memberId, DynamicListRequestBody(lastTime, DEFAULT_LIMIT, userId, friends))
    }

    fun getSync(): Flowable<SyncInfo> {
        val sync: SyncRequestBody
        val spUtils = SPUtils.getInstance(Constants.SP_NAME)
        val cache: String = spUtils.getString(Constants.SP_SYNC)
        sync = if (cache.isEmpty()) {
            val date = String.format("%tY-%<tm-%<td %<tH:%<tM:%<tS", 0L)
            SyncRequestBody(date, date, date, date, date, date).also {
                spUtils.put(Constants.SP_SYNC, GSON.toJson(it))
            }
        } else {
            GSON.fromJson(cache, SyncRequestBody::class.java)
        }
        return syncApi.sync(sync)
    }

    fun getCommit(lastTime: Long, dynamicId: Int): Flowable<CommitInfo> {
        return dynamicApi.getCommit(CommitRequestBody(lastTime, dynamicId, DEFAULT_LIMIT))
    }

    companion object {

        const val IMG_BASE_URL = "https://source.48.cn/"
        private const val LIVE_BASE_URL = "https://plive.48.cn/"
        private const val OTHER_BASE_URL = "https://pother.48.cn/"
        private const val ROOM_BASE_URL = "https://pjuju.48.cn/"
        private const val USER_BASE_URL = "https://puser.48.cn/"
        private const val JUJU_BASE_URL = "https://pjuju.48.cn/"
        private const val DYNAMIC_BASE_URL = "https://pdynamic.48.cn/"
        private const val SYNC_BASE_URL = "https://psync.48.cn/"

        private const val DEFAULT_LIMIT = 10

        private val GSON = Gson()

        val instance: AppRepository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AppRepository()
        }

        val friends: ArrayList<Int> by lazy {
            val friendsStr = SPUtils.getInstance(Constants.SP_NAME).getString(Constants.SP_FRIENDS, "")
            val list: ArrayList<Int>
            list = if (TextUtils.isEmpty(friendsStr)) {
                ArrayList()
            } else {
                val listType = object : TypeToken<ArrayList<Int>>() {

                }.type
                GSON.fromJson(friendsStr, listType)
            }
            list
        }

        private val userId: Int by lazy {
            SPUtils.getInstance(Constants.SP_NAME).getInt(Constants.SP_USER_ID)
        }

        private val okHttpClient: OkHttpClient
            get() = OkHttpClient.Builder()
                    .addInterceptor(HeaderInterceptor())
                    .addInterceptor(HttpLoggingInterceptor().also {
                        it.level = HttpLoggingInterceptor.Level.BASIC
                    })
                    .build()
    }
}
