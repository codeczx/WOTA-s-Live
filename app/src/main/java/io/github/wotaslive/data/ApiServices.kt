package io.github.wotaslive.data

import io.github.wotaslive.data.model.*
import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {

    @get:POST("othersystem/api/user/v1/homepage/recommendList")
    val recommendList: Flowable<RecommendInfo>

    @POST("/livesystem/api/live/v1/memberLivePage")
    fun getMemberLive(@Body liveRequestBody: LiveRequestBody): Flowable<LiveInfo>

    @POST("/livesystem/api/live/v1/openLivePage")
    fun getOpenLive(@Body showRequestBody: ShowRequestBody): Flowable<ShowInfo>

    @POST("/livesystem/api/live/v1/getLiveOne")
    fun getLiveOne(@Body liveOneRequestBody: LiveOneRequestBody): Flowable<ResponseBody>

    @POST("imsystem/api/im/room/v1/login/user/list")
    fun getRoomList(@Body roomListRequestBody: RoomListRequestBody): Flowable<RoomInfo>

    @POST("usersystem/api/user/v1/login/phone")
    fun login(@Body loginRequestBody: LoginRequestBody): Flowable<LoginInfo>

    @POST("imsystem/api/im/v1/member/room/message/mainpage")
    fun getRoomDetail(@Body roomDetailRequestBody: RoomDetailRequestBody): Flowable<RoomDetailInfo>

    //	@POST("https://ppayqa.48.cn/idolanswersystem/api/idolanswer/v1/question_answer/detail")
    //	Flowable<String> getFanpaiAnswer(@Body )

    @POST("imsystem/api/im/v1/member/room/message/boardpage")
    fun getRoomBoard(@Body boardPageRequestBody: BoardPageRequestBody): Flowable<BoardPageInfo>
}
