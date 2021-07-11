package com.app_maker.models.rest

import com.google.gson.annotations.SerializedName

data class MarsPhotoDTO(
    val id : Int,
    @SerializedName("earth_date")
    val earthDate: String,
    @SerializedName("img_src")
    val srcImg : String,
    val camera : Map<String,String>
)
