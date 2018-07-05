package io.github.wotaslive.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

fun Context.setClipboard(text: String) {
    val cmb = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    cmb.let {
        val clipData = ClipData.newPlainText(null, text)
        it.primaryClip = clipData
    }
}