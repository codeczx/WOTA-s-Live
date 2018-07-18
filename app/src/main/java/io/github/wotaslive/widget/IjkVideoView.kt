/*
 * Copyright (C) 2015 Bilibili
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.wotaslive.widget

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.support.v7.app.AlertDialog
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.MediaController
import android.widget.TextView
import io.github.wotaslive.MediaPlayerService
import io.github.wotaslive.R
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import java.io.IOException

class IjkVideoView : FrameLayout, MediaController.MediaPlayerControl {
    // settable by the client
    private var mUri: Uri? = null
    private var mHeaders: Map<String, String>? = null

    // mCurrentState is a VideoView object's current state.
    // mTargetState is the state that a method caller intends to reach.
    // For instance, regardless the VideoView object's current state,
    // calling pause() intends to bring the object to a target state
    // of STATE_PAUSED.
    private var mCurrentState = STATE_IDLE
    private var mTargetState = STATE_IDLE

    // All the stuff we need for playing and showing a video
    private var mSurfaceHolder: IRenderView.ISurfaceHolder? = null
    private var mMediaPlayer: IMediaPlayer? = null
    // private int         mAudioSession;
    private var mVideoWidth: Int = 0
    private var mVideoHeight: Int = 0
    private var mSurfaceWidth: Int = 0
    private var mSurfaceHeight: Int = 0
    private var mVideoRotationDegree: Int = 0
    private var mMediaController: IMediaController? = null
    private var mOnCompletionListener: IMediaPlayer.OnCompletionListener? = null
    private var mOnPreparedListener: IMediaPlayer.OnPreparedListener? = null
    private var mCurrentBufferPercentage: Int = 0
    private var mOnErrorListener: IMediaPlayer.OnErrorListener? = null
    private var mOnInfoListener: IMediaPlayer.OnInfoListener? = null
    private var mSeekWhenPrepared: Int = 0  // recording the seek position while preparing
    private val mCanPause = true
    private val mCanSeekBack = true
    private val mCanSeekForward = true

    /** Subtitle rendering widget overlaid on top of the video.  */
    // private RenderingWidget mSubtitleWidget;

    /**
     * Listener for changes to subtitle data, used to redraw when needed.
     */
    // private RenderingWidget.OnChangedListener mSubtitlesChangedListener;

    private var mAppContext: Context? = null
    private var mRenderView: IRenderView? = null
    private var mVideoSarNum: Int = 0
    private var mVideoSarDen: Int = 0

    private var mPrepareStartTime: Long = 0
    private var mPrepareEndTime: Long = 0

    private var mSeekStartTime: Long = 0
    private var mSeekEndTime: Long = 0

    private var subtitleDisplay: TextView? = null

    private var mSizeChangedListener: IMediaPlayer.OnVideoSizeChangedListener = IMediaPlayer.OnVideoSizeChangedListener { mp, _, _, _, _ ->
        mVideoWidth = mp.videoWidth
        mVideoHeight = mp.videoHeight
        mVideoSarNum = mp.videoSarNum
        mVideoSarDen = mp.videoSarDen
        if (mVideoWidth != 0 && mVideoHeight != 0) {
            mRenderView?.let {
                it.setVideoSize(mVideoWidth, mVideoHeight)
                it.setVideoSampleAspectRatio(mVideoSarNum, mVideoSarDen)
            }
            // REMOVED: getHolder().setFixedSize(mVideoWidth, mVideoHeight);
            requestLayout()
        }
    }

    private var mPreparedListener: IMediaPlayer.OnPreparedListener = IMediaPlayer.OnPreparedListener { mp ->
        mPrepareEndTime = System.currentTimeMillis()
        mCurrentState = STATE_PREPARED

        // Get the capabilities of the player for this stream
        // REMOVED: Metadata

        mOnPreparedListener?.onPrepared(mMediaPlayer)
        mMediaController?.setEnabled(true)
        mVideoWidth = mp.videoWidth
        mVideoHeight = mp.videoHeight

        val seekToPosition = mSeekWhenPrepared  // mSeekWhenPrepared may be changed after seekTo() call
        if (seekToPosition != 0) {
            seekTo(seekToPosition)
        }
        if (mVideoWidth != 0 && mVideoHeight != 0) {
            //Log.i("@@@@", "video size: " + mVideoWidth +"/"+ mVideoHeight);
            // REMOVED: getHolder().setFixedSize(mVideoWidth, mVideoHeight);
            mRenderView?.let {
                it.setVideoSize(mVideoWidth, mVideoHeight)
                it.setVideoSampleAspectRatio(mVideoSarNum, mVideoSarDen)
                if (it.shouldWaitForResize() || mSurfaceWidth == mVideoWidth && mSurfaceHeight == mVideoHeight) {
                    // We didn't actually change the size (it was already at the size
                    // we need), so we won't get a "surface changed" callback, so
                    // start the video here instead of in the callback.
                    if (mTargetState == STATE_PLAYING) {
                        start()
                        mMediaController?.show()
                    } else if (!isPlaying && (seekToPosition != 0 || currentPosition > 0)) {
                        // Show the media controls when we're paused into a video and make 'em stick.
                        mMediaController?.show(0)
                    }
                }
            }
        } else {
            // We don't know the video size yet, but should start anyway.
            // The video size might be reported to us later.
            if (mTargetState == STATE_PLAYING) {
                start()
            }
        }
    }

    private val mCompletionListener = IMediaPlayer.OnCompletionListener {
        mCurrentState = STATE_PLAYBACK_COMPLETED
        mTargetState = STATE_PLAYBACK_COMPLETED
        mMediaController?.hide()
        mOnCompletionListener?.onCompletion(mMediaPlayer)
    }

    private val mInfoListener = IMediaPlayer.OnInfoListener { mp, arg1, arg2 ->
        mOnInfoListener?.onInfo(mp, arg1, arg2)
        when (arg1) {
            IMediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING -> Log.d(TAG, "MEDIA_INFO_VIDEO_TRACK_LAGGING:")
            IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START -> Log.d(TAG, "MEDIA_INFO_VIDEO_RENDERING_START:")
            IMediaPlayer.MEDIA_INFO_BUFFERING_START -> Log.d(TAG, "MEDIA_INFO_BUFFERING_START:")
            IMediaPlayer.MEDIA_INFO_BUFFERING_END -> Log.d(TAG, "MEDIA_INFO_BUFFERING_END:")
            IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH -> Log.d(TAG, "MEDIA_INFO_NETWORK_BANDWIDTH: $arg2")
            IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING -> Log.d(TAG, "MEDIA_INFO_BAD_INTERLEAVING:")
            IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE -> Log.d(TAG, "MEDIA_INFO_NOT_SEEKABLE:")
            IMediaPlayer.MEDIA_INFO_METADATA_UPDATE -> Log.d(TAG, "MEDIA_INFO_METADATA_UPDATE:")
            IMediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE -> Log.d(TAG, "MEDIA_INFO_UNSUPPORTED_SUBTITLE:")
            IMediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT -> Log.d(TAG, "MEDIA_INFO_SUBTITLE_TIMED_OUT:")
            IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED -> {
                mVideoRotationDegree = arg2
                Log.d(TAG, "MEDIA_INFO_VIDEO_ROTATION_CHANGED: $arg2")
                mRenderView?.setVideoRotation(arg2)
            }
            IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START -> Log.d(TAG, "MEDIA_INFO_AUDIO_RENDERING_START:")
        }
        true
    }

    private val mErrorListener = IMediaPlayer.OnErrorListener { _, framework_err, impl_err ->
        Log.d(TAG, "Error: $framework_err,$impl_err")
        mCurrentState = STATE_ERROR
        mTargetState = STATE_ERROR
        mMediaController?.hide()

        /* If an error handler has been supplied, use it and finish. */
        if (mOnErrorListener?.onError(mMediaPlayer, framework_err, impl_err) == true) {
            return@OnErrorListener true
        }

        /* Otherwise, pop up an error dialog so the user knows that
					 * something bad has happened. Only try and pop up the dialog
					 * if we're attached to a window. When we're going away and no
					 * longer have a window, don't bother showing the user an error.
					 */
        windowToken?.let {
            val messageId: Int = if (framework_err == MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK) {
                R.string.VideoView_error_text_invalid_progressive_playback
            } else {
                R.string.VideoView_error_text_unknown
            }

            AlertDialog.Builder(context)
                    .setMessage(messageId)
                    .setPositiveButton(R.string.VideoView_error_button
                    ) { _, _ ->
                        /* If we get here, there is no onError listener, so
												 * at least inform them that the video is over.
												 */
                        mOnCompletionListener?.onCompletion(mMediaPlayer)
                    }
                    .setCancelable(false)
                    .show()
        }
        true
    }

    private val mBufferingUpdateListener = IMediaPlayer.OnBufferingUpdateListener { _, percent -> mCurrentBufferPercentage = percent }

    private val mSeekCompleteListener = IMediaPlayer.OnSeekCompleteListener {
        mSeekEndTime = System.currentTimeMillis()
        //			mHudViewHolder.updateSeekCost(mSeekEndTime - mSeekStartTime);
    }

    private val mOnTimedTextListener = IMediaPlayer.OnTimedTextListener { _, text ->
        subtitleDisplay?.text = text?.text
    }

    private var mSHCallback: IRenderView.IRenderCallback = object : IRenderView.IRenderCallback {
        override fun onSurfaceChanged(holder: IRenderView.ISurfaceHolder, format: Int, width: Int, height: Int) {
            if (holder.renderView !== mRenderView) {
                Log.e(TAG, "onSurfaceChanged: unmatched render callback\n")
                return
            }

            mSurfaceWidth = width
            mSurfaceHeight = height
            val isValidState = mTargetState == STATE_PLAYING
            val hasValidSize = mRenderView?.shouldWaitForResize() == false || mVideoWidth == width && mVideoHeight == height
            if (mMediaPlayer != null && isValidState && hasValidSize) {
                if (mSeekWhenPrepared != 0) {
                    seekTo(mSeekWhenPrepared)
                }
                start()
            }
        }

        override fun onSurfaceCreated(holder: IRenderView.ISurfaceHolder, width: Int, height: Int) {
            if (holder.renderView !== mRenderView) {
                Log.e(TAG, "onSurfaceCreated: unmatched render callback\n")
                return
            }

            mSurfaceHolder = holder
            if (mMediaPlayer != null)
                bindSurfaceHolder(mMediaPlayer, holder)
            else
                openVideo()
        }

        override fun onSurfaceDestroyed(holder: IRenderView.ISurfaceHolder) {
            if (holder.renderView !== mRenderView) {
                Log.e(TAG, "onSurfaceDestroyed: unmatched render callback\n")
                return
            }

            // after we return from this we can't use the surface any more
            mSurfaceHolder = null
            // REMOVED: if (mMediaController != null) mMediaController.hide();
            // REMOVED: release(true);
            releaseWithoutStop()
        }
    }

    private val isInPlaybackState: Boolean
        get() = mCurrentState != STATE_ERROR && mCurrentState != STATE_IDLE && mCurrentState != STATE_PREPARING
    private var mCurrentAspectRatioIndex = 1
    private var mCurrentAspectRatio = s_allAspectRatio[1]

    //-------------------------
    // Extend: Background
    //-------------------------

    val isBackgroundPlayEnabled = false

    constructor(context: Context) : super(context) {
        initVideoView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initVideoView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initVideoView(context)
    }

    // REMOVED: onMeasure
    // REMOVED: onInitializeAccessibilityEvent
    // REMOVED: onInitializeAccessibilityNodeInfo
    // REMOVED: resolveAdjustedSize

    private fun initVideoView(context: Context) {
        mAppContext = context.applicationContext

        initBackground()
        initRenders()

        mVideoWidth = 0
        mVideoHeight = 0
        // REMOVED: getHolder().addCallback(mSHCallback);
        // REMOVED: getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        isFocusable = true
        isFocusableInTouchMode = true
        requestFocus()
        // REMOVED: mPendingSubtitleTracks = new Vector<Pair<InputStream, MediaFormat>>();
        mCurrentState = STATE_IDLE
        mTargetState = STATE_IDLE

        subtitleDisplay = TextView(context)
        subtitleDisplay?.textSize = 24f
        subtitleDisplay?.gravity = Gravity.CENTER
        val layoutParamsTxt = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM)
        addView(subtitleDisplay, layoutParamsTxt)
    }

    private fun setRenderView(renderView: IRenderView?) {
        mRenderView?.let {
            mMediaPlayer?.setDisplay(null)

            val renderUIView = it.view
            it.removeRenderCallback(mSHCallback)
            removeView(renderUIView)
        }
        mRenderView = null

        if (renderView == null)
            return

        mRenderView = renderView
        renderView.setAspectRatio(mCurrentAspectRatio)
        if (mVideoWidth > 0 && mVideoHeight > 0)
            renderView.setVideoSize(mVideoWidth, mVideoHeight)
        if (mVideoSarNum > 0 && mVideoSarDen > 0)
            renderView.setVideoSampleAspectRatio(mVideoSarNum, mVideoSarDen)

        val renderUIView = mRenderView?.view
        val lp = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER)
        renderUIView?.layoutParams = lp
        addView(renderUIView)

        mRenderView?.addRenderCallback(mSHCallback)
        mRenderView?.setVideoRotation(mVideoRotationDegree)
    }

    /**
     * Sets video path.
     *
     * @param path the path of the video.
     */
    fun setVideoPath(path: String) {
        // 断网自动重新连接
        setVideoURI(Uri.parse("ijkhttphook:$path"), null)
    }

    /**
     * Sets video URI using specific headers.
     *
     * @param uri     the URI of the video.
     * @param headers the headers for the URI request.
     * Note that the cross domain redirection is allowed by default, but that can be
     * changed with key/value pairs through the headers parameter with
     * "android-allow-cross-domain-redirect" as the key and "0" or "1" as the value
     * to disallow or allow cross domain redirection.
     */
    private fun setVideoURI(uri: Uri, headers: Map<String, String>?) {
        mUri = uri
        mHeaders = headers
        mSeekWhenPrepared = 0
        openVideo()
        requestLayout()
        invalidate()
    }

    // REMOVED: addSubtitleSource
    // REMOVED: mPendingSubtitleTracks

    fun stopPlayback() {
        mMediaPlayer?.stop()
        mMediaPlayer?.release()
        mMediaPlayer = null
        mCurrentState = STATE_IDLE
        mTargetState = STATE_IDLE
        val am = mAppContext?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        am.abandonAudioFocus(null)
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun openVideo() {
        if (mUri == null || mSurfaceHolder == null) {
            // not ready for playback just yet, will try again later
            return
        }
        // we shouldn't clear the target state, because somebody might have
        // called start() previously
        release(false)

        val am = mAppContext?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)

        try {
            mMediaPlayer = createPlayer(0)

            // 断网自动重新连接
            mMediaPlayer?.let {
                with(it) {
                    (this as IjkMediaPlayer).setOnNativeInvokeListener { _, _ -> true }
                    setOnPreparedListener(mPreparedListener)
                    setOnVideoSizeChangedListener(mSizeChangedListener)
                    setOnCompletionListener(mCompletionListener)
                    setOnErrorListener(mErrorListener)
                    setOnInfoListener(mInfoListener)
                    setOnBufferingUpdateListener(mBufferingUpdateListener)
                    setOnSeekCompleteListener(mSeekCompleteListener)
                    setOnTimedTextListener(mOnTimedTextListener)
                    mCurrentBufferPercentage = 0
                    setDataSource(mAppContext, mUri, mHeaders)
                    bindSurfaceHolder(mMediaPlayer, mSurfaceHolder)
                    setAudioStreamType(AudioManager.STREAM_MUSIC)
                    setScreenOnWhilePlaying(true)
                    mPrepareStartTime = System.currentTimeMillis()
                    prepareAsync()
                }
            }

            // REMOVED: mPendingSubtitleTracks

            // we don't set the target state here either, but preserve the
            // target state that was there before.
            mCurrentState = STATE_PREPARING
            attachMediaController()
        } catch (ex: IOException) {
            Log.w(TAG, "Unable to open content: $mUri", ex)
            mCurrentState = STATE_ERROR
            mTargetState = STATE_ERROR
            mErrorListener.onError(mMediaPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0)
        } catch (ex: IllegalArgumentException) {
            Log.w(TAG, "Unable to open content: $mUri", ex)
            mCurrentState = STATE_ERROR
            mTargetState = STATE_ERROR
            mErrorListener.onError(mMediaPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0)
        }

    }

    fun setFullScreen(activity: Activity) {
        activity.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
    }

    fun setMediaController(controller: IMediaController) {
        mMediaController?.hide()
        mMediaController = controller
        attachMediaController()
    }

    private fun attachMediaController() {
        mMediaController?.let {
            it.setMediaPlayer(this)
            val anchorView = if (this.parent is View)
                this.parent as View
            else
                this
            it.setAnchorView(anchorView)
            it.setEnabled(isInPlaybackState)
        }
    }

    /**
     * Register a callback to be invoked when the media file
     * is loaded and ready to go.
     *
     * @param l The callback that will be run
     */
    fun setOnPreparedListener(l: IMediaPlayer.OnPreparedListener) {
        mOnPreparedListener = l
    }

    /**
     * Register a callback to be invoked when the end of a media file
     * has been reached during playback.
     *
     * @param l The callback that will be run
     */
    fun setOnCompletionListener(l: IMediaPlayer.OnCompletionListener) {
        mOnCompletionListener = l
    }

    /**
     * Register a callback to be invoked when an error occurs
     * during playback or setup.  If no listener is specified,
     * or if the listener returned false, VideoView will inform
     * the user of any errors.
     *
     * @param l The callback that will be run
     */
    fun setOnErrorListener(l: IMediaPlayer.OnErrorListener) {
        mOnErrorListener = l
    }

    /**
     * Register a callback to be invoked when an informational event
     * occurs during playback or setup.
     *
     * @param l The callback that will be run
     */
    fun setOnInfoListener(l: IMediaPlayer.OnInfoListener) {
        mOnInfoListener = l
    }

    // REMOVED: mSHCallback
    private fun bindSurfaceHolder(mp: IMediaPlayer?, holder: IRenderView.ISurfaceHolder?) {
        if (mp == null)
            return

        if (holder == null) {
            mp.setDisplay(null)
            return
        }

        holder.bindToMediaPlayer(mp)
    }

    fun releaseWithoutStop() {
        mMediaPlayer?.setDisplay(null)
    }

    /*
	 * release the media player in any state
	 */
    fun release(cleartargetstate: Boolean) {
        mMediaPlayer?.reset()
        mMediaPlayer?.release()
        // REMOVED: mPendingSubtitleTracks.clear();
        mCurrentState = STATE_IDLE
        if (cleartargetstate) {
            mTargetState = STATE_IDLE
        }
        val am = mAppContext!!.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        am.abandonAudioFocus(null)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (isInPlaybackState && mMediaController != null) {
            toggleMediaControlsVisiblity()
        }
        return false
    }

    override fun onTrackballEvent(ev: MotionEvent): Boolean {
        if (isInPlaybackState && mMediaController != null) {
            toggleMediaControlsVisiblity()
        }
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val isKeyCodeSupported = keyCode != KeyEvent.KEYCODE_BACK &&
                keyCode != KeyEvent.KEYCODE_VOLUME_UP &&
                keyCode != KeyEvent.KEYCODE_VOLUME_DOWN &&
                keyCode != KeyEvent.KEYCODE_VOLUME_MUTE &&
                keyCode != KeyEvent.KEYCODE_MENU &&
                keyCode != KeyEvent.KEYCODE_CALL &&
                keyCode != KeyEvent.KEYCODE_ENDCALL
        if (isInPlaybackState && isKeyCodeSupported && mMediaController != null) {
            if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE) {
                if (mMediaPlayer?.isPlaying == true) {
                    pause()
                    mMediaController?.show()
                } else {
                    start()
                    mMediaController?.hide()
                }
                return true
            } else if (keyCode == KeyEvent.KEYCODE_MEDIA_PLAY) {
                if (mMediaPlayer?.isPlaying == false) {
                    start()
                    mMediaController?.hide()
                }
                return true
            } else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP || keyCode == KeyEvent.KEYCODE_MEDIA_PAUSE) {
                if (mMediaPlayer?.isPlaying == true) {
                    pause()
                    mMediaController?.show()
                }
                return true
            } else {
                toggleMediaControlsVisiblity()
            }
        }

        return super.onKeyDown(keyCode, event)
    }

    private fun toggleMediaControlsVisiblity() {
        if (mMediaController?.isShowing() == true) {
            mMediaController?.hide()
        } else {
            mMediaController?.show()
        }
    }

    override fun start() {
        if (isInPlaybackState) {
            mMediaPlayer?.start()
            mCurrentState = STATE_PLAYING
        }
        mTargetState = STATE_PLAYING
    }

    override fun pause() {
        if (isInPlaybackState) {
            if (mMediaPlayer?.isPlaying == true) {
                mMediaPlayer?.pause()
                mCurrentState = STATE_PAUSED
            }
        }
        mTargetState = STATE_PAUSED
    }

    fun suspend() {
        release(false)
    }

    fun resume() {
        openVideo()
    }

    override fun getDuration(): Int {
        return if (isInPlaybackState) {
            mMediaPlayer?.duration?.toInt() ?: 0
        } else -1

    }

    override fun getCurrentPosition(): Int {
        return if (isInPlaybackState) {
            mMediaPlayer?.currentPosition?.toInt() ?: 0
        } else 0
    }

    override fun seekTo(msec: Int) {
        if (isInPlaybackState) {
            mSeekStartTime = System.currentTimeMillis()
            mMediaPlayer?.seekTo(msec.toLong())
            mSeekWhenPrepared = 0
        } else {
            mSeekWhenPrepared = msec
        }
    }

    override fun isPlaying(): Boolean {
        return isInPlaybackState && (mMediaPlayer?.isPlaying ?: false)
    }

    override fun getBufferPercentage(): Int {
        return mCurrentBufferPercentage
    }

    override fun canPause(): Boolean {
        return mCanPause
    }

    override fun canSeekBackward(): Boolean {
        return mCanSeekBack
    }

    override fun canSeekForward(): Boolean {
        return mCanSeekForward
    }

    override fun getAudioSessionId(): Int {
        return 0
    }

    fun toggleAspectRatio(): Int {
        mCurrentAspectRatioIndex++
        mCurrentAspectRatioIndex %= s_allAspectRatio.size

        mCurrentAspectRatio = s_allAspectRatio[mCurrentAspectRatioIndex]
        mRenderView?.setAspectRatio(mCurrentAspectRatio)
        return mCurrentAspectRatio
    }

    private fun initRenders() {
        setRenderView(SurfaceRenderView(context))
    }

    private fun createPlayer(playerType: Int): IMediaPlayer {
        val ijkMediaPlayer: IjkMediaPlayer = IjkMediaPlayer()
        IjkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG)
        // 硬解
        with(ijkMediaPlayer) {
            setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1)
            setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", IjkMediaPlayer.SDL_FCC_RV32.toLong())
            setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1)
            setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0)
            setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "analyzeduration", 1)
            setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", 0)
            setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48)
        }
        return ijkMediaPlayer
    }

    private fun initBackground() {
        MediaPlayerService.intentToStart(context)
        MediaPlayerService.mediaPlayer?.let {
            mMediaPlayer = it
        }
    }

    fun enterBackground() {
        MediaPlayerService.mediaPlayer = mMediaPlayer
    }

    fun stopBackgroundPlay() {
        MediaPlayerService.mediaPlayer = null
    }

    companion object {
        private const val TAG = "IjkVideoView"
        // all possible internal states
        private const val STATE_ERROR = -1
        private const val STATE_IDLE = 0
        private const val STATE_PREPARING = 1
        private const val STATE_PREPARED = 2
        private const val STATE_PLAYING = 3
        private const val STATE_PAUSED = 4
        private const val STATE_PLAYBACK_COMPLETED = 5

        // REMOVED: getAudioSessionId();
        // REMOVED: onAttachedToWindow();
        // REMOVED: onDetachedFromWindow();
        // REMOVED: onLayout();
        // REMOVED: draw();
        // REMOVED: measureAndLayoutSubtitleWidget();
        // REMOVED: setSubtitleWidget();
        // REMOVED: getSubtitleLooper();

        //-------------------------
        // Extend: Aspect Ratio
        //-------------------------

        private val s_allAspectRatio = intArrayOf(IRenderView.AR_ASPECT_FIT_PARENT, IRenderView.AR_ASPECT_FILL_PARENT, IRenderView.AR_ASPECT_WRAP_CONTENT,
                // IRenderView.AR_MATCH_PARENT,
                IRenderView.AR_16_9_FIT_PARENT, IRenderView.AR_4_3_FIT_PARENT)
    }
}