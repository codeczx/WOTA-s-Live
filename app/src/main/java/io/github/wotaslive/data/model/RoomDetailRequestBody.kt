package io.github.wotaslive.data.model

/**
 * Created by codeczx on 2017/11/7 23:24.
 * Class description:
 */
/**
 * roomId : 5777239
 * chatType : 0
 * lastTime : 0
 * limit : 10
 */
data class RoomDetailRequestBody(var roomId: Int, var chatType: Int, var lastTime: Long, var limit: Int)
