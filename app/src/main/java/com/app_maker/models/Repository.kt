package com.app_maker.models

import com.app_maker.models.rest.NasaPictureDTO
import retrofit2.Callback

interface Repository {
    fun getDataFromApi(callback : Callback<NasaPictureDTO>)
    fun getDataFromApiPrevDatePic(date : String, callback : Callback<NasaPictureDTO>)
}