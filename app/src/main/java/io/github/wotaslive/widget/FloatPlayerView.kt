package io.github.wotaslive.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import io.github.wotaslive.R

class FloatPlayerView : FrameLayout, View.OnClickListener {
    private lateinit var floatingVideo: FloatingVideo
    private var lastTouchX = 0
    private var lastTouchY = 0
    var windowLayoutParams: WindowManager.LayoutParams? = null
    var windowManager: WindowManager? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        floatingVideo = FloatingVideo(context)

        val layoutParams = FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        layoutParams.gravity = Gravity.CENTER
        addView(floatingVideo, layoutParams)

        floatingVideo.findViewById<View>(R.id.iv_close).setOnClickListener(this)

        floatingVideo.setIsTouchWiget(false)
    }

    fun setUp(url: String?, title: String?) {
        floatingVideo.setUp(url, false, title)
        floatingVideo.startButton.performClick()
    }

    override fun onClick(v: View?) {
        floatingVideo.release()
        windowManager?.removeView(this)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                x = event.rawX
                y = event.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                windowLayoutParams?.let {
                    it.x += (event.rawX - x).toInt()
                    it.y += (event.rawX - y).toInt()
                    windowManager?.updateViewLayout(this, it)
                }
            }

        }
        return super.onTouchEvent(event)
    }
}