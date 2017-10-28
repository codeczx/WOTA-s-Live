package io.github.wotaslive.data;

import io.github.wotaslive.data.model.LiveInfo;
import io.github.wotaslive.data.model.LiveOneRequestBody;
import io.github.wotaslive.data.model.LiveRequestBody;
import io.github.wotaslive.data.model.RecommendInfo;
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
}
