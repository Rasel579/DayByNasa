package com.app_maker

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }
    companion object{
      lateinit var appInstance : App
    }
}