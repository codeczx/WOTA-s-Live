package io.github.wotaslive.dynamiclist

import android.support.v7.util.DiffUtil
import io.github.wotaslive.data.model.DynamicInfo

class DynamicDiffCallback : DiffUtil.ItemCallback<DynamicInfo.Content.Data>() {
    override fun areItemsTheSame(oldItem: DynamicInfo.Content.Data?, newItem: DynamicInfo.Content.Data?): Boolean {
        return oldItem?.dynamicId == newItem?.dynamicId
    }

    override fun areContentsTheSame(oldItem: DynamicInfo.Content.Data?, newItem: DynamicInfo.Content.Data?): Boolean {
        return oldItem?.ctime == newItem?.ctime &&
                oldItem?.content == newItem?.content &&
                oldItem?.comment == newItem?.comment
    }
}