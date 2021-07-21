package com.app_maker.ui.iteractors

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.app_maker.ui.NasaNotesAdapter

class ItemTouchHelperCallBack(private val adapter : NasaNotesAdapter)
    : ItemTouchHelper.Callback() {
    override fun isLongPressDragEnabled(): Boolean  = true
    override fun isItemViewSwipeEnabled(): Boolean = true
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags( dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        source: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        adapter.onItemMove(source.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (direction != ItemTouchHelper.ACTION_STATE_IDLE){
            val itemViewHolder = viewHolder as ItemTouchViewHolder
            itemViewHolder.onItemSelected()
        }
        super.onSelectedChanged(viewHolder, direction)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        val itemViewHolder = viewHolder as ItemTouchViewHolder
        itemViewHolder.onItemClear()
    }
}