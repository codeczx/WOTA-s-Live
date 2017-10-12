package io.github.wotaslive.live;

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

import io.github.wotaslive.model.MemberLiveEntity.ContentBean.LiveListBean;
import io.github.wotaslive.model.MemberLiveEntity.ContentBean.ReviewListBean;
import io.github.wotaslive.model.MemberLiveEntity.ContentBean.RoomBean;
import io.github.wotaslive.R;
import io.github.wotaslive.utils.GlideApp;

/**
 * Created by codeczx on 2017/10/11.
 */

public class MemberLiveAdapter extends RecyclerView.Adapter {
    private static final String TAG = "MemberLiveAdapter";
    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_LIVE_ROOM = 1;

    private List<Object> mList;

    public MemberLiveAdapter(List<Object> list) {
        mList = list == null ? new ArrayList<>() : mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_live_header, parent, false);
                return new HeaderViewHolder(itemView);
            case VIEW_TYPE_LIVE_ROOM:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_live, parent, false);
                return new NormalViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Object object = mList.get(position);
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind((String) object);
        } else if (holder instanceof NormalViewHolder) {
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
        } else if (object instanceof String) {
            return VIEW_TYPE_HEADER;
        }
        return RecyclerView.INVALID_TYPE;
    }

    public void updateData(List<Object> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_subtitle)
        TextView tvSubtitle;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(RoomBean roomBean) {
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

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_header)
        TextView tvHeader;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(String header) {
            tvHeader.setText(header);
        }
    }
}
