package io.github.wotaslive.list;


import android.content.Context;
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


public class ListFragment extends Fragment implements ListContract.MemberLiveView, SwipeRefreshLayout.OnRefreshListener {

	@BindView(R.id.rv_member_live)
	RecyclerView rvMemberLive;
	Unbinder unbinder;
	@BindView(R.id.srl_member_live)
	SwipeRefreshLayout srlMemberLive;
	private ListAdapter mAdapter;

	private Context mContext;

	private ListContract.MemberLivePresenter mPresenter;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_live, container, false);
		unbinder = ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mPresenter = new ListPresenterImpl(mContext, this);
		initView();
		initData();
	}

	private void initView() {
		mAdapter = new ListAdapter(mPresenter);
		rvMemberLive.setLayoutManager(new LinearLayoutManager(getContext()));
		rvMemberLive.addItemDecoration(new MaterialViewPagerHeaderDecorator());
		rvMemberLive.setAdapter(mAdapter);
		srlMemberLive.setOnRefreshListener(this);
	}

	private void initData() {
		mPresenter.getMemberLive();
		srlMemberLive.setRefreshing(true);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}

	@Override
	public void refreshUI() {
		mAdapter.notifyDataSetChanged();
		srlMemberLive.setRefreshing(false);
	}

	@Override
	public void updateLive(List<LiveInfo.ContentBean.RoomBean> list) {
		mAdapter.updateLiveList(list);
	}

	@Override
	public void updateReview(List<LiveInfo.ContentBean.RoomBean> list) {
		mAdapter.updateReviewList(list);
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
}
