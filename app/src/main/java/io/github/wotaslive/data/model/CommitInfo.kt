package io.github.wotaslive.data.model


data class CommitInfo(
        val status: Int, // 200
        val message: String, // success
        val content: Content
) {

    data class Content(
            val data: List<Data>
    ) {

        data class Data(
                val commentId: Int, // 5578583
                val userId: Int, // 0
                val avatar: String, // http://tvax1.sinaimg.cn/crop.0.0.664.664.180/005wC4Mcly8ftg9bb2nb2j30ig0igabg.jpg
                val nickName: String, // 迪莫不卖萌
                val level: Int, // 1
                val content: String, // [兔子]
                val ctime: Long, // 1532588941000
                val isReply: Boolean, // false
                val badgePicPath: String, // /mediasource/badge/small/mxjj_1_s.png
                val ugcBadgePicPath: String // /mediasource/badge/small/gsxtm_1_s.png
        )
    }
}