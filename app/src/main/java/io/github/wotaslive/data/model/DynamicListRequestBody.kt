package io.github.wotaslive.data.model


data class DynamicListRequestBody(
        val lastTime: Long,
        val limit: Int,
        val userId: Int,
        val memberId: List<Int>
)