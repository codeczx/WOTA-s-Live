package io.github.wotaslive.livelist

import android.databinding.DataBindingUtil
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.wotaslive.R
import io.github.wotaslive.data.model.LiveInfo
import io.github.wotaslive.databinding.ItemLiveBinding

class LiveListAdapter(private val callback: CallBack) :
        ListAdapter<LiveInfo.ContentBean.RoomBean, LiveViewHolder>(LiveListDiffCallBack()) {

    interface CallBack {
        fun onCoverClick(room: LiveInfo.ContentBean.RoomBean)
        fun onLongClick(room: LiveInfo.ContentBean.RoomBean, anchor: View): Boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveViewHolder {
        return LiveViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_live, parent, false), callback)
    }

    override fun onBindViewHolder(holderLive: LiveViewHolder, position: Int) {
        getItem(position).let {
            with(holderLive) {
                bind(it)
                itemView.tag = it
            }
        }
    }
}

class LiveViewHolder(private val binding: ItemLiveBinding, private val callback: LiveListAdapter.CallBack) : RecyclerView.ViewHolder(binding.root) {
    fun bind(room: LiveInfo.ContentBean.RoomBean) {
        with(binding) {
            eventHandler = callback
            viewModel = LiveItemViewModel(itemView.context, room)
            executePendingBindings()
        }
    }
}