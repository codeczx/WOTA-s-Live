package io.github.wotaslive

import android.arch.lifecycle.ViewModel
import android.databinding.ViewDataBinding
import com.android.databinding.library.baseAdapters.BR
import com.chad.library.adapter.base.BaseViewHolder

class DataBindingViewHolder(val binding: ViewDataBinding) : BaseViewHolder(binding.root) {
    fun bind(viewModel: ViewModel) {
        with(binding) {
            setVariable(BR.viewModel, viewModel)
            executePendingBindings()
        }
    }
}