package io.github.wotaslive.roomlist.room.pictures

import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.wotaslive.DataBindingViewHolder
import io.github.wotaslive.data.model.DynamicPictureInfo
import io.github.wotaslive.databinding.ItemDynamicPicBinding

class DynamicPicturesAdapter(private val callback: Callback) : ListAdapter<DynamicPictureInfo.Content.Data, DataBindingViewHolder>(DynamicPictureDiffCallback()) {

    interface Callback {
        fun onImageClick(url: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val binding = ItemDynamicPicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.eventHandler = callback
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        getItem(position).let {
            with(holder) {
                bind(ItemViewModel(it))
                itemView.tag = it
            }
        }
    }
}