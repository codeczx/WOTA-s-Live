package io.github.wotaslive.data.model

/**
 * Created by Tony on 2017/10/29 0:16.
 * Class description:
 */

class ShowRequestBody(
        /**
         * isReview : 1
         * groupId : 0
         * userId : 594709
         * lastGroupId : 0
         * lastTime : 0
         * type : 0
         * limit : 20
         * giftUpdTime : 1498211389003
         */

        var isReview: Int, var groupId: Int, var lastGroupId: Int, var lastTime: Long, var limit: Int, var giftUpdTime: Long) {
    var userId: Int = 0
    var type: Int = 0
}
