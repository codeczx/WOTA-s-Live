package io.github.wotaslive.dynamiclist

import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.wotaslive.DataBindingViewHolder
import io.github.wotaslive.data.model.DynamicInfo
import io.github.wotaslive.data.model.SyncInfo
import io.github.wotaslive.databinding.ItemDynamicBinding

class DynamicAdapter : ListAdapter<DynamicInfo.Content.Data, DataBindingViewHolder>(DynamicDiffCallback()) {
    private val memberMap = HashMap<Int, SyncInfo.Content.MemberInfo>()
    private lateinit var binding: ItemDynamicBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        binding = ItemDynamicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        getItem(position).let {
            with(holder) {
                bind(DynamicItemViewModel(itemView.context, this@DynamicAdapter.binding.rvPics, it, memberMap[it.memberId]))
                itemView.tag = it
            }
        }
    }

    fun updateMember(members: Map<Int, SyncInfo.Content.MemberInfo>?) {
        members?.let {
            memberMap.putAll(it)
        }
    }
}