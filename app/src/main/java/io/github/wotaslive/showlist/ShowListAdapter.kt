package io.github.wotaslive.showlist

import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.wotaslive.DataBindingViewHolder
import io.github.wotaslive.data.model.ShowInfo
import io.github.wotaslive.databinding.ItemShowBinding

/**
 * Created by Tony on 2017/10/22 21:10.
 * Class description:
 */
class ShowListAdapter(val callback: Callback) :
        ListAdapter<ShowInfo.ContentBean.ShowBean, DataBindingViewHolder>(ShowListDiffCallback()) {
    interface Callback {
        fun onCoverClick(show: ShowInfo.ContentBean.ShowBean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val binding = ItemShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.eventHandler = callback
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        getItem(position).let {
            with(holder) {
                bind(ShowItemViewModel(itemView.context, it))
                itemView.tag = it
            }
        }
    }
}