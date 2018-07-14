package io.github.wotaslive.room

import android.arch.lifecycle.ViewModel
import android.databinding.ViewDataBinding
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import io.github.wotaslive.data.model.ExtInfo
import io.github.wotaslive.databinding.ItemRoomBoardBinding
import io.github.wotaslive.room.viewmodel.ItemBoardViewModel

class RoomBoardAdapter : ListAdapter<ExtInfo, ItemViewHolder>(RoomBoardDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemRoomBoardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position).let {
            with(holder) {
                bind(ItemBoardViewModel(it))
                itemView.tag = it
            }
        }
    }
}

class ItemViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
    fun bind(viewModel: ViewModel) {
        with(binding) {
            setVariable(BR.viewModel, viewModel)
            executePendingBindings()
        }
    }
}