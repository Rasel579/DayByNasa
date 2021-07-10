package com.app_maker.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.app_maker.databinding.FragmentEarthBinding
import com.app_maker.getImageUrl
import com.app_maker.models.rest.NasaEpicDTO
import com.app_maker.view_models.AppState
import com.app_maker.view_models.EarthViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class EarthFragment : Fragment() {
   private lateinit var binding : FragmentEarthBinding
   private lateinit var viewModel : EarthViewModel
   private var data = mutableListOf<NasaEpicDTO>() ?: null
    @RequiresApi(Build.VERSION_CODES.O)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEarthBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(EarthViewModel::class.java)
        viewModel.liveData.observe(viewLifecycleOwner, {
            renderData(it as AppState)
        })
        viewModel.loadDataFromApi(DATE)
        return binding.root
    }


    private fun renderData(appState : AppState) {
        when(appState){
            is AppState.EarthFrgSuccess -> {
                data = appState.dataFromNasa
                binding.earthImage.load(getImageUrl(data?.get(0)?.image, DATE))
                Log.e("DATA", getImageUrl(data?.get(0)?.image, DATE))
                Log.e("DATE", DATE)
            }

        }
    }
    companion object {
        fun newInstance() = EarthFragment()
        private val DAY_IN_MILLIS = 86400000
        @SuppressLint("SimpleDateFormat")
        private val DATE = SimpleDateFormat("yyyy/MM/dd").format( Date().time.minus(4* DAY_IN_MILLIS))

    }
}