package io.github.wotaslive.widget

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class SpaceItemDecoration(private val horizontalSpace: Int, private val verticalSpace: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = 0
        outRect.left = horizontalSpace
        outRect.right = horizontalSpace
        outRect.top = if (parent.getChildAdapterPosition(view) == 0) 0 else verticalSpace
    }
}