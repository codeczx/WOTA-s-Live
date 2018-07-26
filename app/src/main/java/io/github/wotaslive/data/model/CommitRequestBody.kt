package io.github.wotaslive.data.model


data class CommitRequestBody(
        val lastTime: Long,
        val dynamicId: Int,
        val limit: Int
)