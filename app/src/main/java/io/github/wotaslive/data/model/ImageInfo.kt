package io.github.wotaslive.data.model

import android.graphics.Rect
import com.previewlibrary.enitity.IThumbViewInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageInfo(var urlStr: String, var videoUrlStr: String?, var rect: Rect?) : IThumbViewInfo {
    override fun getUrl(): String {
        return urlStr
    }

    override fun getVideoUrl(): String? {
        return videoUrlStr
    }

    override fun getBounds(): Rect? {
        return rect
    }
}