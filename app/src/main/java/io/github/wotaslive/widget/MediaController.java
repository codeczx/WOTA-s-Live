package io.github.wotaslive.widget;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.wotaslive.R;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Author codeczx
 * Created at 2017/10/19
 */

public class MediaController extends FrameLayout implements MediaViewControl, SeekBar.OnSeekBarChangeListener, View.OnTouchListener {


	private static final int INTERVAL_MENU_HIDE = 5000;
	private static final int INTERVAL_SHOW_PROGRESS = 1000;
	private static final float ADJUST_PROGRESS_SPEED = 0.3f;
	private static final float ADJUST_VOLUME_SPEED = 2.0f;
	private static final double ADJUST_BRIGHTNESS_SPEED = 2.0f;

	private static final int SLIDE_TYPE_PROGRESS = 1;
	private static final int SLIDE_TYPE_VOLUME = 2;
	private static final int SLIDE_TYPE_BRIGHTNESS = 3;
	private static final int SLIDE_TYPE_NO_SET = 0;

	@BindView(R.id.cl_media_controller)
	ConstraintLayout clMediaController;
	@BindView(R.id.iv_play)
	ImageView ivPlay;
	@BindView(R.id.sb_video)
	SeekBar sbVideo;
	@BindView(R.id.iv_back)
	ImageView ivBack;
	@BindView(R.id.tv_current_position)
	TextView tvCurrentPosition;
	@BindView(R.id.tv_duration)
	TextView tvDuration;
	@BindView(R.id.tv_offset_preview)
	TextView tvOffsetPreview;
	@BindView(R.id.ll_preview_progress)
	LinearLayout llPreviewProgress;
	@BindView(R.id.tv_progress_preview)
	TextView tvProgressPreview;
	@BindView(R.id.sb_volume)
	SeekBar sbVolume;
	@BindView(R.id.ll_preview_volume)
	LinearLayout llPreviewVolume;
	@BindView(R.id.fl_media_controller)
	FrameLayout flMediaController;
	@BindView(R.id.iv_volume)
	ImageView mIvVolume;
	@BindView(R.id.sb_brightness)
	SeekBar mSbBrightness;
	@BindView(R.id.ll_preview_brightness)
	LinearLayout mLlPreviewBrightness;

	private Context mContext;
	private MediaPlayerControl mPlayer;
	private float mLastX;
	private float mLastY;
	private int mSlideType = SLIDE_TYPE_NO_SET;
	private int mPlayerOffset;
	private int mCurrentVolume;
	private AudioManager mAudioManager;
	private float mCurrentBrightness;

	private CompositeDisposable disposables;
	private Disposable timeDisposable;
	private Disposable progressDisposable;
	private Disposable controllerDisposable;

	public MediaController(Context context) {
		this(context, null);
	}

	public MediaController(@NonNull Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MediaController(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		initView();
		disposables = new CompositeDisposable();
	}


	private void initView() {
		View.inflate(mContext, R.layout.media_controller, this);
		ButterKnife.bind(this);
		sbVideo.setOnSeekBarChangeListener(this);
		flMediaController.setOnTouchListener(this);
	}

	public void setVideoController(MediaPlayerControl controller) {
		mPlayer = controller;
		prepareToHide();
	}

	@OnClick({ R.id.iv_back, R.id.iv_play })
	public void onViewClicked(View view) {
		prepareToHide();
		switch (view.getId()) {
			case R.id.iv_back:
				mPlayer.close();
				break;
			case R.id.iv_play:
				if (mPlayer.isPlaying()) {
					mPlayer.pause();
				} else {
					mPlayer.start();
				}
				break;
		}
	}

	private void showOrHideController() {
		if (clMediaController.getVisibility() == View.VISIBLE) {
			clMediaController.setVisibility(View.GONE);
		} else {
			clMediaController.setVisibility(View.VISIBLE);
			startPolling();
			prepareToHide();
		}
	}

	private void prepareToHide() {
		if (controllerDisposable == null || controllerDisposable.isDisposed()) {
			controllerDisposable = Flowable.timer(INTERVAL_MENU_HIDE, TimeUnit.MILLISECONDS)
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(aLong -> {
						clMediaController.setVisibility(View.GONE);
						cancelPollingProgress();
						cancelPollingTime();
					});
		}
		disposables.add(controllerDisposable);
	}

	private void cancelPrepareToHide() {
		if (controllerDisposable != null && !controllerDisposable.isDisposed()) {
			controllerDisposable.dispose();
		}
	}

	@Override
	public void start() {
		ivPlay.setImageResource(R.drawable.ic_pause_circle_outline_white_24dp);
	}

	@Override
	public void pause() {
		ivPlay.setImageResource(R.drawable.ic_play_circle_outline_white_24dp);
	}

	@Override
	public void startPolling() {
		if (mPlayer.isLive()) {
			sbVideo.setEnabled(false);
			tvDuration.setText(mContext.getString(R.string.default_time));
		} else {
			sbVideo.setEnabled(true);
			tvDuration.setText(longToHHMM(mPlayer.getDuration()));
			sbVideo.setMax((int) mPlayer.getDuration());
		}
		pollingProgress();
		pollingTime();
	}

	@Override
	public void cancelPolling() {
		disposables.clear();
	}

	private void pollingProgress() {
		if (progressDisposable == null || progressDisposable.isDisposed()) {
			progressDisposable = Flowable.interval(0, INTERVAL_SHOW_PROGRESS, TimeUnit.MILLISECONDS)
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(aLong -> {
						if (mPlayer != null && !mPlayer.isLive()) {
							sbVideo.setProgress((int) mPlayer.getCurrentPosition());
						}
					});
			disposables.add(progressDisposable);
		}
	}

	private void pollingTime() {
		if (timeDisposable == null || timeDisposable.isDisposed()) {
			timeDisposable = Flowable.interval(0, INTERVAL_SHOW_PROGRESS, TimeUnit.MILLISECONDS)
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(aLong -> {
						if (mPlayer != null) {

							tvCurrentPosition.setText(longToHHMM(mPlayer.getCurrentPosition()));
						}
					});
			disposables.add(timeDisposable);
		}
	}

	private void cancelPollingProgress() {
		if (progressDisposable != null && !progressDisposable.isDisposed()) {
			progressDisposable.dispose();
		}
	}

	private void cancelPollingTime() {
		if (timeDisposable != null && !timeDisposable.isDisposed()) {
			timeDisposable.dispose();
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		cancelPollingProgress();
		cancelPrepareToHide();
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		mPlayer.seekTo(seekBar.getProgress());
		pollingProgress();
		prepareToHide();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				cancelPrepareToHide();
				mSlideType = SLIDE_TYPE_NO_SET;
				mPlayerOffset = 0;
				mLastX = x;
				mLastY = y;
				return true;
			case MotionEvent.ACTION_MOVE:
				float xOffset = x - mLastX;
				float yOffset = y - mLastY;
				int touchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
				switch (mSlideType) {
					case SLIDE_TYPE_NO_SET:
						int centerX = getWidth() / 2;
						if (Math.abs(xOffset) > touchSlop && !mPlayer.isLive()) {
							mSlideType = SLIDE_TYPE_PROGRESS;
							clMediaController.setVisibility(View.VISIBLE);
							llPreviewProgress.setVisibility(View.VISIBLE);
						} else if (Math.abs(yOffset) > touchSlop && mLastX >= centerX) {
							mSlideType = SLIDE_TYPE_VOLUME;
							llPreviewVolume.setVisibility(View.VISIBLE);
							mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
							if (mAudioManager != null) {
								mCurrentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
							}
						} else if (Math.abs(yOffset) > touchSlop && mLastX < centerX) {
							mSlideType = SLIDE_TYPE_BRIGHTNESS;
							mLlPreviewBrightness.setVisibility(View.VISIBLE);
							mCurrentBrightness = getBrightness();
						}
						break;
					case SLIDE_TYPE_PROGRESS:
						previewAdjustProgress(xOffset);
						break;
					case SLIDE_TYPE_VOLUME:
						previewAdjustVolume(yOffset);
						break;
					case SLIDE_TYPE_BRIGHTNESS:
						previewAdjustBrightness(yOffset);
						break;
					default:
				}
				break;
			case MotionEvent.ACTION_UP:
				switch (mSlideType) {
					case SLIDE_TYPE_PROGRESS:
						mPlayer.seekTo(mPlayer.getCurrentPosition() + mPlayerOffset);
						llPreviewProgress.setVisibility(View.GONE);
						prepareToHide();
						break;
					case SLIDE_TYPE_VOLUME:
						llPreviewVolume.setVisibility(View.GONE);
						prepareToHide();
						break;
					case SLIDE_TYPE_BRIGHTNESS:
						mLlPreviewBrightness.setVisibility(View.GONE);
						prepareToHide();
						break;
					default:
						showOrHideController();
				}
				break;
		}
		return false;
	}

	private String longToHHMM(long time) {
		long minute = time / (60 * 1000);
		long second = (time - (minute * 60 * 1000)) / 1000;
		return String.format(Locale.CHINA, "%02d:%02d", minute, second);
	}

	private String longToSecond(long time) {
		return String.valueOf(time / 1000);
	}

	private void previewAdjustProgress(float offset) {
		if (!mPlayer.isLive()) {
			long duration = mPlayer.getDuration();
			int width = getWidth();
			mPlayerOffset = (int) (((offset / width) * duration) * ADJUST_PROGRESS_SPEED);
			String offsetPreview = longToSecond(mPlayerOffset) + mContext.getString(R.string.second);
			if (mPlayerOffset > 0) {
				offsetPreview = "+" + offsetPreview;
			}
			String progressPreview = longToHHMM(mPlayer.getCurrentPosition()
					+ mPlayerOffset) + "/" + longToHHMM(mPlayer.getDuration());
			tvOffsetPreview.setText(offsetPreview);
			tvProgressPreview.setText(progressPreview);
		}
	}

	private void previewAdjustVolume(float offset) {
		int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int height = getHeight();
		int offsetVolume = (int) ((-offset * ADJUST_VOLUME_SPEED / height) * maxVolume);
		int volume = mCurrentVolume + offsetVolume;
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
		sbVolume.setMax(maxVolume);
		sbVolume.setProgress(volume);
	}

	private void previewAdjustBrightness(float offset) {
		int offsetBrightness = (int) ((-offset / getHeight()) * 255);
		Window window = ((Activity) mContext).getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.screenBrightness = (float) (mCurrentBrightness + offsetBrightness * (1f / 255) * ADJUST_BRIGHTNESS_SPEED);
		lp.screenBrightness = lp.screenBrightness > 1 ? 1 : lp.screenBrightness;
		lp.screenBrightness = lp.screenBrightness < 0 ? 0 : lp.screenBrightness;
		mSbBrightness.setMax(255);
		mSbBrightness.setProgress((int) (lp.screenBrightness * 255));
		window.setAttributes(lp);
	}

	private float getBrightness() {
		Window window = ((Activity) mContext).getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		return lp.screenBrightness;
	}
}