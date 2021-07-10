package com.app_maker.models.rest

import com.app_maker.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BackendApi {
   @GET("planetary/apod")
   fun getDataFromNasa(@Query("api_key") apiKey: String) : Call<NasaPictureDTO>@GET("planetary/apod")
   fun getDataFromNasaPreviousDate(@Query("date") date : String, @Query("api_key") apiKey: String) : Call<NasaPictureDTO>
}