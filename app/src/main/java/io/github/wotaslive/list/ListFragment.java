package io.github.wotaslive.list;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.wotaslive.R;
import io.github.wotaslive.data.model.LiveInfo;


public class ListFragment extends Fragment implements ListContract.MemberLiveView {

	@BindView(R.id.rv_member_live)
	RecyclerView rvMemberLive;
	Unbinder unbinder;
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
		mPresenter.getMemberLive();
	}

	private void initView() {
		mAdapter = new ListAdapter();
		rvMemberLive.setLayoutManager(new LinearLayoutManager(getContext()));
		rvMemberLive.setAdapter(mAdapter);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}

	@Override
	public void refreshUI() {
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void updateLive(List<LiveInfo.ContentBean.RoomBean> list) {
		mAdapter.updateLiveList(list);
	}

	@Override
	public void updateReview(List<LiveInfo.ContentBean.RoomBean> list) {
		mAdapter.updateReviewList(list);
	}
}
