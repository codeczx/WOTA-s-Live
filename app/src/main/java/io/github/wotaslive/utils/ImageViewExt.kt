package io.github.wotaslive.utils

import android.widget.ImageView
import com.bumptech.glide.request.target.Target
import io.github.wotaslive.GlideApp
import io.github.wotaslive.data.AppRepository

fun ImageView.loadImage(url: String) {
    url.let {
        GlideApp.with(context)
                .load(AppRepository.IMG_BASE_URL + it)
                .override(Target.SIZE_ORIGINAL)
                .into(this)
    }
}