package io.github.wotaslive.roomlist

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.wotaslive.R
import io.github.wotaslive.data.model.RoomInfo
import io.github.wotaslive.databinding.ItemRoomBinding

class RoomListAdapter(private val callback: Callback) : RecyclerView.Adapter<RoomViewHolder>() {
    private val data = ArrayList<RoomInfo.ContentBean>()

    interface Callback {
        fun onRoomClick(content: RoomInfo.ContentBean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_room, parent, false), callback)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun addNewData(list: List<RoomInfo.ContentBean>?) {
        list?.let {
            data.clear()
            data.addAll(it)
            notifyDataSetChanged()
        }
    }
}

class RoomViewHolder(
        private val binding: ItemRoomBinding,
        private val callback: RoomListAdapter.Callback) : RecyclerView.ViewHolder(binding.root) {
    fun bind(content: RoomInfo.ContentBean) {
        with(binding) {
            viewModel = RoomItemViewModel(content)
            executePendingBindings()
        }
    }
}