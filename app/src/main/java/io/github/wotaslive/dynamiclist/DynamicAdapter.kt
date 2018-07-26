package io.github.wotaslive.dynamiclist

import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.ViewGroup
import io.github.wotaslive.DataBindingViewHolder
import io.github.wotaslive.data.model.DynamicInfo

class DynamicAdapter : ListAdapter<DynamicInfo.Content.Data, DataBindingViewHolder>(DynamicDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}