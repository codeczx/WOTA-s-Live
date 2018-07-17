package io.github.wotaslive.data.model

/**
 * Created by Tony on 2017/10/29 1:38.
 * Class description:
 */

class RecommendInfo {

    /**
     * status : 200
     * message : 请求成功
     * content : [{"sourceId":"2135","sourceType":3,"sourceName":"视频","picPath":"/mediasource/video/1509084512611kK3b7Jq5Ob.jpg","memo":"《记忆中的你我》MV","groupId":10,"heat":3217},{"sourceId":"239","sourceType":4,"sourceName":"夜谈","picPath":"/mediasource/nightwords/15090039890052pHd3meIJ0.jpg","memo":"口口一的KOKORO SONG","groupId":10,"heat":1400}]
     */

    var status: Int = 0
    var message: String? = null
    var content: List<RecommendBean>? = null

    class RecommendBean {
        /**
         * sourceId : 2135
         * sourceType : 3
         * sourceName : 视频
         * picPath : /mediasource/video/1509084512611kK3b7Jq5Ob.jpg
         * memo : 《记忆中的你我》MV
         * groupId : 10
         * heat : 3217
         */

        var sourceId: String? = null
        var sourceType: Int = 0
        var sourceName: String? = null
        var picPath: String? = null
        var memo: String? = null
        var groupId: Int = 0
        var heat: Int = 0
    }
}
