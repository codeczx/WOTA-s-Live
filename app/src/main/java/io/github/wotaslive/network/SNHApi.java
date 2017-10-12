package io.github.wotaslive.network;

import io.github.wotaslive.model.MemberLiveEntity;
import io.github.wotaslive.model.MemberLiveRequestBody;
import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by codeczx on 2017/10/9.
 */

public interface SNHApi {

    public static final String BASE_URL = "https://plive.48.cn/";

    @POST("/livesystem/api/live/v1/memberLivePage")
    Flowable<MemberLiveEntity> getMemberLive(@Body MemberLiveRequestBody memberLiveRequestBody);
}
