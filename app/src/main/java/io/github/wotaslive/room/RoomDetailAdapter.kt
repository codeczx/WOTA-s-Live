package io.github.wotaslive.room

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.wotaslive.Constants
import io.github.wotaslive.DataBindingViewHolder
import io.github.wotaslive.R
import io.github.wotaslive.data.model.ExtInfo
import io.github.wotaslive.databinding.ItemRoomImageBinding
import io.github.wotaslive.room.viewmodel.ItemImageViewModel
import io.github.wotaslive.room.viewmodel.ItemLiveViewModel
import io.github.wotaslive.room.viewmodel.ItemTextViewModel
import io.github.wotaslive.room.viewmodel.ItemTimeViewModel

class RoomDetailAdapter(private val callback: Callback) : ListAdapter<Any, DataBindingViewHolder>(RoomDetailDiffCallback()) {

    interface Callback {
        fun onImageClick(url: String)
        fun onLiveClick(id: String)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        if (item is Long) {
            return R.layout.item_room_time
        }
        return when ((item as ExtInfo).messageObject) {
            Constants.MESSAGE_TYPE_TEXT,
            Constants.MESSAGE_TYPE_FANPAI_TEXT ->
                R.layout.item_room_text
            Constants.MESSAGE_TYPE_IMAGE ->
                R.layout.item_room_image
            Constants.MESSAGE_TYPE_LIVE,
            Constants.MESSAGE_TYPE_DIANTAI ->
                R.layout.item_room_live
            else ->
                R.layout.item_room_text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false)
        if (binding is ItemRoomImageBinding) {
            binding.eventHandler = callback
        }
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        getItem(position).let {
            with(holder) {
                when (getItemViewType(position)) {
                    R.layout.item_room_text ->
                        bind(ItemTextViewModel(it))
                    R.layout.item_room_image ->
                        bind(ItemImageViewModel(it))
                    R.layout.item_room_time ->
                        bind(ItemTimeViewModel(it))
                    R.layout.item_room_live ->
                        bind(ItemLiveViewModel(it))
                }
                itemView.tag = it
            }
        }
    }
}