package com.app_maker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app_maker.databinding.FragmentNasaNotesBinding
import com.app_maker.models.NoteData
import com.app_maker.ui.iteractors.ItemTouchHelperCallBack
import com.app_maker.ui.iteractors.OnStartDragListener
import com.app_maker.view_models.AppState
import com.app_maker.view_models.NasaViewModel


class NasaNotesFragment : Fragment() {
    private  var data  = mutableListOf<NoteData>()
    private lateinit var binding : FragmentNasaNotesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemTouchHelper: ItemTouchHelper
    private val viewModel by lazy {
        ViewModelProvider(this).get(NasaViewModel::class.java)
    }
    private lateinit var adapter: NasaNotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNasaNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveData.observe(viewLifecycleOwner, {renderData(it)})
        viewModel.getNotes()
        binding.sendNote.setOnClickListener{
            adapter.addItem(binding.itemNoteEditText.text.toString())
            viewModel.sendNote(binding.itemNoteEditText.text.toString())
        }
    }

    private fun renderData(appState : AppState){
        when(appState){
            is AppState.NasaNoteFrgSuccess -> {
                data.addAll(appState.dataNasaNotes)
                initRecyclerView()
            }
        }


    }

    private fun initRecyclerView() {
        recyclerView = binding.listOfNotes
        adapter = NasaNotesAdapter(data, object : OnStartDragListener{
            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                itemTouchHelper.startDrag(viewHolder)
            }
        })
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallBack(adapter))
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    companion object {
        fun newInstance() = NasaNotesFragment()
    }
}