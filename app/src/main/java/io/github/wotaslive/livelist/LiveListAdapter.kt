package io.github.wotaslive.livelist

import android.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import io.github.wotaslive.R
import io.github.wotaslive.data.model.LiveInfo
import io.github.wotaslive.databinding.ItemLiveBinding

class LiveListAdapter : BaseQuickAdapter<LiveInfo.ContentBean.RoomBean, BaseViewHolder>(R.layout.item_live) {
    override fun convert(helper: BaseViewHolder?, item: LiveInfo.ContentBean.RoomBean?) {
        helper?.itemView?.let {
            val binding: ItemLiveBinding? = DataBindingUtil.bind(it)
            item?.let { it1 ->
                binding?.viewModel = LiveItemViewModel(it.context, it1)
            }
        }
    }
}