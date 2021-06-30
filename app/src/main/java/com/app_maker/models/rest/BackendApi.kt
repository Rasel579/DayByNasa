package com.app_maker.models.rest

import com.app_maker.BuildConfig
import retrofit2.Call
import retrofit2.http.GET

interface BackendApi {
   @GET("/planetary/apod?" + BuildConfig.NASA_APIKEY)
   fun getDataFromNasa() : Call<NasaPictureDTO>
}