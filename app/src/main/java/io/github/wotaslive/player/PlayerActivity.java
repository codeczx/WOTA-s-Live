package io.github.wotaslive.player;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.wotaslive.Constants;
import io.github.wotaslive.R;
import io.github.wotaslive.widget.VideoView;

public class PlayerActivity extends AppCompatActivity {

	@BindView(R.id.vv_live)
	VideoView mVvLive;

	public static void startPlayerActivity(Context context, String path, boolean isLive) {
		Intent intent = new Intent(context, PlayerActivity.class);
		intent.putExtra(Constants.URL, path);
		intent.putExtra(Constants.IS_LIVE, isLive);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		ButterKnife.bind(this);
		setFullScreen();
		String url = getIntent().getStringExtra(Constants.URL);
		boolean isLive = getIntent().getBooleanExtra(Constants.IS_LIVE, false);
		mVvLive.setVideoUrl(url, isLive);
	}

	private void setFullScreen() {
		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_FULLSCREEN
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
						| View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		mVvLive.restart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mVvLive.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mVvLive.pause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mVvLive.stop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mVvLive.release();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		mVvLive.release();
	}
}