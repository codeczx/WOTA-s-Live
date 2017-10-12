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
import io.github.wotaslive.data.model.LiveInfo.ContentBean.LiveListBean;
import io.github.wotaslive.data.model.LiveInfo.ContentBean.ReviewListBean;
import io.github.wotaslive.data.model.LiveInfo.ContentBean.RoomBean;

/**
 * Created by codeczx on 2017/10/11.
 */

public class ListAdapter extends RecyclerView.Adapter {
	public static final int VIEW_TYPE_HEADER = 0;
	public static final int VIEW_TYPE_LIVE_ROOM = 1;

	private List<Object> mList;

	public ListAdapter() {
		mList = new ArrayList<>();
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		switch (viewType) {
			case VIEW_TYPE_HEADER:
				return HeaderViewHolder.newInstance(parent);
			case VIEW_TYPE_LIVE_ROOM:
				return NormalViewHolder.newInstance(parent);
		}
		return null;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

		Object object = mList.get(position);
		if (holder instanceof HeaderViewHolder) {
			((HeaderViewHolder) holder).bind((String) object);
		}
		else if (holder instanceof NormalViewHolder) {
			((NormalViewHolder) holder).bind((RoomBean) object);
		}
	}

	@Override
	public int getItemCount() {
		return mList == null ? 0 : mList.size();
	}

	@Override
	public int getItemViewType(int position) {
		Object object = mList.get(position);
		if (object instanceof ReviewListBean || object instanceof LiveListBean || object == null) {
			return VIEW_TYPE_LIVE_ROOM;
		}
		else if (object instanceof String) {
			return VIEW_TYPE_HEADER;
		}
		return RecyclerView.INVALID_TYPE;
	}

	public void updateData(List<Object> list) {
		mList.clear();
		mList.addAll(list);
		notifyDataSetChanged();
	}

	static class NormalViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.iv_cover)
		ImageView ivCover;
		@BindView(R.id.tv_title)
		TextView tvTitle;
		@BindView(R.id.tv_subtitle)
		TextView tvSubtitle;

		private static NormalViewHolder newInstance(ViewGroup viewGroup) {
			View view = LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.item_member_live, viewGroup, false);
			return new NormalViewHolder(view);
		}

		private NormalViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		private void bind(RoomBean roomBean) {
			if (roomBean == null) {
				return;
			}
			tvTitle.setText(roomBean.getTitle());
			tvSubtitle.setText(roomBean.getSubTitle());
			GlideApp.with(itemView.getContext())
					.load("https://source.48.cn/" + roomBean.getPicPath())
					.centerCrop()
					.into(ivCover);
		}

	}

	static class HeaderViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.tv_header)
		TextView tvHeader;

		private static HeaderViewHolder newInstance(ViewGroup viewGroup) {
			View view = LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.item_member_live_header, viewGroup, false);
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
