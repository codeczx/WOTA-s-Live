package io.github.wotaslive.data.model

data class CheckInInfo(val message: String = "",
                       val content: Content,
                       val status: Int = 0) {
    data class Content(val addEx: Int = 0,
                       val addMoney: Int = 0,
                       val days: Int = 0)
}