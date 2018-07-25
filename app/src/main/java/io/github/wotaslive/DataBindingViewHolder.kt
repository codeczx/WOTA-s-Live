package io.github.wotaslive

import android.arch.lifecycle.ViewModel
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import com.android.databinding.library.baseAdapters.BR

class DataBindingViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(viewModel: ViewModel) {
        with(binding) {
            setVariable(BR.viewModel, viewModel)
            executePendingBindings()
        }
    }
}