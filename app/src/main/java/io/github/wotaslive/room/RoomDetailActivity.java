package io.github.wotaslive.room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.wotaslive.Constants;
import io.github.wotaslive.R;
import io.github.wotaslive.data.model.ExtInfo;
import io.github.wotaslive.data.model.RoomDetailInfo;
import io.github.wotaslive.data.model.RoomInfo;

public class RoomDetailActivity extends AppCompatActivity implements RoomDetailContract.RoomDetailView {

	@BindView(R.id.toolbar)
	Toolbar mToolbar;
	@BindView(R.id.rv_room_detail)
	RecyclerView mRvRoomDetail;

	private RoomDetailContract.RoomDetailPresenter mPresenter;
	private RoomDetailAdapter mAdapter;

	public static void startRoomDetailActivity(Context context, RoomInfo.ContentBean contentBean) {
		Intent intent = new Intent(context, RoomDetailActivity.class);
		intent.putExtra(Constants.ROOM_ID, contentBean.getRoomId());
		intent.putExtra(Constants.ROOM_NAME, contentBean.getRoomName());
		intent.putExtra(Constants.ROOM_CREATOR, contentBean.getCreatorName());
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room_detail);
		ButterKnife.bind(this);

		int roomId = getIntent().getIntExtra(Constants.ROOM_ID, 0);
		String roomName = getIntent().getStringExtra(Constants.ROOM_NAME);
		String roomCreator = getIntent().getStringExtra(Constants.ROOM_CREATOR);
		setSupportActionBar(mToolbar);
		final ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setTitle(roomCreator + ":" + roomName);
			actionBar.setDisplayShowHomeEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		new RoomDetailPresenterImpl(this, this);
		initView();
		initData(roomId);
	}

	private void initView() {
		mRvRoomDetail.setLayoutManager(new LinearLayoutManager(this));
		mAdapter = new RoomDetailAdapter();
		mRvRoomDetail.setAdapter(mAdapter);
	}

	private void initData(int roomId) {
		mPresenter.getRoomDetailInfo(roomId, 0);
	}

	@Override
	public void setPresenter(RoomDetailContract.RoomDetailPresenter presenter) {
		mPresenter = presenter;
	}

	@Override
	public void updateData(List<ExtInfo> extInfoList, List<RoomDetailInfo.ContentBean.DataBean> content) {
		mAdapter.updateData(extInfoList, content);
	}

	@Override
	public void refreshUI() {
		mAdapter.notifyDataSetChanged();
	}
}
