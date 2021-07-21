package com.app_maker.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import com.app_maker.R
import com.app_maker.models.NoteData
import com.app_maker.ui.iteractors.ItemTouchHelperAdapter
import com.app_maker.ui.iteractors.ItemTouchViewHolder
import com.app_maker.ui.iteractors.OnStartDragListener
import kotlinx.android.synthetic.main.item_nasa_note.view.*
import java.text.SimpleDateFormat
import java.util.*

class NasaNotesAdapter(
    private var data : MutableList<NoteData>,
    private val onStartDrag : OnStartDragListener
) : RecyclerView.Adapter<NasaNotesAdapter.ItemNote>(), ItemTouchHelperAdapter {
    private val DATE = SimpleDateFormat("yyyy-MM-dd").format(Date())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemNote(LayoutInflater.from(parent.context).inflate(R.layout.item_nasa_note, parent, false))

    override fun onBindViewHolder(holder: ItemNote, position: Int) {
        data?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = data.size
    fun addItem(description : String){
        data.add(NoteData(DATE, description))
        notifyItemInserted(itemCount - 1)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        data.removeAt(fromPosition).apply {
            data.add(if(toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition,toPosition)
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ItemNote(itemView : View) : RecyclerView.ViewHolder(itemView), ItemTouchViewHolder{

        private var isExpanded = false

        @SuppressLint("ClickableViewAccessibility")
        fun bind(noteData: NoteData) {
             itemView.date.text = noteData.date
             itemView.description.text = noteData.description
             itemView.delete_btn.setOnClickListener{ deleteItem()}
             itemView.move_up.setOnClickListener{moveUp()}
             itemView.move_down.setOnClickListener{moveDown()}
             itemView.show_hiden_text_btn.setOnClickListener{toggleText(itemView)}
             itemView.hamburger_btn.setOnTouchListener{_, event ->
                 if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN){
                     onStartDrag.onStartDrag(this)
                 }
                 false
             }
        }

        private fun deleteItem(){
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveUp(){
            layoutPosition.takeIf { it > 1 }?.also {
                data.removeAt(it).apply {
                    data.add(it - 1, this)
                }
            }
            notifyItemMoved(layoutPosition, layoutPosition - 1)
        }
        private fun moveDown(){
            layoutPosition.takeIf { it < data.size - 1 }?.also {
                data.removeAt(it).apply {
                    data.add(it + 1, this)
                }
                notifyItemMoved(layoutPosition, layoutPosition + 1)
            }
        }
        private fun toggleText(itemView: View) {
               isExpanded = !isExpanded
               if (isExpanded){
                   itemView.hiden_description.visibility = View.VISIBLE
               } else {
                   itemView.hiden_description.visibility = View.GONE
               }
            notifyItemChanged(layoutPosition)
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.GRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.WHITE)
        }
    }
}