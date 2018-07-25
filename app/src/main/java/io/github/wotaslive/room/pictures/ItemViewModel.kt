package io.github.wotaslive.room.pictures

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import io.github.wotaslive.data.model.Data

class ItemViewModel(data: Data) : ViewModel() {
    val url = ObservableField<String>(data.filePath)
}