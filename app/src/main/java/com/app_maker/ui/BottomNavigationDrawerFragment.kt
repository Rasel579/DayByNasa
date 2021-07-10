package com.app_maker.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app_maker.R
import com.app_maker.databinding.FragmentBottomNavigationDrawerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment(){
    private lateinit var binding: FragmentBottomNavigationDrawerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this
        binding = FragmentBottomNavigationDrawerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) = with(binding) {
        super.onActivityCreated(savedInstanceState)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.navigation_one -> {
                    changeSharedPreferenceData(true)
                }
                R.id.navigation_two -> {
                    changeSharedPreferenceData(false)
                }
            }
            true
        }
    }

    private fun changeSharedPreferenceData(isSecondaryTheme: Boolean) {
        activity?.let {
            val preference = it.getSharedPreferences(MainFragment.sharedPreferenceName, Context.MODE_PRIVATE)
            val editor = preference.edit()
            editor.putBoolean(MainFragment.isSecondaryTheme, isSecondaryTheme)
            editor.apply()
        }
        activity?.recreate()
    }
}