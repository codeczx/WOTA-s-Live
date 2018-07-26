package io.github.wotaslive.roomlist.room.pictures

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import io.github.wotaslive.data.model.DynamicPictureInfo

class ItemViewModel(data: DynamicPictureInfo.Content.Data) : ViewModel() {
    val url = ObservableField<String>(data.filePath)
}