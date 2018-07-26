package io.github.wotaslive.roomlist.room.pictures

import android.support.v7.util.DiffUtil
import io.github.wotaslive.data.model.DynamicPictureInfo

class DynamicPictureDiffCallback : DiffUtil.ItemCallback<DynamicPictureInfo.Content.Data>() {
    override fun areItemsTheSame(oldItem: DynamicPictureInfo.Content.Data?, newItem: DynamicPictureInfo.Content.Data?): Boolean {
        return oldItem?.picId == newItem?.picId
    }

    override fun areContentsTheSame(oldItem: DynamicPictureInfo.Content.Data?, newItem: DynamicPictureInfo.Content.Data?): Boolean {
        return oldItem?.filePath == newItem?.filePath
    }
}