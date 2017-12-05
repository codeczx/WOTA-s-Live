package io.github.wotaslive.livelist;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.wotaslive.R;
import io.github.wotaslive.data.model.LiveInfo;


public class LiveListFragment extends Fragment implements LiveListContract.LiveListView, SwipeRefreshLayout.OnRefreshListener {

	Unbinder unbinder;
	@BindView(R.id.rv_live)
	RecyclerView mRvLive;
	@BindView(R.id.srl_live)
	SwipeRefreshLayout mSrlLive;
	private LiveListAdapter mAdapter;

	private LiveListContract.LiveListPresenter mPresenter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_live_list, container, false);
		unbinder = ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		new LiveListPresenterImpl(getContext(), this);
		initView();
		initData();
	}

	private void initView() {
		mAdapter = new LiveListAdapter(mPresenter);
		mRvLive.setLayoutManager(new LinearLayoutManager(getContext()));
		mRvLive.addItemDecoration(new MaterialViewPagerHeaderDecorator());
		int verticalSpace = getContext().getResources().getDimensionPixelOffset(R.dimen.cardMarginVertical);
		int horizontalSpace = getContext().getResources().getDimensionPixelOffset(R.dimen.cardMarginHorizontal);
		mRvLive.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
				super.getItemOffsets(outRect, view, parent, state);
				outRect.bottom = 0;
				outRect.left = horizontalSpace;
				outRect.right = horizontalSpace;
				outRect.top = parent.getChildAdapterPosition(view) == 0 ? 0 : verticalSpace;
			}
		});
		mRvLive.setAdapter(mAdapter);
		mRvLive.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if (newState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView.canScrollVertically(0)) {
					mPresenter.loadMemberLive();
				}
			}
		});
		mSrlLive.setOnRefreshListener(this);
	}

	private void initData() {
		mPresenter.getMemberLive();
		mSrlLive.setRefreshing(true);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
		mPresenter.unSubscribe();
	}


	@Override
	public void refreshList(List<LiveInfo.ContentBean.RoomBean> liveList, List<LiveInfo.ContentBean.RoomBean> reviewList) {
		mAdapter.updateLiveList(liveList);
		mAdapter.updateReviewList(reviewList);
		mAdapter.notifyDataSetChanged();
		mSrlLive.setRefreshing(false);
	}

	@Override
	public void updateList(List<LiveInfo.ContentBean.RoomBean> liveList, List<LiveInfo.ContentBean.RoomBean> reviewList) {
		mAdapter.insertLiveList(liveList);
		mAdapter.insertReviewList(reviewList);
	}

	@Override
	public void stopRefreshing() {
		mSrlLive.setRefreshing(false);
	}

	@Override
	public void showMenu(LiveInfo.ContentBean.RoomBean room, View anchor) {
		Context wrapper = new ContextThemeWrapper(getContext(), R.style.AppTheme_Menu);
		PopupMenu popupMenu = new PopupMenu(wrapper, anchor);
		popupMenu.inflate(R.menu.menu_list_more);
		popupMenu.setOnMenuItemClickListener(menuItem -> {
			switch (menuItem.getItemId()) {
				case R.id.List_copy_address:
					mPresenter.setClipboard(room.getStreamPath());
					return true;
			}
			return false;
		});
		popupMenu.show();
	}

	@Override
	public void onRefresh() {
		mPresenter.getMemberLive();
	}

	@Override
	public void setPresenter(LiveListContract.LiveListPresenter presenter) {
		mPresenter = presenter;
	}
}
