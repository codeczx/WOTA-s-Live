package io.github.wotaslive

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

abstract class BaseLazyFragment : Fragment() {
    private var isInitData = false /*标志位 判断数据是否初始化*/
    private var isVisibleToUser = false /*标志位 判断fragment是否可见*/
    private var isPrepareView = false /*标志位 判断view已经加载完成 避免空指针操作*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isPrepareView = true
    }

    /*加载数据的方法,由子类实现*/
    abstract fun initData()

    abstract fun scrollToTop()

    /*懒加载方法*/
    fun lazyInitData() {
        if (!isInitData && isVisibleToUser && isPrepareView) {
            isInitData = true
            initData()
        }
    }

    /*当fragment由可见变为不可见和不可见变为可见时回调*/
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        this.isVisibleToUser = isVisibleToUser
        lazyInitData()
    }

    /*fragment生命周期中onViewCreated之后的方法 在这里调用一次懒加载 避免第一次可见不加载数据*/
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lazyInitData()
    }
}