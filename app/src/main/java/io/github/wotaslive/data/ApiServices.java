package io.github.wotaslive.data;

import io.github.wotaslive.data.model.LiveInfo;
import io.github.wotaslive.data.model.LiveRequestBody;
import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by codeczx on 2017/10/9.
 */

public interface ApiServices {

    public static final String BASE_URL = "https://plive.48.cn/";

    @POST("/livesystem/api/live/v1/memberLivePage")
    Flowable<LiveInfo> getMemberLive(@Body LiveRequestBody liveRequestBody);
}
