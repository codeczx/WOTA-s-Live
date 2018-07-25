package io.github.wotaslive.livelist

import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.wotaslive.DataBindingViewHolder
import io.github.wotaslive.data.model.LiveInfo
import io.github.wotaslive.databinding.ItemLiveBinding

class LiveListAdapter(private val callback: CallBack) :
        ListAdapter<LiveInfo.ContentBean.RoomBean, DataBindingViewHolder>(LiveListDiffCallBack()) {

    interface CallBack {
        fun onCoverClick(room: LiveInfo.ContentBean.RoomBean)
        fun onLongClick(room: LiveInfo.ContentBean.RoomBean, anchor: View): Boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val binding = ItemLiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.eventHandler = callback
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holderLive: DataBindingViewHolder, position: Int) {
        getItem(position).let {
            with(holderLive) {
                bind(LiveItemViewModel(itemView.context, it))
                itemView.tag = it
            }
        }
    }
}