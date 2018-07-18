package io.github.wotaslive.data.model

/**
 * Created by codeczx on 2017/11/2 21:51.
 * Class description:
 */
data class RoomInfo(var status: Int = 0,
                    var message: String? = null,
                    var content: List<ContentBean>? = null) {
    /**
     * roomId : 5770618
     * roomType : 1
     * chatType : 0
     * roomName : 李清扬
     * roomAvatar : /mediasource/room/14916912868567h66NnMvFu.jpg
     * roomTopic :
     * creatorId : 28
     * creatorName : 李清扬
     * creatorRole : 1
     * comment : 缺席三周年公演好遗憾老是特殊公演的时候有事情委屈T^T
     * commentTime : 3天前
     * commentTimeMs : 1509278654683
     * hot : 0
     * bgPath :
     * fontColor :
     * maxJoinerNum : 200
     * nowJoinerNum : 0
     */
    data class ContentBean(var roomId: Int = 0,
                           var roomType: Int = 0,
                           var chatType: Int = 0,
                           var roomName: String? = null,
                           var roomAvatar: String? = null,
                           var roomTopic: String? = null,
                           var creatorId: Int = 0,
                           var creatorName: String? = null,
                           var creatorRole: Int = 0,
                           var comment: String? = null,
                           var commentTime: String? = null,
                           var commentTimeMs: Long = 0,
                           var hot: Int = 0,
                           var bgPath: String? = null,
                           var fontColor: String? = null,
                           var maxJoinerNum: Int = 0,
                           var nowJoinerNum: Int = 0)
}
