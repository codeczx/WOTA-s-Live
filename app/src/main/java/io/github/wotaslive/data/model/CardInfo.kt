package io.github.wotaslive.data.model

data class CardInfo(val message: String = "",
                    val content: Content,
                    val status: Int = 0) {
    data class Content(val punchCardDay: Int = 0,
                       val todayPunchCard: Boolean = false,
                       val experience: Int = 0)
}


