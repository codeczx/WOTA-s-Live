package io.github.wotaslive.roomlist;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.SPUtils;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.wotaslive.Constants;
import io.github.wotaslive.R;
import io.github.wotaslive.data.model.RoomInfo;
import io.github.wotaslive.login.LoginFragment;
import io.github.wotaslive.room.RoomDetailActivity;
import io.github.wotaslive.widget.SpaceItemDecoration;

public class RoomListFragment extends Fragment implements RoomListContract.RoomListView, SwipeRefreshLayout.OnRefreshListener {

	@BindView(R.id.rv_room)
	RecyclerView mRvRoom;
	@BindView(R.id.srl_room)
	SwipeRefreshLayout mSrlRoom;
	Unbinder unbinder;
	@BindView(R.id.btn_ask_login)
	Button mBtnAskLogin;
	@BindView(R.id.fl_login)
	FrameLayout mFlLogin;

	private RoomListContract.RoomListPresenter mPresenter;
	private RoomListAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
			showLoginButton();
		}
		else {
			showRoomList();
		}
	}

	private void showLoginButton() {
		mSrlRoom.setVisibility(View.GONE);
		mFlLogin.setVisibility(View.VISIBLE);
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
		mSrlRoom.setVisibility(View.VISIBLE);
		mFlLogin.setVisibility(View.GONE);
		initView();
		initData();
	}

	@Override
	public void onRefresh() {
		mPresenter.getRoomList();
	}

	@OnClick(R.id.btn_ask_login)
	public void onViewClicked() {
		showLoginDialog();
	}

	private void showLoginDialog() {
		LoginFragment loginFragment = new LoginFragment();
		loginFragment.show(getChildFragmentManager(), LoginFragment.TAG);
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
