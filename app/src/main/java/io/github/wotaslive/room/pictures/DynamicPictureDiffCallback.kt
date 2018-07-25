package io.github.wotaslive.room.pictures

import android.support.v7.util.DiffUtil
import io.github.wotaslive.data.model.Data

class DynamicPictureDiffCallback : DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data?, newItem: Data?): Boolean {
        return oldItem?.picId == newItem?.picId
    }

    override fun areContentsTheSame(oldItem: Data?, newItem: Data?): Boolean {
        return oldItem?.filePath == newItem?.filePath
    }
}