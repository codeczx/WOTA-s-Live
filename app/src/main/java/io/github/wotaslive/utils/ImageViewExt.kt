package io.github.wotaslive.utils

import android.content.Intent
import android.databinding.BindingAdapter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Environment
import android.text.TextUtils
import android.widget.ImageView
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import io.github.wotaslive.App
import io.github.wotaslive.GlideApp
import io.github.wotaslive.R
import io.github.wotaslive.data.AppRepository
import io.reactivex.schedulers.Schedulers

fun checkUrl(originUrl: String): String {
    var url = originUrl
    if (url.startsWith("http")) {
        // 网易NOS的证书Glide不认可，替换成http
//        if (url.startsWith("https://nos.netease.com")) {
//            url = url.replace("https", "http")
//        }
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?"))
        }
    }
//    else if (url.endsWith(".gif")) {
//        url = AppRepository.IMG_BASE_URL + url.substring(url.indexOf("/") + 1)
//    }
    else {
        url = AppRepository.IMG_BASE_URL + url
    }
    return url
}

@BindingAdapter("avatarUrl")
fun ImageView.loadAvatar(url: String?) {
    if (TextUtils.isEmpty(url)) return
    GlideApp.with(context)
            .load(checkUrl(url!!))
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .centerCrop()
            .dontAnimate()
            .into(this)
}

@BindingAdapter("thumbUrl")
fun ImageView.loadThumb(url: String?) {
    if (TextUtils.isEmpty(url)) return
    GlideApp.with(context)
            .load(checkUrl("/resize_250X250$url"))
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .centerCrop()
            .dontAnimate()
            .into(this)
}

@BindingAdapter("roundImageUrl")
fun ImageView.loadRoundImageUrl(url: String?) {
    if (TextUtils.isEmpty(url)) return
    val size = resources.getDimensionPixelSize(R.dimen.room_max_img_width)
    GlideApp.with(context)
            .load(checkUrl(url!!))
            .override(size, size)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .dontAnimate()
            .transform(RoundedCorners(resources.getDimensionPixelOffset(R.dimen.room_radius)))
            .into(this)
}

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    if (TextUtils.isEmpty(url)) return
    when {
        url!!.endsWith("gif") -> GlideApp.with(context)
                .asBitmap()
                .load(checkUrl(url))
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .dontAnimate()
                .into(this)
        else -> GlideApp.with(context)
                .load(checkUrl(url))
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .dontAnimate()
                .into(this)
    }
}

@BindingAdapter("bgUrl")
fun ImageView.loadBg(url: String?) {
    if (TextUtils.isEmpty(url)) return
    when {
        url!!.startsWith("#") -> GlideApp.with(context)
                .load(ColorDrawable(Color.parseColor(url)))
                .dontAnimate()
                .into(this)
        else -> GlideApp.with(context)
                .load(checkUrl(url))
                .dontAnimate()
                .into(this)
    }
}

fun ImageView.saveToLocal(url: String) {
    val worker = Schedulers.io().createWorker()
    worker.schedule {
        val file = GlideApp.with(context)
                .downloadOnly()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .load(checkUrl(url))
                .submit()
                .get()
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val fileName = "${path.absolutePath}/${System.currentTimeMillis()}.jpg"
        FileUtils.copyFile(file.absolutePath, fileName, null)
        ToastUtils.showShort("已保存到:$fileName")
        App.instance.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(path)))
    }
}