package io.github.wotaslive.roomlist;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SPUtils;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.wotaslive.Constants;
import io.github.wotaslive.R;
import io.github.wotaslive.data.model.RoomInfo;
import io.github.wotaslive.login.LoginActivity;
import io.github.wotaslive.room.RoomDetailActivity;
import io.github.wotaslive.widget.SpaceItemDecoration;

public class RoomListFragment extends Fragment implements RoomListContract.RoomListView, SwipeRefreshLayout.OnRefreshListener {

	@BindView(R.id.rv_room)
	RecyclerView mRvRoom;
	@BindView(R.id.srl_room)
	SwipeRefreshLayout mSrlRoom;
	Unbinder unbinder;

	private RoomListContract.RoomListPresenter mPresenter;
	private RoomListAdapter mAdapter;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_room_list, container, false);
		unbinder = ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		new RoomListPresenterImpl(getContext(), this);
		SPUtils spUtils = SPUtils.getInstance(Constants.SP_NAME);
		String token = spUtils.getString(Constants.HEADER_KEY_TOKEN, "");
		if (TextUtils.isEmpty(token)) {
			LoginActivity.Companion.startLoginActivity(getActivity());
		}
		else {
			showRoomList();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == LoginActivity.requestCode && resultCode == Activity.RESULT_OK) {
			showRoomList();
		}
	}

	private void initData() {
		mSrlRoom.setRefreshing(true);
		mPresenter.getRoomList();
	}

	private void initView() {
		mAdapter = new RoomListAdapter(contentBean ->
				RoomDetailActivity.startRoomDetailActivity(getContext(), contentBean));
		mRvRoom.setLayoutManager(new LinearLayoutManager(getContext()));
		mRvRoom.addItemDecoration(new MaterialViewPagerHeaderDecorator());
		mRvRoom.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.cardMarginHorizontal),
				getResources().getDimensionPixelOffset(R.dimen.cardMarginVertical)));
		mRvRoom.setAdapter(mAdapter);
		mSrlRoom.setOnRefreshListener(this);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}

	@Override
	public void setPresenter(RoomListContract.RoomListPresenter presenter) {
		mPresenter = presenter;
	}

	@Override
	public void updateRoom(List<RoomInfo.ContentBean> roomInfoList) {
		mAdapter.updateRoomList(roomInfoList);
	}

	@Override
	public void refreshUI() {
		mAdapter.notifyDataSetChanged();
		mSrlRoom.setRefreshing(false);
	}

	@Override
	public void showRoomList() {
		initView();
		initData();
	}

	@Override
	public void onRefresh() {
		mPresenter.getRoomList();
	}

	@Override
	public void onStart() {
		super.onStart();
		mPresenter.subscribe();
	}

	@Override
	public void onStop() {
		super.onStop();
		mPresenter.unSubscribe();
	}
}
