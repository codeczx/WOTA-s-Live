package io.github.wotaslive.roomlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.wotaslive.GlideApp;
import io.github.wotaslive.R;
import io.github.wotaslive.data.AppRepository;
import io.github.wotaslive.data.model.RoomInfo;

/**
 * Created by codeczx on 2017/11/2 21:52.
 * Class description:
 */
public class RoomListAdapter extends RecyclerView.Adapter {

	private List<RoomInfo.ContentBean> mList;

	RoomListAdapter() {
		mList = new ArrayList<>();
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return RoomViewHolder.newInstance(parent);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		((RoomViewHolder) holder).bind(mList.get(position));
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	void updateRoomList(List<RoomInfo.ContentBean> roomInfoList) {
		mList.clear();
		if (roomInfoList != null) {
			mList.addAll(roomInfoList);
			notifyDataSetChanged();
		}
	}

	static class RoomViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.iv_avatar)
		ImageView mIvAvatar;
		@BindView(R.id.tv_name)
		TextView mTvName;
		@BindView(R.id.tv_comment)
		TextView mTvComment;
		@BindView(R.id.tv_time)
		TextView mIvTime;

		private RoomViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		static RoomViewHolder newInstance(ViewGroup parent) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
			return new RoomViewHolder(view);
		}

		void bind(RoomInfo.ContentBean contentBean) {
			GlideApp.with(itemView.getContext())
					.load(AppRepository.IMG_BASE_URL + contentBean.getRoomAvatar())
					.override(Target.SIZE_ORIGINAL)
					.into(mIvAvatar);
			mTvName.setText(contentBean.getCreatorName());
			mTvComment.setText(contentBean.getComment());
			mIvTime.setText(contentBean.getCommentTime());
		}
	}
}
