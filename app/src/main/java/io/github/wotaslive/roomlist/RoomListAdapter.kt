package io.github.wotaslive.roomlist

import android.databinding.DataBindingUtil
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import io.github.wotaslive.R
import io.github.wotaslive.data.model.RoomInfo
import io.github.wotaslive.databinding.ItemRoomBinding

class RoomListAdapter(private val callback: Callback) :
        ListAdapter<RoomInfo.ContentBean, RoomViewHolder>(RoomListDiffCallback()) {

    interface Callback {
        fun onRoomClick(content: RoomInfo.ContentBean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_room, parent, false), callback)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        Logger.d("bind")
        getItem(position).let {
            with(holder) {
                bind(it)
                itemView.tag = it
            }
        }
    }
}

class RoomViewHolder(
        private val binding: ItemRoomBinding,
        private val callback: RoomListAdapter.Callback) : RecyclerView.ViewHolder(binding.root) {
    fun bind(content: RoomInfo.ContentBean) {
        with(binding) {
            viewModel = RoomItemViewModel(content)
            eventHandler = callback
            executePendingBindings()
        }
    }
}