package io.github.wotaslive.dynamiclist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.wotaslive.DataBindingViewHolder
import io.github.wotaslive.data.model.DynamicInfo
import io.github.wotaslive.databinding.ItemDynamicPicBinding
import io.github.wotaslive.roomlist.room.pictures.DynamicPicturesAdapter
import io.github.wotaslive.roomlist.room.pictures.ItemViewModel

class DynamicPicturesAdapter(private val callback: DynamicPicturesAdapter.Callback, private val list: List<DynamicInfo.Content.Data.Picture>) : RecyclerView.Adapter<DataBindingViewHolder>() {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val binding = ItemDynamicPicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.eventHandler = callback
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        list[position].let {
            with(holder) {
                bind(ItemViewModel(it.filePath))
                itemView.tag = it
            }
        }
    }
}