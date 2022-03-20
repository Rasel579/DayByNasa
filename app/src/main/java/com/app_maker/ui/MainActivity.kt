package com.app_maker.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app_maker.R

class MainActivity : AppCompatActivity() {
   var isCustomTheme = false
    override fun onCreate(savedInstanceState: Bundle?) {
        initDataSet()
        super.onCreate(savedInstanceState)
        if (isCustomTheme){
            setTheme(R.style.CustomSecondaryTheme)
        } else{
            setTheme(R.style.Theme_DayByNasa)
        }
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

    }

    private fun initDataSet() {
       isCustomTheme =  getSharedPreferences(MainFragment.sharedPreferenceName, Context.MODE_PRIVATE)
            .getBoolean(MainFragment.isSecondaryTheme, false)
    }
}