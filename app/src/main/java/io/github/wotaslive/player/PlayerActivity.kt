package io.github.wotaslive.player

import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.PixelFormat
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import io.github.wotaslive.Constants
import io.github.wotaslive.GlideApp
import io.github.wotaslive.R
import io.github.wotaslive.data.model.LiveInfo
import io.github.wotaslive.databinding.ActPlayerBinding
import io.github.wotaslive.utils.checkUrl
import io.github.wotaslive.widget.FloatPlayerView

class PlayerActivity : AppCompatActivity() {

    private lateinit var viewDataBinding: ActPlayerBinding
    private lateinit var room: LiveInfo.ContentBean.RoomBean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        room = intent.getParcelableExtra<LiveInfo.ContentBean.RoomBean>(Constants.ROOM)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.act_player)
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        room.picPath?.let {
            val imgArr = it.split(",")
            if (!imgArr.isEmpty()) {
                GlideApp.with(this@PlayerActivity)
                        .load(checkUrl(imgArr[0]))
                        .dontAnimate()
                        .into(imageView)
            }
        }
        GSYVideoOptionBuilder()
                .setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(true)
                .setNeedLockFull(false)
                .setUrl(room.streamPath)
                .setCacheWithPlay(false)
                .setVideoTitle(room.title)
                .setVideoAllCallBack(object : GSYSampleCallBack() {
                    override fun onPrepared(url: String?, vararg objects: Any?) {
                        super.onPrepared(url, *objects)
                        viewDataBinding.player.startButton.performClick()
                    }
                })
                .build(viewDataBinding.player)

        with(viewDataBinding.player) {
            fullscreenButton.setOnClickListener {
                if (checkFloatPermission()) {
                    showFloat()
                } else {
                    requestPermission()
                }
            }
            fullscreenButton.visibility = View.GONE
            startButton.performClick()
        }
    }

    private fun showFloat() {
        val floatPlayerView = FloatPlayerView(applicationContext)
        floatPlayerView.setUp(room.streamPath, room.title)
        floatPlayerView.setOnClickListener {
            PlayerActivity.startPlayerActivity(floatPlayerView.context, room)
            windowManager.removeView(it)
        }
        val size = Point()
        windowManager.defaultDisplay.getSize(size)
        val width = size.x * 0.3
        val height = size.y * 0.3
        val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else
            WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        val layoutParams = WindowManager.LayoutParams(width.toInt(), height.toInt(), 0, 0, type,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE.or(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL),
                PixelFormat.TRANSLUCENT)
        windowManager.addView(floatPlayerView, layoutParams)
        floatPlayerView.windowLayoutParams = layoutParams
        floatPlayerView.windowManager = windowManager
        finish()
    }

    private fun checkFloatPermission(): Boolean {
        val appOpsMgr = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOpsMgr.checkOpNoThrow("android:system_alert_window", Process.myUid(), packageName)
        return mode == AppOpsManager.MODE_ALLOWED || mode == AppOpsManager.MODE_IGNORED
    }

    private fun requestPermission() {
        val intent = Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION")
        intent.data = Uri.parse("package:$packageName")
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            showFloat()
        }
    }

    override fun onBackPressed() {
        viewDataBinding.player.setVideoAllCallBack(null)
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        viewDataBinding.player.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.player.onVideoResume()
    }

    override fun onDestroy() {
        GSYVideoManager.releaseAllVideos()
        super.onDestroy()
    }

    companion object {

        fun startPlayerActivity(context: Context?, room: LiveInfo.ContentBean.RoomBean) {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra(Constants.ROOM, room)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(intent)
        }
    }
}
