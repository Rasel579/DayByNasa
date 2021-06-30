package com.app_maker.models.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BackendRepo {
   val api : BackendApi by lazy {
         val adapter = Retrofit.Builder()
             .baseUrl(ApiUtils.baseUrl)
             .addConverterFactory(GsonConverterFactory.create())
             .client(ApiUtils.getOkHTTPBuilderWithHeaders())
             .build()
      adapter.create(BackendApi ::class.java)
   }
}