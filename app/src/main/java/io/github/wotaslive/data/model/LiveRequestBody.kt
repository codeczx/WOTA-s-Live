package io.github.wotaslive.data.model

data class LiveRequestBody(
        /**
         * lastTime : 0
         * groupId : 0
         * type : 0
         * memberId : 0
         * limit : 20
         * giftUpdTime : 1498211389003
         */

        var lastTime: Long, var groupId: Int, var type: Int, var memberId: Int, var limit: Int, var giftUpdTime: Long)
