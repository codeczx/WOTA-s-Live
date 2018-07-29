package io.github.wotaslive.data.model


data class DynamicInfo(
        val status: Int,
        val message: String,
        val content: Content) {
    data class Content(val data: List<Data>) {
        data class Data(val dynamicId: Int,
                        val memberId: Int,
                        val ctime: Long,
                        val content: String,
                        val praise: Int,
                        val share: Int,
                        val quote: Int,
                        val comment: Int,
                        val picture: List<Picture>?) {
            data class Picture(
                    val picId: Int,
                    val width: Int,
                    val height: Int,
                    val size: Int,
                    val filePath: String,
                    val ctime: Long
            )
        }
    }
}