package io.github.wotaslive.roomlist;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.wotaslive.R;
import io.github.wotaslive.data.model.RoomInfo;

public class RoomListFragment extends Fragment implements RoomListContract.RoomListView, SwipeRefreshLayout.OnRefreshListener {

	@BindView(R.id.rv_room)
	RecyclerView mRvRoom;
	@BindView(R.id.srl_room)
	SwipeRefreshLayout mSrlRoom;
	Unbinder unbinder;

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
		initView();
		initData();
	}

	private void initData() {
		mSrlRoom.setRefreshing(true);
		mPresenter.getRoomList();
	}

	private void initView() {
		mAdapter = new RoomListAdapter();
		mRvRoom.setLayoutManager(new LinearLayoutManager(getContext()));
		mRvRoom.addItemDecoration(new MaterialViewPagerHeaderDecorator());
		int verticalSpace = getContext().getResources().getDimensionPixelOffset(R.dimen.cardMarginVertical);
		int horizontalSpace = getContext().getResources().getDimensionPixelOffset(R.dimen.cardMarginHorizontal);
		mRvRoom.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
				super.getItemOffsets(outRect, view, parent, state);
				outRect.bottom = 0;
				outRect.left = horizontalSpace;
				outRect.right = horizontalSpace;
				outRect.top = parent.getChildAdapterPosition(view) == 0 ? 0 : verticalSpace;
			}
		});
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
	public void onRefresh() {
		mPresenter.getRoomList();
	}
}
