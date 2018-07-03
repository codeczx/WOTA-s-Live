package io.github.wotaslive.showlist


import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator
import io.github.wotaslive.R
import io.github.wotaslive.data.model.ShowInfo
import kotlinx.android.synthetic.main.frag_show_list.*

class ShowListFragment : Fragment(), ShowListContract.ShowListView, SwipeRefreshLayout.OnRefreshListener {
    private lateinit var mPresenter: ShowListContract.ShowListPresenter
    private lateinit var mShowAdapter: ShowListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.frag_show_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ShowListPresenterImpl(context, this)
        initView()
        initData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unSubscribe()
    }

    private fun initView() {
        mShowAdapter = ShowListAdapter(mPresenter)
        rv_show.layoutManager = LinearLayoutManager(context)
        rv_show.addItemDecoration(MaterialViewPagerHeaderDecorator())
        val verticalSpace = context?.resources?.getDimensionPixelOffset(R.dimen.cardMarginVertical)
        val horizontalSpace = context?.resources?.getDimensionPixelOffset(R.dimen.cardMarginHorizontal)
        rv_show.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.bottom = 0
                outRect.left = horizontalSpace!!
                outRect.right = horizontalSpace
                outRect.top = if (parent.getChildAdapterPosition(view) == 0) 0 else verticalSpace!!
            }
        })
        rv_show.adapter = mShowAdapter
        srl_show.setOnRefreshListener(this)
    }

    private fun initData() {
        mPresenter.getShowList()
        srl_show.isRefreshing = true
    }

    override fun setPresenter(presenter: ShowListContract.ShowListPresenter) {
        mPresenter = presenter
    }

    override fun onRefresh() {
        mPresenter.getShowList()
    }

    override fun refreshUI() {
        srl_show.isRefreshing = false
    }

    override fun updateShow(list: List<ShowInfo.ContentBean.ShowBean>?) {
        mShowAdapter.updateShowList(list)
    }

    override fun showMenu() {
    }

}
