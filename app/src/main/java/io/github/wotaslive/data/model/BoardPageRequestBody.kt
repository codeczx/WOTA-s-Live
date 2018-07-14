package io.github.wotaslive.data.model

data class BoardPageRequestBody(var roomId: Int, var lastTime: Long, var limit: Int, var isFirst: Boolean)