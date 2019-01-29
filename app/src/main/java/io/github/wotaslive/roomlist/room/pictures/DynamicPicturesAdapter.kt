package io.github.wotaslive.roomlist.room.pictures

import android.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import io.github.wotaslive.R
import io.github.wotaslive.data.model.DynamicPictureInfo
import io.github.wotaslive.databinding.ItemDynamicPicBinding

class DynamicPicturesAdapter :
        BaseQuickAdapter<DynamicPictureInfo.Content.Data, BaseViewHolder>(R.layout.item_dynamic_pic, null) {

    override fun convert(helper: BaseViewHolder?, item: DynamicPictureInfo.Content.Data?) {
        helper?.itemView?.let {
            val binding: ItemDynamicPicBinding? = DataBindingUtil.bind(it)
            binding?.viewModel = ItemViewModel(item?.filePath)
            it.tag = item
        }
    }
}