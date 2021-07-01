package com.app_maker.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.app_maker.R
import com.app_maker.databinding.MainFragmentBinding
import com.app_maker.models.rest.NasaPictureDTO
import com.app_maker.view_models.AppState
import com.app_maker.view_models.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var data : NasaPictureDTO

    private lateinit var binding : MainFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.liveData.observe(viewLifecycleOwner, { renderData(it as AppState)})
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.loadDataFromApi()
    }

    private fun renderData(appState: AppState) {
        when(appState){
            is AppState.Success -> {
               data = appState.dataFromNasa
            }
            is AppState.Loading -> appState
            is AppState.Errors -> appState.error
        }

    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
