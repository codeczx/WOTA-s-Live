package io.github.wotaslive.widget

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.Observable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.MediaController
import android.widget.PopupWindow
import io.github.wotaslive.databinding.MediaControllerBinding

class MediaController(context: Context?) : FrameLayout(context), IMediaController {
    private var mAnchor: View? = null
    private var mRoot: View? = null
    private val mContext = context
    private val mWindow: PopupWindow = PopupWindow(context)
    private var mShowing = false
    private var mPlayer: MediaController.MediaPlayerControl? = null
    private lateinit var viewDataBinding: MediaControllerBinding
    private var viewModel: MediaControlViewModel = MediaControlViewModel()

    init {
        mWindow.isFocusable = false
        mWindow.setBackgroundDrawable(null)
        mWindow.isOutsideTouchable = true
        requestFocus()
    }

    private fun makeControllerView(): View? {
        return MediaControllerBinding.inflate(mContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)?.also {
            viewDataBinding = it
            viewDataBinding.viewModel = viewModel
        }?.root
    }

    override fun isShowing(): Boolean {
        return mShowing
    }

    override fun hide() {
        mAnchor?.let {
            return
        }
        if (mShowing) {
            mWindow.dismiss()
            mShowing = false
        }
    }

    override fun setAnchorView(view: View) {
        mAnchor = view
        removeAllViews()
        mRoot = makeControllerView()
        mWindow.contentView = mRoot
        mWindow.width = LayoutParams.MATCH_PARENT
        mWindow.height = LayoutParams.MATCH_PARENT
    }

    override fun setMediaPlayer(player: MediaController.MediaPlayerControl) {
        mPlayer = player
        viewModel.progress.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                player.seekTo(viewModel.progress.get())
                viewModel.setCurTime(player.currentPosition.toLong())
            }
        })
    }

    override fun show(timeout: Int) {
        if (!mShowing && mAnchor != null) {
            val location = IntArray(2)
            mAnchor?.getLocationInWindow(location)
            mWindow.animationStyle = android.R.style.Animation
            mWindow.showAtLocation(mAnchor, Gravity.TOP, location[0], location[1] + (mAnchor?.width
                    ?: 0))
            mPlayer?.let {
                viewModel.max.set(1000)
                viewModel.progress.set(if (it.duration == 0) 0 else it.currentPosition * 1000 / it.duration)
                viewModel.setCurTime(it.currentPosition.toLong())
                viewModel.setTotalTime(it.duration.toLong())
            }
            mShowing = true
        }
    }

    override fun show() {
        show(DEFAULT_TIMEOUT)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN ->
                show(0)
            MotionEvent.ACTION_UP ->
                show(DEFAULT_TIMEOUT)
            MotionEvent.ACTION_CANCEL ->
                hide()
        }
        return true
    }

    override fun onTrackballEvent(event: MotionEvent?): Boolean {
        show(DEFAULT_TIMEOUT)
        return false
    }

    companion object {
        const val DEFAULT_TIMEOUT = 3000
    }
}