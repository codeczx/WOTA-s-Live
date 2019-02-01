package io.github.wotaslive

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v4.app.Fragment
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.previewlibrary.loader.IZoomMediaLoader
import com.previewlibrary.loader.MySimpleTarget

class ImageLoader : IZoomMediaLoader {
    override fun clearMemory(c: Context) {
        GlideApp.get(c).clearMemory()
    }

    override fun onStop(context: Fragment) {
        GlideApp.with(context).onStop()
    }

    override fun displayImage(context: Fragment, path: String, imageView: ImageView?, simpleTarget: MySimpleTarget) {
        imageView?.let {
            GlideApp.with(context)
                    .asBitmap()
                    .load(path)
                    .error(R.drawable.ic_error)
                    .dontAnimate()
                    .listener(object : RequestListener<Bitmap> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                            simpleTarget.onLoadFailed(null)
                            return false
                        }

                        override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            simpleTarget.onResourceReady()
                            return false
                        }
                    })
                    .into(imageView)
        }
    }

    override fun displayGifImage(context: Fragment, path: String, imageView: ImageView?, simpleTarget: MySimpleTarget) {
        imageView?.let {
            GlideApp.with(context)
                    .load(path)
                    .error(R.drawable.ic_error)
                    .dontAnimate()
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            simpleTarget.onLoadFailed(null)
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            simpleTarget.onResourceReady()
                            return false
                        }
                    })
                    .into(imageView)
        }
    }
}