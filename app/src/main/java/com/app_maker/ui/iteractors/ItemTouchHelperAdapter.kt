package com.app_maker.ui.iteractors


interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition : Int)
    fun onItemDismiss(position: Int)
}