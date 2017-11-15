package io.github.wotaslive.room;

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
import io.github.wotaslive.Constants;
import io.github.wotaslive.GlideApp;
import io.github.wotaslive.R;
import io.github.wotaslive.data.AppRepository;
import io.github.wotaslive.data.model.ExtInfo;
import io.github.wotaslive.data.model.RoomDetailInfo;
import io.github.wotaslive.utils.TimeUtils;

/**
 * Created by codeczx on 2017/11/9 0:24.
 * Class description:
 */
public class RoomDetailAdapter extends RecyclerView.Adapter {

	private static final int TYPE_TEXT_CREATOR = 0;
	private static final int TYPE_IMAGE_CREATOR = 1;
	private static final int TYPE_LIVE_CREATOR = 2;
	private static final int TYPE_FANPAI_TEXT = 3;
	private static final int TYPE_TEXT_JUJU = 4;
	private static final int TYPE_TEXT_MEMBER = 5;
	private static final int TYPE_TIME = 6;

	private List<RoomDetailInfo.ContentBean.DataBean> mDataBeanList;
	private List<Object> mList;

	RoomDetailAdapter() {
		mDataBeanList = new ArrayList<>();
		mList = new ArrayList<>();
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		switch (viewType) {
			case TYPE_TEXT_CREATOR:
				return TextCreatorViewHolder.newInstance(parent);
			case TYPE_IMAGE_CREATOR:
				return TextCreatorViewHolder.newInstance(parent);
			case TYPE_LIVE_CREATOR:
				return TextCreatorViewHolder.newInstance(parent);
			case TYPE_FANPAI_TEXT:
				return TextFanpaiViewHolder.newInstance(parent);
			case TYPE_TEXT_JUJU:
				return TextCreatorViewHolder.newInstance(parent);
			case TYPE_TEXT_MEMBER:
				return TextMemberViewHolder.newInstance(parent);
			case TYPE_TIME:
				return TimeViewHolder.newInstance(parent);
			default:
				return TextCreatorViewHolder.newInstance(parent);
		}
	}

	@Override
	public int getItemViewType(int position) {
		Object object = mList.get(position);
		if (object instanceof ExtInfo) {
			ExtInfo extInfo = (ExtInfo) object;
			if (Constants.MESSAGE_TYPE_TEXT.equals(extInfo.getMessageObject()) && extInfo.getRole() == 2) {
				return TYPE_TEXT_CREATOR;
			} else if (Constants.MESSAGE_TYPE_FANPAI_TEXT.equals(extInfo.getMessageObject())) {
				return TYPE_FANPAI_TEXT;
			} else if (Constants.MESSAGE_TYPE_IMAGE.equals(extInfo.getMessageObject())) {
				return TYPE_IMAGE_CREATOR;
			} else if (Constants.MESSAGE_TYPE_LIVE.equals(extInfo.getMessageObject())) {
				return TYPE_LIVE_CREATOR;
			} else if (Constants.MESSAGE_TYPE_TEXT.equals(extInfo.getMessageObject()) && extInfo.getRole() == -1) {
				return TYPE_TEXT_JUJU;
			} else if (Constants.MESSAGE_TYPE_TEXT.equals(extInfo.getMessageObject()) && extInfo.getRole() == 0) {
				return TYPE_TEXT_MEMBER;
			}
		} else if (object instanceof String) {
			return TYPE_TIME;
		}
		return RecyclerView.INVALID_TYPE;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof TextCreatorViewHolder) {
			((TextCreatorViewHolder) holder).bind((ExtInfo) mList.get(position));
		} else if (holder instanceof TimeViewHolder) {
			((TimeViewHolder) holder).bind((String) mList.get(position));
		} else if (holder instanceof TextFanpaiViewHolder) {
			((TextFanpaiViewHolder) holder).bind((ExtInfo) mList.get(position));
		} else if (holder instanceof TextMemberViewHolder) {
			((TextMemberViewHolder) holder).bind((ExtInfo) mList.get(position));
		}
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	void updateData(List<ExtInfo> extInfoList, List<RoomDetailInfo.ContentBean.DataBean> dataBeanList) {
		if (extInfoList != null) {
			List<Object> tempList = new ArrayList<>();
			for (int i = extInfoList.size() - 1; i >= 0; i--) {
				tempList.add(extInfoList.get(i));
				if (i >= 1 && TimeUtils.isNeedShowTimeStamp(dataBeanList.get(i).getMsgTime(), dataBeanList.get(i - 1).getMsgTime())) {
					tempList.add(TimeUtils.getTimeStamp(dataBeanList.get(i).getMsgTime()));
				}
			}
			mList.addAll(tempList);
		}
	}

	public static class TextCreatorViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.iv_avatar)
		ImageView mIvAvatar;
		@BindView(R.id.tv_message)
		TextView mTvMessage;
		@BindView(R.id.tv_sender_name)
		TextView mTvSenderName;

		private TextCreatorViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		static TextCreatorViewHolder newInstance(ViewGroup parent) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_text_creator, parent, false);
			return new TextCreatorViewHolder(view);
		}

		public void bind(ExtInfo extInfo) {
			mTvSenderName.setText(extInfo.getSenderName());
			mTvMessage.setText(extInfo.getText());
			GlideApp.with(itemView.getContext())
					.load(AppRepository.IMG_BASE_URL + extInfo.getSenderAvatar())
					.override(Target.SIZE_ORIGINAL)
					.into(mIvAvatar);
		}
	}

	public static class TextMemberViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.iv_avatar)
		ImageView mIvAvatar;
		@BindView(R.id.tv_message)
		TextView mTvMessage;
		@BindView(R.id.tv_sender_name)
		TextView mTvSenderName;

		private TextMemberViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		static TextMemberViewHolder newInstance(ViewGroup parent) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_text_member, parent, false);
			return new TextMemberViewHolder(view);
		}

		public void bind(ExtInfo extInfo) {
			mTvSenderName.setText(extInfo.getSenderName());
			mTvMessage.setText(extInfo.getText());
			GlideApp.with(itemView.getContext())
					.load(AppRepository.IMG_BASE_URL + extInfo.getSenderAvatar())
					.override(Target.SIZE_ORIGINAL)
					.into(mIvAvatar);
		}
	}

	static class TextFanpaiViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.iv_avatar)
		ImageView mIvAvatar;
		@BindView(R.id.iv_divider)
		ImageView mIvDivider;
		@BindView(R.id.tv_message)
		TextView mTvMessage;
		@BindView(R.id.tv_sender_name)
		TextView mTvSenderName;
		@BindView(R.id.tv_message_juju)
		TextView mTvMessageJuju;

		private TextFanpaiViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		static TextFanpaiViewHolder newInstance(ViewGroup parent) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_text_fanpai, parent, false);
			return new TextFanpaiViewHolder(view);
		}

		void bind(ExtInfo extInfo) {
			mTvMessage.setText(extInfo.getMessageText());
			mTvMessageJuju.setText(extInfo.getFaipaiContent());
			mTvSenderName.setText(extInfo.getSenderName());
			GlideApp.with(itemView.getContext())
					.load(AppRepository.IMG_BASE_URL + extInfo.getSenderAvatar())
					.override(Target.SIZE_ORIGINAL)
					.into(mIvAvatar);
		}
	}

	public static class TimeViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.tv_time)
		TextView mTvTime;

		private TimeViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		static TimeViewHolder newInstance(ViewGroup parent) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_detail_time, parent, false);
			return new TimeViewHolder((view));
		}

		public void bind(String time) {
			mTvTime.setText(time);
		}
	}

}
