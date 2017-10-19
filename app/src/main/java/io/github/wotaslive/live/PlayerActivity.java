package io.github.wotaslive.live;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.wotaslive.Constants;
import io.github.wotaslive.R;
import io.github.wotaslive.widget.VideoView;

public class PlayerActivity extends AppCompatActivity {

	@BindView(R.id.vv_live)
	VideoView mVvLive;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		ButterKnife.bind(this);
		String url = getIntent().getStringExtra(Constants.URL);
		boolean isLive = getIntent().getBooleanExtra(Constants.IS_LIVE, false);
		mVvLive.setVideoUrl(url, isLive);
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
