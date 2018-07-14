package io.github.wotaslive.data;

import io.github.wotaslive.data.model.BoardPageInfo;
import io.github.wotaslive.data.model.BoardPageRequestBody;
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
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiServices {

	@POST("/livesystem/api/live/v1/memberLivePage")
	Flowable<LiveInfo> getMemberLive(@Body LiveRequestBody liveRequestBody);

	@POST("/livesystem/api/live/v1/openLivePage")
	Flowable<ShowInfo> getOpenLive(@Body ShowRequestBody showRequestBody);

	@POST("/livesystem/api/live/v1/getLiveOne")
	Flowable<ResponseBody> getLiveOne(@Body LiveOneRequestBody liveOneRequestBody);

	@POST("othersystem/api/user/v1/homepage/recommendList")
	Flowable<RecommendInfo> getRecommendList();

	@POST("imsystem/api/im/room/v1/login/user/list")
	Flowable<RoomInfo> getRoomList(@Body RoomListRequestBody roomListRequestBody);

	@POST("usersystem/api/user/v1/login/phone")
	Flowable<LoginInfo> login(@Body LoginRequestBody loginRequestBody);

	@POST("imsystem/api/im/v1/member/room/message/mainpage")
	Flowable<RoomDetailInfo> getRoomDetail(@Body RoomDetailRequestBody roomDetailRequestBody);

//	@POST("https://ppayqa.48.cn/idolanswersystem/api/idolanswer/v1/question_answer/detail")
//	Flowable<String> getFanpaiAnswer(@Body )

	@POST("imsystem/api/im/v1/member/room/message/boardpage")
	Flowable<BoardPageInfo> getRoomBoard(@Body BoardPageRequestBody boardPageRequestBody);
}
