package io.github.wotaslive.data.model

import com.google.gson.annotations.SerializedName

class LiveInfo {

    var status: Int = 0
    var message: String? = null
    var content: ContentBean? = null

    class ContentBean {

        var giftUpdTime: Long = 0
        @SerializedName("liveList")
        var liveList: List<RoomBean>? = null
        @SerializedName("reviewList")
        var reviewList: List<RoomBean>? = null
        var giftUpdUrl: List<*>? = null
        var hasReviewUids: List<Int>? = null

        class RoomBean {
            /**
             * liveId : 59de13f10cf294bc616de87e
             * title : 吕梦莹的直播间
             * subTitle : 日常写作业🐷
             * picPath : /mediasource/live/15061690159861h5h22LAZ0.jpg
             * startTime : 1507726321749
             * memberId : 286982
             * liveType : 1
             * picLoopTime : 0
             * lrcPath : /mediasource/live/lrc/59de13f10cf294bc616de87e.lrc
             * streamPath : http://2519.liveplay.myqcloud.com/live/2519_3145421.flv
             * screenMode : 0
             */

            var liveId: String? = null
            var title: String? = null
            var subTitle: String? = null
            var picPath: String? = null
            var startTime: Long = 0
            var memberId: Int = 0
            var liveType: Int = 0
            var picLoopTime: Int = 0
            var lrcPath: String? = null
            var streamPath: String? = null
            var screenMode: Int = 0
        }
    }
}
