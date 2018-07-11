package io.github.wotaslive.utils

import android.databinding.BindingAdapter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import io.github.wotaslive.GlideApp
import io.github.wotaslive.R
import io.github.wotaslive.data.AppRepository

private fun checkUrl(originUrl: String): String {
    var url = originUrl
    if (url.startsWith("http")) {
        // 网易NOS的证书Glide不认可，替换成http
        if (url.startsWith("https://nos.netease.com")) {
            url = url.replace("https", "http")
        }
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?"))
        }
    } else {
        url = AppRepository.IMG_BASE_URL + url
    }
    return url
}

@BindingAdapter("avatarUrl")
fun ImageView.loadAvatar(url: String) {
    if (TextUtils.isEmpty(url)) return
    GlideApp.with(context)
            .load(checkUrl(url))
            .into(this)
}

@BindingAdapter("roundImageUrl")
fun ImageView.loadRoundImageUrl(url: String) {
    if (TextUtils.isEmpty(url)) return
    val size = resources.getDimensionPixelSize(R.dimen.room_max_img_width)
    GlideApp.with(context)
            .load(checkUrl(url))
            .override(size, size)
            .transform(RoundedCorners(resources.getDimensionPixelOffset(R.dimen.room_radius)))
            .into(this)
}

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String) {
    if (TextUtils.isEmpty(url)) return
    if (url.startsWith("#")) {
        GlideApp.with(context)
                .load(ColorDrawable(Color.parseColor(url)))
                .into(this)
    } else {
        GlideApp.with(context)
                .load(checkUrl(url))
                .dontAnimate()
                .into(this)
    }
}