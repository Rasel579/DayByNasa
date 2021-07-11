package com.app_maker.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.app_maker.R
import com.app_maker.databinding.FragmentViewpagerBinding
import com.app_maker.models.rest.MarsMutableApiDTO
import com.app_maker.models.rest.NasaEpicDTO
import com.app_maker.ui.iteractors.ZoomOutPageTransformer
import com.app_maker.view_models.AppState
import com.app_maker.view_models.ViewPagerViewModel
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.*


class ViewPagerFragment : Fragment() {
    private lateinit var binding: FragmentViewpagerBinding
    private  var earthData: MutableList<NasaEpicDTO> ?= null
    private var marsData : MarsMutableApiDTO ?= null
    private val viewModel by lazy {
        ViewModelProvider(this).get(ViewPagerViewModel::class.java)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentViewpagerBinding.inflate(inflater, container, false)
        viewModel.liveData.observe(viewLifecycleOwner, {renderData(it as AppState)})
        viewModel.loadDataFromMars(DATE)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun renderData(appState: AppState)=with(binding) {
        when(appState){
            is AppState.MarsFrgSuccess ->  marsData = appState.dataFromNasa
            is AppState.EarthFrgSuccess -> earthData = appState.dataFromNasa
        }
        viewPager2.adapter = ViewPagerAdapter(earthData, marsData)
        TabLayoutMediator(tabView, viewPager2){tab, position ->
            run {
                when(position){
                    data.getValue("Mars") -> {
                        tab.text = "Mars"
                        tab.icon = context?.getDrawable(R.drawable.mars)
                    }
                    data.getValue("Earth") -> {
                        tab.text = "Earth"
                        tab.icon = context?.getDrawable(R.drawable.world)
                    }
                    data.getValue("Moon") -> {
                        tab.text = "Moon"
                        tab.icon = context?.getDrawable(R.drawable.moon)
                    }
                }
            }
        }.attach()
        viewPager2.setPageTransformer(ZoomOutPageTransformer())
    }

    companion object {
        fun newInstance() = ViewPagerFragment()
        private val data = mapOf("Mars" to 0, "Earth" to 1, "Moon" to 2)
        private const val DAY_IN_MILLIS = 86400000
        private val DATE = SimpleDateFormat("yyyy/MM/dd").format( Date().time.minus(2* DAY_IN_MILLIS))


    }
}