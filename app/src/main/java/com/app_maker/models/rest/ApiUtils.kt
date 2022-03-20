package com.app_maker.models.rest

import com.app_maker.baseNasaUrl
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object ApiUtils {
    val baseUrl = baseNasaUrl
    val duration = 2000L
    fun getOkHTTPBuilderWithHeaders() : OkHttpClient {
        val httpClient =  OkHttpClient.Builder()
        httpClient.apply {
           connectTimeout(duration, TimeUnit.MILLISECONDS)
           readTimeout(duration, TimeUnit.MILLISECONDS)
           writeTimeout(duration, TimeUnit.MILLISECONDS)
        }
        httpClient.addInterceptor{chain ->
            val originalReq = chain.request()
            val request = originalReq.newBuilder()
                .method(originalReq.method(), originalReq.body())
                .build()
             chain.proceed(request)
        }
         return httpClient.build()
    }
}