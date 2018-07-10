package io.github.wotaslive.room

import android.arch.lifecycle.ViewModel
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import io.github.wotaslive.Constants
import io.github.wotaslive.R
import io.github.wotaslive.data.model.ExtInfo
import io.github.wotaslive.room.viewmodel.ItemImageViewModel
import io.github.wotaslive.room.viewmodel.ItemTextViewModel
import io.github.wotaslive.room.viewmodel.ItemTimeViewModel

class RoomDetailAdapter : ListAdapter<Any, RoomDetailAdapter.ItemViewHolder>(RoomDetailDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        if (item is Long) {
            return R.layout.item_room_detail_time
        }
        return when ((item as ExtInfo).messageObject) {
            Constants.MESSAGE_TYPE_TEXT,
            Constants.MESSAGE_TYPE_FANPAI_TEXT ->
                R.layout.item_room_text
            Constants.MESSAGE_TYPE_IMAGE ->
                R.layout.item_room_detail_image
            Constants.MESSAGE_TYPE_LIVE ->
                R.layout.item_room_text
            else ->
                R.layout.item_room_text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), viewType, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position).let {
            with(holder) {
                when (getItemViewType(position)) {
                    R.layout.item_room_text ->
                        bind(ItemTextViewModel(it))
                    R.layout.item_room_detail_image ->
                        bind(ItemImageViewModel(it))
                    R.layout.item_room_detail_time ->
                        bind(ItemTimeViewModel(it))
                }
                itemView.tag = it
            }
        }
    }

    class ItemViewHolder(private val binding: ViewDataBinding) : ViewHolder(binding.root) {
        fun bind(viewModel: ViewModel) {
            with(binding) {
                setVariable(BR.viewModel, viewModel)
                executePendingBindings()
            }
        }
    }
}