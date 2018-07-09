package io.github.wotaslive.showlist

import android.databinding.DataBindingUtil
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.wotaslive.R
import io.github.wotaslive.data.model.ShowInfo
import io.github.wotaslive.databinding.ItemShowBinding

/**
 * Created by Tony on 2017/10/22 21:10.
 * Class description:
 */
class ShowListAdapter(val callback: Callback) :
        ListAdapter<ShowInfo.ContentBean.ShowBean, ShowViewHolder>(ShowListDiffCallback()) {
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
        getItem(position).let {
            with(holder) {
                bind(it)
                itemView.tag = it
            }
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