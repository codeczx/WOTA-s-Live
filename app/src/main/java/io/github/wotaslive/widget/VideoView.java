package io.github.wotaslive.widget;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.wotaslive.R;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Author codeczx
 * Created at 2017/8/14
 */

public class VideoView extends FrameLayout implements MediaPlayerControl {

	@BindView(R.id.surface_view)
	SurfaceView surfaceView;

	private Context mContext;
	private Activity mActivity;
	private MediaViewControl mMediaViewControl;
	private IjkMediaPlayer mPlayer;
	private SurfaceHolder mSurfaceHolder;
	private ViewGroup.LayoutParams mLayoutParams;
	private String mUrl;
	private boolean mIsLive;
	private long mCurrentPosition;
	private int mWidth;
	private int mHeight;

	public VideoView(@NonNull Context context) {
		this(context, null);
	}

	public VideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public VideoView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		mActivity = (Activity) mContext;
		initView();
	}

	private void initView() {
		LayoutInflater.from(mContext).inflate(R.layout.video_view, this);
		ButterKnife.bind(this);
		MediaController mMediaController = new MediaController(mContext);
		addView(mMediaController);
		mMediaController.setVideoController(this);
		mMediaViewControl = mMediaController;
		surfaceView.getHolder().addCallback(mSHCallback);
	}

	private static final String TAG = "VideoView";
	private SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback() {
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			mSurfaceHolder = holder;
			if (mPlayer == null) {
				Log.i(TAG, "surfaceCreated: ");
				openVideo();
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {

		}
	};

	private IMediaPlayer.OnPreparedListener mPrepareListener = new IMediaPlayer.OnPreparedListener() {
		@Override
		public void onPrepared(IMediaPlayer iMediaPlayer) {
			mPlayer.setSurface(surfaceView.getHolder().getSurface());
			if (!mIsLive) {
				mPlayer.seekTo(mCurrentPosition);
			}
			start();
			mMediaViewControl.startPolling();
		}
	};

	private IMediaPlayer.OnCompletionListener mCompletionListener = iMediaPlayer -> {
		mCurrentPosition = 0;
		pause();
	};

	public void setVideoUrl(String url, boolean isLive) {
		mUrl = url;
		mIsLive = isLive;
		openVideo();
	}

	private void openVideo() {
		if (mSurfaceHolder == null || mUrl == null) {
			return;
		}
		if (mPlayer != null) {
			mPlayer.reset();
		}
		mPlayer = new IjkMediaPlayer();
		try {
			mPlayer.setDataSource(mUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mPlayer.setOnPreparedListener(mPrepareListener);
		mPlayer.setOnCompletionListener(mCompletionListener);
		mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
		mPlayer.prepareAsync();
	}

	@Override
	public void changeOrientation() {
		AppCompatActivity activity = (AppCompatActivity) mContext;
		int orientation = activity.getRequestedOrientation();
		// The default value of orientation is SCREEN_ORIENTATION_UNSPECIFIED.
		if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			mLayoutParams.width = mWidth;
			mLayoutParams.height = mHeight;
			setLayoutParams(mLayoutParams);
			mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		else {
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			mLayoutParams = getLayoutParams();
			mWidth = mLayoutParams.width;
			mHeight = mLayoutParams.height;
			mLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
			mLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
			setLayoutParams(mLayoutParams);
			mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
	}

	private void release(boolean clearState) {
		if (mPlayer != null) {
			mCurrentPosition = getCurrentPosition();
			mPlayer.release();
			mPlayer = null;
		}
		if (clearState) {
			mUrl = null;
			mCurrentPosition = 0;
		}
		mMediaViewControl.cancelPolling();
	}

	@Override
	public boolean isPlaying() {
		return mPlayer != null && mPlayer.isPlaying();
	}

	@Override
	public void start() {
		if (mPlayer != null) {
			mPlayer.start();
			mMediaViewControl.start();
		}
	}

	@Override
	public void pause() {
		if (mPlayer != null) {
			mPlayer.pause();
			mMediaViewControl.pause();
		}
	}

	@Override
	public void stop() {
		release(false);
	}

	@Override
	public void release() {
		release(true);
	}

	public void restart() {
		openVideo();
	}

	@Override
	public boolean isLive() {
		return mIsLive;
	}

	@Override
	public void seekTo(long progress) {
		if (mPlayer != null) {
			mPlayer.seekTo(progress);
		}
	}

	@Override
	public void close() {
		mActivity.onBackPressed();
	}

	@Override
	public long getDuration() {
		return mPlayer == null ? 0 : mPlayer.getDuration();
	}

	@Override
	public long getCurrentPosition() {
		return mPlayer == null ? 0 : mPlayer.getCurrentPosition();
	}
}
