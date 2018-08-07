package io.github.wotaslive.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.PopupWindow
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ScreenUtils
import com.github.chrisbanes.photoview.PhotoView
import io.github.wotaslive.R
import io.github.wotaslive.utils.loadImage
import io.github.wotaslive.utils.saveToLocal

class PhotoWindow(private val context: Context, private val url: String) : PopupWindow() {

    fun show(anchor: View) {
        val photo = PhotoView(context)
        width = ScreenUtils.getScreenWidth()
        height = ScreenUtils.getScreenHeight() - BarUtils.getStatusBarHeight()
        contentView = photo
        isFocusable = true
        isTouchable = true
        isOutsideTouchable = true
        animationStyle = R.style.popup_anim_style
        setBackgroundDrawable(ColorDrawable(Color.WHITE))
        photo.loadImage(url)
        photo.setOnClickListener {
            dismiss()
        }
        photo.setOnLongClickListener { _ ->
            Snackbar.make(photo, R.string.pic_save, Snackbar.LENGTH_LONG)
                    .setAction(android.R.string.ok) {
                        photo.saveToLocal(url)
                    }
                    .show()
            return@setOnLongClickListener true
        }
        showAsDropDown(anchor)
    }
}