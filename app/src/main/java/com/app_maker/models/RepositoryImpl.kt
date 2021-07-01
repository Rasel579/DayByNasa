package com.app_maker.models

import android.util.Log
import com.app_maker.BuildConfig
import com.app_maker.models.rest.BackendApi
import com.app_maker.models.rest.BackendRepo
import com.app_maker.models.rest.NasaPictureDTO
import retrofit2.Callback

class RepositoryImpl : Repository {
    override fun getDataFromApi(callback: Callback<NasaPictureDTO>) {
         BackendRepo.api.getDataFromNasa(BuildConfig.NASA_APIKEY).enqueue(callback)
    }
}