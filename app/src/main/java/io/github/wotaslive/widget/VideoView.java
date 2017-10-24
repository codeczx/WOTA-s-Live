package io.github.wotaslive.widget;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
	private int mBrightnessMode;

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
		setFullScreen();

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

	private void keepScreenOn() {
		mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	private void clearKeepScreenOn() {
		mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	private void setFullScreen() {
		mActivity.getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_FULLSCREEN
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
						| View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
	}

	private void setUpBrightness() {
		// 检查当前亮度模式，设置为手动模式
		ContentResolver contentResolver = mContext.getContentResolver();
		try {
			int brightnessMode = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE);
			mBrightnessMode = brightnessMode;
			if (brightnessMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
				Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
						Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
			}
		} catch (Settings.SettingNotFoundException e) {
			e.printStackTrace();
		}
		// 用window的方式来调整亮度，lp.screenBrightness的亮度默认是-1，需要初始化为当前系统亮度
		int defVal = 125;
		int brightnessVal = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, defVal);
		Window window = ((Activity) mContext).getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.screenBrightness = brightnessVal * (1f / 255);
		window.setAttributes(lp);
	}

	private void checkSystemWritePermission() {
		boolean retVal;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			retVal = Settings.System.canWrite(mContext);
			if (retVal) {
				openVideo();
			} else {
				Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
				intent.setData(Uri.parse("package:" + mContext.getPackageName()));
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(intent);
			}
		}
	}

	private void resumeBrightnessMode() {
		ContentResolver contentResolver = mContext.getContentResolver();
		Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, mBrightnessMode);
	}

	private SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback() {
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			mSurfaceHolder = holder;
			if (mPlayer == null) {
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
		checkSystemWritePermission();
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
		setUpBrightness();
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
		} else {
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
		clearKeepScreenOn();
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
		keepScreenOn();
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
