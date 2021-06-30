package com.app_maker.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.app_maker.R
import com.app_maker.databinding.MainFragmentBinding
import com.app_maker.view_models.AppState
import com.app_maker.view_models.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding : MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.liveData.observe(viewLifecycleOwner, { renderData(it as AppState)})
        // TODO: Use the ViewModel
    }

    private fun renderData(appState: AppState) {
        when(appState){
            is AppState.Success -> {
                appState.dataFromNasa
            }
            is AppState.Loading -> appState
            is AppState.Errors -> appState.error
        }

    }


}