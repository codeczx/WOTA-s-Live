package io.github.wotaslive.roomlist.room.pictures

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField

class ItemViewModel(url: String) : ViewModel() {
    val url = ObservableField<String>(url)
}