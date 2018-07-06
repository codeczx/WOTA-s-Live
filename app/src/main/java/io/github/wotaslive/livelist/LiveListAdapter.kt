package io.github.wotaslive.livelist

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.wotaslive.R
import io.github.wotaslive.data.model.LiveInfo
import io.github.wotaslive.databinding.ItemLiveBinding

class LiveListAdapter(private val callback: CallBack) : RecyclerView.Adapter<LiveViewHolder>() {
    private val data = ArrayList<LiveInfo.ContentBean.RoomBean>()

    interface CallBack {
        fun onCoverClick(room: LiveInfo.ContentBean.RoomBean)
        fun onLongClick(room: LiveInfo.ContentBean.RoomBean, anchor: View): Boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveViewHolder {
        return LiveViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_live, parent, false), callback)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holderLive: LiveViewHolder, position: Int) {
        holderLive.bind(data[position])
    }

    fun addNewData(list: List<LiveInfo.ContentBean.RoomBean>?) {
        list?.let {
            data.clear()
            data.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun addMoreData(list: List<LiveInfo.ContentBean.RoomBean>?) {
        list?.let {
            val size = data.size
            data.addAll(it)
            notifyItemRangeInserted(size + 1, it.size)
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