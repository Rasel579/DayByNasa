package com.app_maker.ui

import android.net.Uri
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
import com.app_maker.MARS_IMG_URI
import com.app_maker.MOON_PIC_URL
import com.app_maker.R
import com.app_maker.databinding.FragmentMarsBinding
import com.app_maker.models.rest.MarsMutableApiDTO
import com.app_maker.parseURL
import com.app_maker.view_models.AppState
import com.app_maker.view_models.ViewPagerViewModel
import java.text.DateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MarsFragment : Fragment() {
    private lateinit var binding: FragmentMarsBinding
    private lateinit var data : MarsMutableApiDTO
    private val viewModel by  lazy {
         ViewModelProvider(this).get(ViewPagerViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMarsBinding.inflate(inflater, container, false)
        viewModel.liveData.observe(viewLifecycleOwner, {renderData( it as AppState)})
        viewModel.loadDataFromMars(DATE)
        return binding.root
    }

    private fun renderData(appState: AppState) {
          when(appState){
              is AppState.MarsFrgSuccess -> {
                  data = appState.dataFromNasa!!
                  binding.marsPhoto.load(parseURL(data.photos.get(0).srcImg))
                  Log.e("Mars",parseURL(data.photos.get(3).srcImg))
              }
          }
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        private val DATE = LocalDateTime.now().minusDays(2).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        fun newInstance() = MarsFragment()
    }
}