package io.github.wotaslive.showlist

import android.support.v7.util.DiffUtil
import io.github.wotaslive.data.model.ShowInfo

class ShowListDiffCallback : DiffUtil.ItemCallback<ShowInfo.ContentBean.ShowBean>() {
    override fun areItemsTheSame(oldItem: ShowInfo.ContentBean.ShowBean?, newItem: ShowInfo.ContentBean.ShowBean?): Boolean {
        return oldItem?.liveId == newItem?.liveId
    }

    override fun areContentsTheSame(oldItem: ShowInfo.ContentBean.ShowBean?, newItem: ShowInfo.ContentBean.ShowBean?): Boolean {
        return oldItem?.picPath == newItem?.picPath &&
                oldItem?.isIsOpen == newItem?.isIsOpen &&
                oldItem?.title == newItem?.title &&
                oldItem?.subTitle == newItem?.subTitle &&
                oldItem?.startTime == newItem?.startTime
    }
}