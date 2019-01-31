package io.github.wotaslive.showlist

import android.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import io.github.wotaslive.R
import io.github.wotaslive.data.model.ShowInfo
import io.github.wotaslive.databinding.ItemShowBinding

/**
 * Created by Tony on 2017/10/22 21:10.
 * Class description:
 */
class ShowListAdapter : BaseQuickAdapter<ShowInfo.ContentBean.ShowBean, BaseViewHolder>(R.layout.item_show) {
    override fun convert(helper: BaseViewHolder?, item: ShowInfo.ContentBean.ShowBean?) {
        helper?.itemView?.let {
            val binding: ItemShowBinding? = DataBindingUtil.bind(it)
            item?.let { it1 ->
                binding?.viewModel = ShowItemViewModel(it.context, it1)
            }
        }
    }
}