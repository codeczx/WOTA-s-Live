package io.github.wotaslive.data.model


data class DynamicPictureInfo(
        val status: Int,
        val message: String,
        val content: Content) {
    data class Content(val data: List<Data>) {
        data class Data(val picId: Int,
                        val width: Int,
                        val height: Int,
                        val size: Int,
                        val filePath: String,
                        val ctime: Long)
    }
}