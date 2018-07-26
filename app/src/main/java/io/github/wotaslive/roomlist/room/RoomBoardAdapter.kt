package io.github.wotaslive.roomlist.room

import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.wotaslive.DataBindingViewHolder
import io.github.wotaslive.data.model.ExtInfo
import io.github.wotaslive.databinding.ItemRoomBoardBinding
import io.github.wotaslive.roomlist.room.viewmodel.ItemBoardViewModel

class RoomBoardAdapter : ListAdapter<ExtInfo, DataBindingViewHolder>(RoomBoardDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        return DataBindingViewHolder(ItemRoomBoardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        getItem(position).let {
            with(holder) {
                bind(ItemBoardViewModel(it))
                itemView.tag = it
            }
        }
    }
}