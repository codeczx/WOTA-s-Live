package io.github.wotaslive.list;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.wotaslive.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements ListContract.MemberLiveView {

	@BindView(R.id.rv_member_live)
	RecyclerView rvMemberLive;
	Unbinder unbinder;
	private ListAdapter mAdapter;

	private Context mContext;

	private ListContract.MemberLivePresenter mPresenter;

	public ListFragment() {
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_member_live, container, false);
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
		GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
		gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
			@Override
			public int getSpanSize(int position) {
				switch (mAdapter.getItemViewType(position)) {
					case ListAdapter.VIEW_TYPE_LIVE_ROOM:
						return 1;
					case ListAdapter.VIEW_TYPE_HEADER:
						return 2;
					default:
						return -1;
				}
			}
		});
		rvMemberLive.setLayoutManager(gridLayoutManager);
		rvMemberLive.setAdapter(mAdapter);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}


	@Override
	public void refreshUI(List<Object> list) {
		mAdapter.updateData(list);
	}
}
