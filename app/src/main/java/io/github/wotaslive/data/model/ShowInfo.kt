package io.github.wotaslive.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Tony on 2017/10/22 22:26.
 * Class description:
 */

data class ShowInfo(var status: Int = 0,
                    var message: String? = null,
                    var content: ContentBean? = null) {

    /**
     * status : 200
     * message : 请求成功
     * content : {"liveList":[{"liveId":"59e6c4be0cf23fa04ee4c2fd","title":"《第48区》剧场公演","subTitle":"TEAM SII剧场公演","picPath":"/mediasource/live/1508295870745LpNjGPwh18.jpg","isOpen":false,"startTime":1508930100000,"count":{"praiseCount":711,"commentCount":54,"memberCommentCount":0,"shareCount":17,"quoteCount":0},"isLike":false,"groupId":10},{"liveId":"59e6c4df0cf23fa04ee4c2fe","title":"《以爱之名》剧场公演","subTitle":"TEAM NII剧场公演","picPath":"/mediasource/live/1508295903659T8a11zK5V9.jpg","isOpen":false,"startTime":1509016500000,"count":{"praiseCount":596,"commentCount":43,"memberCommentCount":0,"shareCount":6,"quoteCount":0},"isLike":false,"groupId":10}]}
     */

    data class ContentBean(@SerializedName("liveList")
                           var showList: List<ShowBean>? = null) {
        /**
         * liveId : 59e6c4be0cf23fa04ee4c2fd
         * title : 《第48区》剧场公演
         * subTitle : TEAM SII剧场公演
         * picPath : /mediasource/live/1508295870745LpNjGPwh18.jpg
         * isOpen : false
         * startTime : 1508930100000
         * count : {"praiseCount":711,"commentCount":54,"memberCommentCount":0,"shareCount":17,"quoteCount":0}
         * isLike : false
         * groupId : 10
         */
        class ShowBean(var liveId: String? = null,
                       var title: String? = null,
                       var subTitle: String? = null,
                       var picPath: String? = null,
                       var isIsOpen: Boolean = false,
                       var startTime: Long = 0,
                       var count: CountBean? = null,
                       var isIsLike: Boolean = false,
                       var groupId: Int = 0) {

            /**
             * praiseCount : 711
             * commentCount : 54
             * memberCommentCount : 0
             * shareCount : 17
             * quoteCount : 0
             */
            class CountBean(var praiseCount: Int = 0,
                            var commentCount: Int = 0,
                            var memberCommentCount: Int = 0,
                            var shareCount: Int = 0,
                            var quoteCount: Int = 0)
        }
    }
}
