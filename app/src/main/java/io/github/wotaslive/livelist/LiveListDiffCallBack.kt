package io.github.wotaslive.livelist

import android.support.v7.util.DiffUtil
import io.github.wotaslive.data.model.LiveInfo

class LiveListDiffCallBack : DiffUtil.ItemCallback<LiveInfo.ContentBean.RoomBean>() {
    override fun areItemsTheSame(oldItem: LiveInfo.ContentBean.RoomBean?, newItem: LiveInfo.ContentBean.RoomBean?): Boolean {
        return oldItem?.liveId == newItem?.liveId
    }

    override fun areContentsTheSame(oldItem: LiveInfo.ContentBean.RoomBean?, newItem: LiveInfo.ContentBean.RoomBean?): Boolean {
        return oldItem?.title == newItem?.title &&
                oldItem?.subTitle == newItem?.subTitle &&
                oldItem?.startTime == newItem?.startTime &&
                oldItem?.picPath == newItem?.picPath
    }
}