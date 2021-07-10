package com.app_maker.models

import android.annotation.SuppressLint
import android.util.Log
import com.app_maker.BuildConfig
import com.app_maker.models.rest.*
import retrofit2.Callback
import java.text.SimpleDateFormat
import java.util.*

class RepositoryImpl : Repository {
    override fun getDataFromApi(callback: Callback<NasaPictureDTO>) {
         BackendRepo.api.getDataFromNasa(BuildConfig.NASA_APIKEY).enqueue(callback)
    }

    @SuppressLint("SimpleDateFormat")
    override fun getDataFromApiPrevDatePic(date: String, callback: Callback<NasaPictureDTO>) {
        BackendRepo.api.getDataFromNasaPreviousDate(date, BuildConfig.NASA_APIKEY).enqueue(callback)
    }

    override fun getDataFromEPICNasaApi(
        date: String,
        callback: Callback<MutableList<NasaEpicDTO>>
    ) {
        BackendRepo.api.getDataFromNasaEpicApi(date, BuildConfig.NASA_APIKEY).enqueue(callback)
    }

    override fun getDataFromMarsApi(callback: Callback<MarsMutableApiDTO>) {
        BackendRepo.api.getDataFromMarsApi(800, BuildConfig.NASA_APIKEY).enqueue(callback)
    }
}