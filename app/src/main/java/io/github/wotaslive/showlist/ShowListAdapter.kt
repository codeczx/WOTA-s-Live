package io.github.wotaslive.showlist

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.wotaslive.R
import io.github.wotaslive.data.model.ShowInfo
import io.github.wotaslive.databinding.ItemShowBinding
import java.util.*

/**
 * Created by Tony on 2017/10/22 21:10.
 * Class description:
 */
class ShowListAdapter(val callback: Callback) : RecyclerView.Adapter<ShowViewHolder>() {
    private var data: ArrayList<ShowInfo.ContentBean.ShowBean> = ArrayList()

    interface Callback {
        fun onCoverClick(show: ShowInfo.ContentBean.ShowBean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        return ShowViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_show,
                        parent,
                        false),
                callback)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addNewData(list: List<ShowInfo.ContentBean.ShowBean>?) {
        list?.let {
            data.clear()
            data.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun addMoreData(list: List<ShowInfo.ContentBean.ShowBean>?) {
        list?.let {
            val size = data.size
            data.addAll(it)
            notifyItemRangeInserted(size + 1, it.size)
        }
    }
}

class ShowViewHolder(private val binding: ItemShowBinding, val callback: ShowListAdapter.Callback) : RecyclerView.ViewHolder(binding.root) {
    fun bind(show: ShowInfo.ContentBean.ShowBean) {
        with(binding) {
            eventHandler = callback
            viewModel = ShowItemViewModel(itemView.context, show)
            executePendingBindings()
        }
    }
}