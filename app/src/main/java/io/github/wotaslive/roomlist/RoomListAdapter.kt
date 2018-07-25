package io.github.wotaslive.roomlist

import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.wotaslive.DataBindingViewHolder
import io.github.wotaslive.data.model.RoomInfo
import io.github.wotaslive.databinding.ItemRoomBinding

class RoomListAdapter(private val callback: Callback) :
        ListAdapter<RoomInfo.ContentBean, DataBindingViewHolder>(RoomListDiffCallback()) {

    interface Callback {
        fun onRoomClick(content: RoomInfo.ContentBean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val binding = ItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.eventHandler = callback
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        getItem(position).let {
            with(holder) {
                bind(RoomItemViewModel(it))
                itemView.tag = it
            }
        }
    }
}