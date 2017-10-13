package io.github.wotaslive.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.wotaslive.GlideApp;
import io.github.wotaslive.R;
import io.github.wotaslive.data.model.LiveInfo.ContentBean.RoomBean;

/**
 * Created by codeczx on 2017/10/11.
 */

public class ListAdapter extends RecyclerView.Adapter {
	private static final int TYPE_LIVE_HEADER = 0;
	private static final int TYPE_LIVE_CONTENT = 1;
	private static final int TYPE_REVIEW_HEADER = 2;
	private static final int TYPE_REVIEW_CONTENT = 3;

	private List<RoomBean> mLiveList;
	private List<RoomBean> mReviewList;

	ListAdapter() {
		mLiveList = new ArrayList<>();
		mReviewList = new ArrayList<>();
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0 && mLiveList.size() != 0)
			return TYPE_LIVE_HEADER;
		else if (position <= mLiveList.size())
			return TYPE_LIVE_CONTENT;
		else if (mReviewList.size() > 0 &&
				((position == mLiveList.size() + 1 && mLiveList.size() > 0) ||
						(position == mLiveList.size() && mLiveList.size() == 0)))
			return TYPE_REVIEW_HEADER;
		else
			return TYPE_REVIEW_CONTENT;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		switch (viewType) {
			case TYPE_LIVE_HEADER:
				return HeaderViewHolder.newInstance(parent);
			case TYPE_LIVE_CONTENT:
				return ContentViewHolder.newInstance(parent);
			case TYPE_REVIEW_HEADER:
				return HeaderViewHolder.newInstance(parent);
			case TYPE_REVIEW_CONTENT:
				return ContentViewHolder.newInstance(parent);
		}
		return null;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		switch (getItemViewType(position)) {
			case TYPE_LIVE_HEADER:
				((HeaderViewHolder) holder).bind("直播");
				break;
			case TYPE_LIVE_CONTENT:
				((ContentViewHolder) holder).bind(mLiveList.get(position - 1));
				break;
			case TYPE_REVIEW_HEADER:
				((HeaderViewHolder) holder).bind("回放");
				break;
			case TYPE_REVIEW_CONTENT:
				((ContentViewHolder) holder).bind(mReviewList.get(mReviewList.size() + position - getItemCount()));
				break;
		}
	}

	@Override
	public int getItemCount() {
		int count = 0;
		if (mLiveList.size() != 0)
			count += mLiveList.size() + 1;
		if (mReviewList.size() != 0)
			count += mReviewList.size() + 1;
		return count;
	}

	void updateLiveList(List<RoomBean> list) {
		mLiveList.clear();
		if (list != null) {
			mLiveList.addAll(list);
		}
	}

	void updateReviewList(List<RoomBean> list) {
		mReviewList.clear();
		if (list != null) {
			mReviewList.addAll(list);
		}
	}

	static class ContentViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.iv_cover)
		ImageView ivCover;
		@BindView(R.id.tv_title)
		TextView tvTitle;
		@BindView(R.id.tv_subtitle)
		TextView tvSubtitle;

		private static ContentViewHolder newInstance(ViewGroup viewGroup) {
			View view = LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.item_live, viewGroup, false);
			return new ContentViewHolder(view);
		}

		private ContentViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		private void bind(RoomBean roomBean) {
			if (roomBean == null) {
				return;
			}
			tvTitle.setText(roomBean.getTitle());
			tvSubtitle.setText(roomBean.getSubTitle());
			String path = roomBean.getPicPath();
			if (path.contains(",")) {
				String[] paths = path.split(",");
				path = paths[0];
			}
			GlideApp.with(itemView.getContext())
					.load("https://source.48.cn" + path)
					.centerCrop()
					.into(ivCover);
		}

	}

	static class HeaderViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.tv_header)
		TextView tvHeader;

		private static HeaderViewHolder newInstance(ViewGroup viewGroup) {
			View view = LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.item_live_header, viewGroup, false);
			return new HeaderViewHolder(view);
		}

		private HeaderViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		private void bind(String header) {
			tvHeader.setText(header);
		}
	}
}
