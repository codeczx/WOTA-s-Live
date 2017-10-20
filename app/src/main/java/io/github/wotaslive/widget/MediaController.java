package io.github.wotaslive.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

public class MediaController extends FrameLayout implements MediaViewControl, SeekBar.OnSeekBarChangeListener {


	private static final int INTERVAL_MENU_HIDE = 5000;
	private static final int INTERVAL_SHOW_PROGRESS = 1000;
	private static final double FAST_RATE = 0.3;

	@BindView(R.id.cl_media_controller)
	ConstraintLayout clMediaController;
	@BindView(R.id.iv_play)
	ImageView ivPlay;
	@BindView(R.id.sb_video)
	SeekBar sbVideo;
	@BindView(R.id.iv_close)
	ImageView ivBack;
	@BindView(R.id.tv_current_position)
	TextView tvCurrentPosition;
	@BindView(R.id.tv_duration)
	TextView tvDuration;
	@BindView(R.id.tv_preview)
	TextView tvPreview;
	@BindView(R.id.cv_preview)
	CardView cvPreview;

	private Context mContext;
	private MediaPlayerControl mPlayer;
	private float mLastX;
	private float mLastY;
	private boolean mIsSlide;
	private int mPlayerOffset;

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
	}

	public void setVideoController(MediaPlayerControl controller) {
		mPlayer = controller;
		prepareToHide();
	}

	@OnClick({ R.id.iv_close, R.id.iv_play })
	public void onViewClicked(View view) {
		prepareToHide();
		switch (view.getId()) {
			case R.id.iv_close:
				mPlayer.close();
				break;
			case R.id.iv_play:
				if (mPlayer.isPlaying()) {
					mPlayer.pause();
				}
				else {
					mPlayer.start();
				}
				break;
		}
	}

	private void showOrHideController() {
		if (clMediaController.getVisibility() == View.VISIBLE) {
			clMediaController.setVisibility(View.GONE);
		}
		else {
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
		}
		else {
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

	private String longToHHMM(long time) {
		long minute = time / (60 * 1000);
		long second = (time - (minute * 60 * 1000)) / 1000;
		return String.format(Locale.CHINA, "%02d:%02d", minute, second);
	}

	private String longToSecond(long time) {
		return String.valueOf(time / 1000);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				cancelPrepareToHide();
				mIsSlide = false;
				mPlayerOffset = 0;
				mLastX = x;
				mLastY = y;
				return true;
			case MotionEvent.ACTION_MOVE:
				float xOffset = x - mLastX;
				float yOffset = y - mLastY;
				int touchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
				if (Math.abs(xOffset) > touchSlop || Math.abs(yOffset) > touchSlop) {
					mIsSlide = true;
					if (!mPlayer.isLive()) {
						long duration = mPlayer.getDuration();
						int width = getWidth();
						mPlayerOffset = (int) (((xOffset / width) * duration) * FAST_RATE);
						String preview = longToSecond(mPlayerOffset) + mContext.getString(R.string.second);
						if (mPlayerOffset > 0) {
							preview = "+" + preview;
						}
						clMediaController.setVisibility(View.VISIBLE);
						tvPreview.setText(preview);
						cvPreview.setVisibility(View.VISIBLE);
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				if (mIsSlide) {
					if (!mPlayer.isLive()) {
						mPlayer.seekTo(mPlayer.getCurrentPosition() + mPlayerOffset);
						cvPreview.setVisibility(View.GONE);
					}
					prepareToHide();
				}
				else {
					showOrHideController();
				}
				break;
		}
		return super.onTouchEvent(event);
	}

}
